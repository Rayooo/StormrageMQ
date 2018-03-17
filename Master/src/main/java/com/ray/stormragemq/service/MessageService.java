package com.ray.stormragemq.service;

import com.ray.stormragemq.common.Message;
import com.ray.stormragemq.util.PageBean;

public interface MessageService {

    void saveMessage(Message message);

    PageBean<Message> getMessage(int pageIndex);

}
