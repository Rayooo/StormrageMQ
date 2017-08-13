package com.ray.stormragemq.service.impl;

import com.ray.stormragemq.config.Refresh;
import com.ray.stormragemq.dao.QueueDao;
import com.ray.stormragemq.domain.QueueEntity;
import com.ray.stormragemq.domain.UserAccountEntity;
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

    private final Refresh refresh;

    @Autowired
    public QueueServiceImpl(QueueDao queueDao, Refresh refresh) {
        this.queueDao = queueDao;
        this.refresh = refresh;
    }

    @Transactional
    @Override
    public void addQueue(QueueEntity queue) throws BaseException {
        if(canAddQueue(queue)){
            queueDao.insertQueue(queue);
            refresh.contextRefreshEvent();
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

    @Override
    @Transactional
    public void deleteQueue(QueueEntity queue, UserAccountEntity user) throws BaseException {
        queue = getQueue(queue);
        if(queue.getCreateUserId().equals(user.getId())){
            if(queueDao.deleteQueueById(queue) == 1){
                refresh.contextRefreshEvent();
            }
            else{
                throw new BaseException("删除失败");
            }
        }
        else{
            throw new BaseException("无法删除不是自己创建的队列");
        }
    }

    @Override
    public QueueEntity getQueue(QueueEntity queue) {
        return queueDao.getQueue(queue);
    }

    @Override
    @Transactional
    public void changeQueue(QueueEntity queue) throws BaseException {
        if(canAddQueue(queue)){
            if(queueDao.updateQueue(queue) == 1){
                refresh.contextRefreshEvent();
            }
            else {
                throw new BaseException("更新失败");
            }
        }
        else {
            throw new BaseException("不能添加两个相同名字的队列");
        }

    }
}
