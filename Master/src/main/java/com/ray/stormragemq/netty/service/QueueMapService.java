package com.ray.stormragemq.netty.service;

import com.ray.stormragemq.entity.QueueEntity;
import com.ray.stormragemq.entity.QueueMessageEntity;
import com.ray.stormragemq.netty.ClientChannel;
import com.ray.stormragemq.util.JsonUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 从queueMap中取出数据，发送到消费者
 *
 *
 * 无用！！！！！
 *
 * */

public class QueueMapService {

    private Map<String, QueueEntity> queueMap;

    private GatewayService gatewayService;

    private volatile static boolean IS_RUNNING = false;


    public void sendMessage(){
        if(!IS_RUNNING){
            synchronized (QueueMapService.class){
                if(!IS_RUNNING){
                    //发送消息
                    try {
                        IS_RUNNING = true;
                        sendMessageInner();
                    }finally {
                        IS_RUNNING = false;
                    }
                }
            }
        }
    }

    private void sendMessageInner(){
        queueMap.forEach((s, queueEntity) -> {
            Queue<QueueMessageEntity> queue = queueEntity.getBlockingQueue();


        });
    }

    /**
     * 根据队列名，消费者发送消息
     *
     * 规则：一个队列queue中含有多个queueMessage
     *      同时，一个queue中也有多个消费者
     *      但是一个queueMessage只能被一个消费者消费
     *      将queueMessage所以随机给一个消费者
     * */
    public void sendMessageByQueueNameAndConsumer(String queueName, String consumerName){
        QueueEntity queueEntity = queueMap.get(queueName);
        Queue<QueueMessageEntity> q = queueEntity.getBlockingQueue();
        if(q.isEmpty()){
            return;
        }

        List<String> consumerUuidList = queueEntity.getConsumerUuidList();
        ClientChannel clientChannel = gatewayService.getClientChannelByConsumerName(consumerName);
        if(clientChannel != null){
            String uuid = clientChannel.getSocketChannel().id().asLongText();
            if(consumerUuidList.contains(uuid)){
                //发送消息
                clientChannel.getSocketChannel().writeAndFlush(Unpooled.copiedBuffer(JsonUtil.toJson(q.poll().getMessage()), CharsetUtil.UTF_8));
            }
        }
    }

    /**
     * 根据队列名，消费者uuid发送消息
     * */
    public void sendMessageByQueueNameAndConsumerUuid(String queueName, String consumerUuid){
        QueueEntity queueEntity = queueMap.get(queueName);
        Queue<QueueMessageEntity> q = queueEntity.getBlockingQueue();
        if(q.isEmpty()){
            return;
        }

        ClientChannel clientChannel = gatewayService.getGatewayChannel(consumerUuid);
        if(clientChannel != null){
            //发送消息
            clientChannel.getSocketChannel().writeAndFlush(Unpooled.copiedBuffer(JsonUtil.toJson(q.poll().getMessage()), CharsetUtil.UTF_8));
        }
    }





}
