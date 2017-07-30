package com.ray.stormragemq.dao;

import com.ray.stormragemq.domain.ExchangerEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangerDao {

    void insertExchanger(ExchangerEntity exchanger);

    int countExchangerByName(ExchangerEntity exchanger);

}
