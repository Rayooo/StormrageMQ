package com.ray.stormragemq.service;

import com.ray.stormragemq.domain.ExchangerEntity;
import com.ray.stormragemq.util.BaseException;

import java.util.List;

public interface ExchangerService {

    void addExchanger(ExchangerEntity exchanger) throws BaseException;

    List<ExchangerEntity> getExchangerListByUser(int userId);

}
