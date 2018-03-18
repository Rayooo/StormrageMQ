package com.ray.stormragemq.dao;

import com.ray.stormragemq.entity.QueueMessageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QueueMessageDao {

    QueueMessageEntity getQueueMessage(Map<String, String> param);

    void insertQueueMessage(QueueMessageEntity queueMessageEntity);

    List<QueueMessageEntity> getAllQueueMessageNotReceived();

    void updateQueueMessage(QueueMessageEntity qm);

    /**
     * 根据消息id查询出队列消息
     * */
    List<QueueMessageEntity> getQueueMessageByMessageId(String messageId);

}
