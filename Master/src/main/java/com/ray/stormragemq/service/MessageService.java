package com.ray.stormragemq.service;

import com.ray.stormragemq.common.Message;

public interface MessageService {

    void saveMessage(Message message);

}
