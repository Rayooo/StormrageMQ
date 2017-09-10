package com.ray.stormragemq.netty.service;

import com.ray.stormragemq.common.Message;
import com.ray.stormragemq.domain.ExchangerEntity;
import com.ray.stormragemq.domain.QueueEntity;
import com.ray.stormragemq.util.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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

    private static final String MESSAGE_KEY = "MessageKey";

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
        queueList.forEach(q -> {
            QueueEntity queue = queueMap.get(q);
            BlockingQueue<Message> bq = queue.getBlockingQueue();
            boolean canAdd = true;
            //根据id查找有没有重复的消息
            if(bq != null){
                for (Message inner : bq) {
                    if(inner.getUuid().equals(id)){
                        canAdd = false;
                        break;
                    }
                }

                if(canAdd){
                    //TODO 改成 QueueMessage
                    bq.offer(message);
                    redisTemplate.opsForHash().put(MESSAGE_KEY, message.getUuid(), message.toJson());

                }
            }
        });


        queueMap.forEach((s, queueEntity) -> {
            System.out.println(queueEntity.getBlockingQueue());
        });


    }

    public void handleImpontentMessage(Message message){

    }


}
