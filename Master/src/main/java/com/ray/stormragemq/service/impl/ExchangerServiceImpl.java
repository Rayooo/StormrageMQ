package com.ray.stormragemq.service.impl;

import com.ray.stormragemq.dao.ExchangerDao;
import com.ray.stormragemq.domain.ExchangerEntity;
import com.ray.stormragemq.service.ExchangerService;
import com.ray.stormragemq.util.BaseException;
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
    public void addExchanger(ExchangerEntity exchanger) throws BaseException {
        if(canAddExchanger(exchanger)){
            exchangerDao.insertExchanger(exchanger);
        }
        else{
            throw new BaseException("不能添加两个相同名字的交换器");
        }

    }

    private boolean canAddExchanger(ExchangerEntity exchanger){
        return exchangerDao.countExchangerByName(exchanger) < 1;
    }

}
