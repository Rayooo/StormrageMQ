package com.ray.stormragemq.dao;

import com.ray.stormragemq.common.Message;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

@Repository
public interface MessageDao {

    void insertMessage(Message message);

    Boolean isExist(Message message);

    int sendMessageCount(Map<String, Object> param);
}
