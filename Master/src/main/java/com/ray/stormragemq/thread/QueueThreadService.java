package com.ray.stormragemq.thread;

import com.ray.stormragemq.common.Message;
import com.ray.stormragemq.common.MessageTypeConstant;
import com.ray.stormragemq.constant.ConstantVariable;
import com.ray.stormragemq.dao.QueueMessageDao;
import com.ray.stormragemq.entity.QueueEntity;
import com.ray.stormragemq.entity.QueueMessageEntity;
import com.ray.stormragemq.netty.ClientChannel;
import com.ray.stormragemq.netty.service.GatewayService;
import com.ray.stormragemq.util.JsonUtil;
import com.ray.stormragemq.util.LogUtil;
import com.ray.stormragemq.util.RandomUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 将队列中的消息发送到消费者中
 * */
@Service
public class QueueThreadService {

    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private Map<String, QueueEntity> queueMap;

    @Autowired
    private QueueMessageDao queueMessageDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 每个队列启动一个线程发送消息
     * */
    public void start(){
        queueMap.forEach((s, queueEntity) -> {

            startThread(queueEntity);

        });

    }


    /**
     * 开启线程
     * */
    private void startThread(QueueEntity queueEntity){
        new Thread(() -> {
            int modCount = ModCount.getModCount();
            Thread.currentThread().setName(queueEntity.getName() + "-" + modCount);

            BlockingQueue<QueueMessageEntity> q = queueEntity.getBlockingQueue();
            List<String> consumerUuidList = queueEntity.getConsumerUuidList();

            while (true){
                QueueMessageEntity queueMessage = null;

                //检查消费者是否存在
                if(consumerUuidList.size() > 0){
                    try {
                        queueMessage = q.peek();

                        //如果该消息是重要消息，并且状态为发送中了，那么检查数据库中是否收到，收到就直接放弃了
                        if(queueMessage != null){
                            if(MessageTypeConstant.IMPORTANT_MESSAGE_TYPE.equals(queueMessage.getMessage().getType()) &&
                                    queueMessage.isSending() == true && queueMessage.isReceived() == false){
                                //消息状态为发送中
                                Map<String, String> param = new HashMap<>();
                                param.put("id", queueMessage.getId());
                                QueueMessageEntity dbQM = queueMessageDao.getQueueMessage(param);
                                if(dbQM.isReceived() == true){
                                    //已送达了
                                    LogUtil.logInfo(queueMessage.getId() + "  已送达，不发送该消息");
                                    q.poll(5, TimeUnit.SECONDS);
                                    continue;
                                }
                            }

                        }


                        if(queueMessage != null){
                            Message theMessage = queueMessage.getMessage();
                            theMessage.setConfirmId(queueMessage.getId());
                            String messageString = JsonUtil.toJson(theMessage);

                            ClientChannel clientChannel = null;

                            //取出具有sendCount的消费者
                            for (String uuid : consumerUuidList) {
                                ClientChannel cc = gatewayService.getGatewayChannel(uuid);
                                if(cc != null && cc.canSend()){
                                    clientChannel = cc;
                                    break;
                                }

                            }

                            //可以发送消息
                            if(clientChannel != null){
                                queueMessage = q.poll(5, TimeUnit.SECONDS);
                                clientChannel.getSocketChannel().writeAndFlush(Unpooled.copiedBuffer(messageString, CharsetUtil.UTF_8));
                                LogUtil.logInfo("向" + clientChannel.getName() + "发送消息， 消息内容:" + JsonUtil.toJson(queueMessage));
                                //将 不重要 的消息直接把状态改为送达
                                //并将redis中的消息删除
                                if(queueMessage.getMessage() != null && MessageTypeConstant.NORMAL_MESSAGE_TYPE.equals(queueMessage.getMessage().getType())){
                                    LogUtil.logInfo("普通消息，插入数据库并从Redis删除，该消息结束");
                                    queueMessage.setReceived(true);
                                    queueMessage.setSending(true);
                                    queueMessage.setConsumerName(clientChannel.getName());
                                    queueMessageDao.insertQueueMessage(queueMessage);
                                    redisTemplate.opsForHash().delete(ConstantVariable.MESSAGE_QUEUE_KEY,  queueMessage.getId());
                                }

                                //将 重要 的消息把状态改为发送中
                                //将postgres中的消息改状态
                                if(queueMessage.getMessage() != null && MessageTypeConstant.IMPORTANT_MESSAGE_TYPE.equals(queueMessage.getMessage().getType())){
                                    LogUtil.logInfo("重要消息，将状态置为发送中，等待下次检查消息是否送达");
                                    queueMessage.setSending(true);
                                    queueMessage.setConsumerName(clientChannel.getName());
                                    queueMessageDao.updateQueueMessage(queueMessage);
                                    //并且再将该消息放入末尾
                                    q.offer(queueMessage);
                                }
                            }

                        }
                        else{
                            //队列为空，暂停5秒
                            try {
                                TimeUnit.SECONDS.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    //消费者不存在，暂停5秒
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //检查modCount
                if(modCount != ModCount.getModCount()){
                    LogUtil.logInfo("ModCount被修改，线程结束，modCount = " + ModCount.getModCount());
                    break;
                }

            }
        }).start();

    }






}
