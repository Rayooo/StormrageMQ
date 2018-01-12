package com.ray.stormragemq.service;


import com.ray.stormragemq.entity.QueueEntity;
import com.ray.stormragemq.entity.UserAccountEntity;
import com.ray.stormragemq.util.BaseException;

import java.util.List;

public interface QueueService {

    void addQueue(QueueEntity queue) throws BaseException;

    List getQueueListByUserId(int userid);

    void deleteQueue(QueueEntity queue, UserAccountEntity user) throws BaseException;

    QueueEntity getQueue(QueueEntity queue);

    void changeQueue(QueueEntity queue) throws BaseException;
}
