package com.ray.stormragemq.config;

import com.ray.stormragemq.dao.ExchangerDao;
import com.ray.stormragemq.dao.QueueDao;
import com.ray.stormragemq.domain.ExchangerEntity;
import com.ray.stormragemq.domain.QueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class StartUp {

    private Map<String, ExchangerEntity> exchangerMap;

    private final ExchangerDao exchangerDao;

    private final QueueDao queueDao;

    private Map<String, QueueEntity> queueMap;

    @Autowired
    public StartUp(Map<String, ExchangerEntity> exchangerMap, ExchangerDao exchangerDao, QueueDao queueDao, Map<String, QueueEntity> queueMap) {
        this.exchangerMap = exchangerMap;
        this.exchangerDao = exchangerDao;
        this.queueDao = queueDao;
        this.queueMap = queueMap;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshEvent(){
        initExchanger();
        initQueue();
    }

    private void initExchanger(){
        List<ExchangerEntity> list = exchangerDao.getAllExchanger();
        list.forEach(e -> exchangerMap.put(e.getName(), e));
    }

    private void initQueue(){
        List<QueueEntity> list = queueDao.getAllQueue();
        list.forEach(q -> queueMap.put(q.getName(), q));
    }


}
