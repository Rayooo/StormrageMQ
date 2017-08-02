package com.ray.stormragemq.service.impl;

import com.ray.stormragemq.dao.ExchangerDao;
import com.ray.stormragemq.domain.ExchangerEntity;
import com.ray.stormragemq.service.ExchangerService;
import com.ray.stormragemq.util.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExchangerServiceImpl implements ExchangerService {

    private final ExchangerDao exchangerDao;

    private final Map<String, ExchangerEntity> exchangerMap;

    @Autowired
    public ExchangerServiceImpl(ExchangerDao exchangerDao, Map<String, ExchangerEntity> exchangerMap) {
        this.exchangerDao = exchangerDao;
        this.exchangerMap = exchangerMap;
    }

    @Transactional
    @Override
    public void addExchanger(ExchangerEntity exchanger) throws BaseException {
        if(canAddExchanger(exchanger)){
            exchangerDao.insertExchanger(exchanger);
            exchangerMap.put(exchanger.getName(), exchanger);
        }
        else{
            throw new BaseException("不能添加两个相同名字的交换器");
        }

    }

    @Override
    public List<ExchangerEntity> getExchangerListByUser(int userId) {
        Map<String, Integer> param = new HashMap<>();
        param.put("userId", userId);
        return exchangerDao.getExchangerList(param);
    }

    private boolean canAddExchanger(ExchangerEntity exchanger){
        return exchangerDao.countExchangerByName(exchanger) < 1;
    }

    @Transactional
    @Override
    public void deleteExchanger(ExchangerEntity exchanger) {
        exchanger = getExchanger(exchanger);
        if(exchangerDao.deleteExchangerById(exchanger) == 1){
            exchangerMap.remove(exchanger.getName());
        }
    }

    @Override
    public ExchangerEntity getExchanger(ExchangerEntity exchanger) {
        return exchangerDao.getExchanger(exchanger);
    }

    @Override
    public void changeExchanger(ExchangerEntity exchanger) throws BaseException {
        ExchangerEntity before = getExchanger(new ExchangerEntity(exchanger.getId()));
        if(canAddExchanger(exchanger)){
            if(exchangerDao.updateExchanger(exchanger) == 1){
                exchangerMap.remove(before.getName());
                exchangerMap.put(exchanger.getName(), exchanger);
            }
        }
        else{
            throw new BaseException("不能添加两个相同名字的交换器");
        }
    }
}
