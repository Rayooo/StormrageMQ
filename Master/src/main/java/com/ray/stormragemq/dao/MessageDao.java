package com.ray.stormragemq.dao;

import com.ray.stormragemq.common.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao {

    void insertMessage(Message message);

    Boolean isExist(Message message);
}
