package com.ray.stormragemq.dao;

import com.ray.stormragemq.entity.QueueMessageEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueMessageDao {

    void insertQueueMessage(QueueMessageEntity queueMessageEntity);

}
