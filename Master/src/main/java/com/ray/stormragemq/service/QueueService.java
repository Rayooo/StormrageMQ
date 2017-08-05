package com.ray.stormragemq.service;


import com.ray.stormragemq.domain.QueueEntity;
import com.ray.stormragemq.util.BaseException;

import java.util.List;

public interface QueueService {

    void addQueue(QueueEntity queue) throws BaseException;

    List getQueueListByUserId(int userid);

}
