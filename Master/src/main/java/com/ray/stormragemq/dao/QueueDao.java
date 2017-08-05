package com.ray.stormragemq.dao;

import com.ray.stormragemq.domain.QueueEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface QueueDao {

    void insertQueue(QueueEntity queue);

    int countQueueByName(QueueEntity queue);

    List<QueueEntity> getQueueList(Map<String, Integer> param);

    int deleteQueueById(QueueEntity queue);

    List<QueueEntity> getAllQueue();

    QueueEntity getQueue(QueueEntity queue);

    int updateQueue(QueueEntity queue);


}
