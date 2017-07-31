package com.ray.stormragemq.config;

import com.ray.stormragemq.dao.ExchangerDao;
import com.ray.stormragemq.domain.ExchangerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class StartUp {

    private final Map<String, ExchangerEntity> exchangerMap;

    private final ExchangerDao exchangerDao;

    @Autowired
    public StartUp(Map<String, ExchangerEntity> exchangerMap, ExchangerDao exchangerDao) {
        this.exchangerMap = exchangerMap;
        this.exchangerDao = exchangerDao;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshEvent(){
        List<ExchangerEntity> list = exchangerDao.getAllExchanger();
        list.forEach(e -> exchangerMap.put(e.getName(), e));
    }


}
