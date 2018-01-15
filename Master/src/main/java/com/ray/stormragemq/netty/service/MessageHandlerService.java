package com.ray.stormragemq.netty.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.stormragemq.common.Message;
import com.ray.stormragemq.constant.ClientTypeEnum;
import com.ray.stormragemq.constant.ConstantVariable;
import com.ray.stormragemq.entity.ExchangerEntity;
import com.ray.stormragemq.entity.QueueEntity;
import com.ray.stormragemq.entity.QueueMessageEntity;
import com.ray.stormragemq.netty.ClientChannel;
import com.ray.stormragemq.service.UserAccountService;
import com.ray.stormragemq.util.BaseException;
import com.ray.stormragemq.util.BaseResponse;
import com.ray.stormragemq.util.LogUtil;
import com.ray.stormragemq.util.MatchQueueUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

@Service
public class MessageHandlerService {

    @Autowired
    private Map<String, ExchangerEntity> exchangerMap;

    @Autowired
    private Map<String, QueueEntity> queueMap;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private GatewayService gatewayService;

    /**
     * 处理不重要的消息
     * */
    public void handleNotImportantMessage(Message message) throws BaseException {
        String exchangerName = message.getExchangerName();
        ExchangerEntity exchanger = exchangerMap.get(exchangerName);
        if(exchanger == null){
            throw new BaseException("exchanger名字错误，已丢弃消息");
        }
        List<String> queueList = exchanger.getQueueList();
        if(queueList == null || queueList.size() == 0){
            throw new BaseException("该Exchanger不存在Queue");
        }

        String id = message.getUuid();
        if(StringUtils.isBlank(id)){
            throw new BaseException("Message id 为空");
        }

        //为每个队列发送消息
        Date now = new Date();
        int count = 0;
        for (String q : queueList) {
            QueueEntity queue = queueMap.get(q);
            BlockingQueue<QueueMessageEntity> bq = queue.getBlockingQueue();
            boolean canAdd = true;
            //根据id查找有没有重复的消息
            if(bq != null){
                for (QueueMessageEntity inner : bq) {
                    if(inner.getMessageId().equals(id)){
                        canAdd = false;
                        break;
                    }
                }

                if(canAdd){
                    QueueMessageEntity qm = new QueueMessageEntity();
                    qm.setId(message.getUuid() + "-" + (++count));
                    qm.setQueueName(queue.getName());
                    qm.setMessageId(message.getUuid());
                    qm.setCreateTime(now);
                    qm.setReceived(false);
                    qm.setMessage(message);
                    bq.offer(qm);
                    redisTemplate.opsForHash().put(ConstantVariable.MESSAGE_QUEUE_KEY, qm.getId(), qm.toJson());

                }
            }
        }




        queueMap.forEach((s, queueEntity) -> {
            LogUtil.logInfo(queueEntity.getBlockingQueue().toString());
        });


    }

    /**
     * 处理重要的消息
     * */
    public void handleImpontentMessage(Message message){

    }

    //处理消费者认证初始化消息
    private void handleConsumeInitMessage(Message message, String channelUuid) {
        String queueNameListString = message.getQueueNameList();
        if(StringUtils.isBlank(queueNameListString)){
            return;
        }

        List<String> list = Arrays.asList(queueNameListString.split(ConstantVariable.SEPARATOR));

        for (String queueName : list) {
            queueMap.forEach((s, queueEntity) -> {
                if(MatchQueueUtil.match(s, queueName)){
                    queueEntity.addConsumer(channelUuid);
                }
            });
        }

        gatewayService.syncConsumerName();


    }

    //处理 消费者和生产者 初始化消息
    public void handleInitMessage(Message message, ChannelHandlerContext ctx) throws JsonProcessingException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();

        //密码错误情况下
        if(!userAccountService.checkUser(message.getUserName(), message.getPassword())){
            BaseResponse<String> response = new BaseResponse<>();
            response.setMessage("密码错误");
            ctx.writeAndFlush(Unpooled.copiedBuffer(mapper.writeValueAsString(response), CharsetUtil.UTF_8)).sync();
            ctx.close();
            return;
        }

        //将channel保存起来，消费者和生产者
        String uuid = ctx.channel().id().asLongText();
        ClientChannel clientChannel = gatewayService.getGatewayChannel(uuid);
        Map<String, ClientChannel> map = gatewayService.getChannels();
        clientChannel.setName(message.getClientName());
        clientChannel.setClientType(message.getClientType());
        map.put(uuid, clientChannel);

        //检查是否为消费者
        if(ClientTypeEnum.CONSUMER.getType() == (message.getClientType())){
            handleConsumeInitMessage(message, ctx.channel().id().asLongText());
        }
    }

}
