package com.ray.stormragemq.service;

import com.ray.stormragemq.domain.ExchangerEntity;
import com.ray.stormragemq.util.BaseException;

public interface ExchangerService {

    void addExchanger(ExchangerEntity exchanger) throws BaseException;

}
