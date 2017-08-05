package com.ray.stormragemq.dao;

import com.ray.stormragemq.domain.QueueEntity;

import java.util.List;

public interface QueueDao {

    void insertQueue(QueueEntity queue);

    int countQueueByName(QueueEntity queue);

    List<QueueEntity> getQueueList(QueueEntity queue);

    int deleteQueueById(QueueEntity queue);

    List<QueueEntity> getAllQueue(QueueEntity queue);

    QueueEntity getQueue(QueueEntity queue);

    int updateQueue(QueueEntity queue);


}
