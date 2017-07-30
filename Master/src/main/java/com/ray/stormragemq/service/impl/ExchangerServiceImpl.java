package com.ray.stormragemq.service.impl;

import com.ray.stormragemq.dao.ExchangerDao;
import com.ray.stormragemq.domain.ExchangerEntity;
import com.ray.stormragemq.service.ExchangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExchangerServiceImpl implements ExchangerService {

    private final ExchangerDao exchangerDao;

    @Autowired
    public ExchangerServiceImpl(ExchangerDao exchangerDao) {
        this.exchangerDao = exchangerDao;
    }

    @Transactional
    @Override
    public void addExchanger(ExchangerEntity exchanger) {
        exchangerDao.insertExchanger(exchanger);
    }

}
