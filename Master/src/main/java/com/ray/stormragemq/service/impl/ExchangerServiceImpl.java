package com.ray.stormragemq.service.impl;

import com.ray.stormragemq.config.Refresh;
import com.ray.stormragemq.dao.ExchangerDao;
import com.ray.stormragemq.domain.ExchangerEntity;
import com.ray.stormragemq.domain.UserAccountEntity;
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

    private final Refresh refresh;

    @Autowired
    public ExchangerServiceImpl(ExchangerDao exchangerDao, Refresh refresh) {
        this.exchangerDao = exchangerDao;
        this.refresh = refresh;
    }

    @Transactional
    @Override
    public void addExchanger(ExchangerEntity exchanger) throws BaseException {
        if(canAddExchanger(exchanger)){
            exchangerDao.insertExchanger(exchanger);
            refresh.contextRefreshEvent();
        }
        else{
            throw new BaseException("系统中已存在相同名字交换器");
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
    public void deleteExchanger(ExchangerEntity exchanger, UserAccountEntity user) throws BaseException {
        exchanger = getExchanger(exchanger);
        if(exchanger.getCreateUserId().equals(user.getId())){
            if(exchangerDao.deleteExchangerById(exchanger) == 1){
                refresh.contextRefreshEvent();
            }
            else{
                throw new BaseException("删除失败");
            }
        }
        else{
            throw new BaseException("无法删除不是自己创建的交换器");
        }

    }

    @Override
    public ExchangerEntity getExchanger(ExchangerEntity exchanger) {
        return exchangerDao.getExchanger(exchanger);
    }

    @Override
    @Transactional
    public void changeExchanger(ExchangerEntity exchanger) throws BaseException {
        if(canAddExchanger(exchanger)){
            if(exchangerDao.updateExchanger(exchanger) == 1){
                refresh.contextRefreshEvent();
            }
            else {
                throw new BaseException("更新失败");
            }
        }
        else{
            throw new BaseException("不能添加两个相同名字的交换器");
        }
    }
}
