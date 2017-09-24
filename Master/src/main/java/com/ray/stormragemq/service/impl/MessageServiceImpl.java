package com.ray.stormragemq.service.impl;

import com.ray.stormragemq.common.Message;
import com.ray.stormragemq.dao.MessageDao;
import com.ray.stormragemq.service.MessageService;
import com.ray.stormragemq.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageDao messageDao;

    @Autowired
    public MessageServiceImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Async
    @Transactional
    @Override
    public void saveMessage(Message message) {
        if(!isExist(message)){
            messageDao.insertMessage(message);
        }
    }

    private boolean isExist(Message message){
        return messageDao.isExist(message);
    }

}
