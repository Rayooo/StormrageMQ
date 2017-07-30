package com.ray.stormragemq.dao;

import com.ray.stormragemq.domain.ExchangerEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface ExchangerDao {

    void insertExchanger(ExchangerEntity exchanger);

    int countExchangerByName(ExchangerEntity exchanger);

    List<ExchangerEntity> getExchangerList(Map param);
}
