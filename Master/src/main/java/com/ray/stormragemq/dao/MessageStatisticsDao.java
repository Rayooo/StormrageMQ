package com.ray.stormragemq.dao;

import com.ray.stormragemq.entity.MessageStatisticsEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

@Repository
public interface MessageStatisticsDao {

    void insertStatistics(MessageStatisticsEntity messageStatisticsEntity);

    void deleteStatistics(Map<String, Object> param);

    int countSendQueueMessage(Map<String, Date> param);

    int countUnSendQueueMessage(Map<String, Date> param);

}
