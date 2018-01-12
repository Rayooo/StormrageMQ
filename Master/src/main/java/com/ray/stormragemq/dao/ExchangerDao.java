package com.ray.stormragemq.dao;

import com.ray.stormragemq.entity.ExchangerEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ExchangerDao {

    void insertExchanger(ExchangerEntity exchanger);

    int countExchangerByName(ExchangerEntity exchanger);

    List<ExchangerEntity> getExchangerList(Map param);

    List<ExchangerEntity> getAllExchanger();

    int deleteExchangerById(ExchangerEntity exchanger);

    ExchangerEntity getExchanger(ExchangerEntity exchanger);

    int updateExchanger(ExchangerEntity exchanger);
}
