package com.ray.stormragemq.service.impl;

import com.ray.stormragemq.dao.QueueDao;
import com.ray.stormragemq.domain.QueueEntity;
import com.ray.stormragemq.service.QueueService;
import com.ray.stormragemq.util.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueueServiceImpl implements QueueService {

    private final QueueDao queueDao;

    private Map<String, QueueEntity> queueMap;

    @Autowired
    public QueueServiceImpl(QueueDao queueDao, Map<String, QueueEntity> queueMap) {
        this.queueDao = queueDao;
        this.queueMap = queueMap;
    }

    @Transactional
    @Override
    public void addQueue(QueueEntity queue) throws BaseException {
        if(canAddQueue(queue)){
            queueDao.insertQueue(queue);
            queue = queueDao.getQueue(queue);
            queueMap.put(queue.getName(), queue);
        }
        else{
            throw new BaseException("系统中已存在相同名字队列");
        }
    }

    private boolean canAddQueue(QueueEntity queue){
        return queueDao.countQueueByName(queue) < 1;
    }

    @Override
    public List getQueueListByUserId(int userid) {
        Map<String, Integer> param = new HashMap<>();
        param.put("userid", userid);
        return queueDao.getQueueList(param);
    }
}
