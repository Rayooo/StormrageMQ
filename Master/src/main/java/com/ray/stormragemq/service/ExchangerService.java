package com.ray.stormragemq.service;

import com.ray.stormragemq.entity.ExchangerEntity;
import com.ray.stormragemq.entity.UserAccountEntity;
import com.ray.stormragemq.util.BaseException;

import java.util.List;

public interface ExchangerService {

    void addExchanger(ExchangerEntity exchanger) throws BaseException;

    List<ExchangerEntity> getExchangerListByUser(int userId);

    void deleteExchanger(ExchangerEntity exchanger, UserAccountEntity user) throws BaseException ;

    ExchangerEntity getExchanger(ExchangerEntity exchanger);

    void changeExchanger(ExchangerEntity exchanger) throws BaseException;
}
