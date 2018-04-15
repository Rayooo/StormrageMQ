package com.ray.stormragemq.service.impl;

import com.ray.stormragemq.common.Message;
import com.ray.stormragemq.dao.MessageDao;
import com.ray.stormragemq.service.MessageService;
import com.ray.stormragemq.util.LogUtil;
import com.ray.stormragemq.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public PageBean<Message> getMessage(int pageIndex){
        if(pageIndex <= 0){
            pageIndex = 1;
        }

        PageBean<Message> pageBean = new PageBean<>();
        int pageSize = pageBean.getPageSize();
        int totalCount = messageDao.getMessageCount();
        pageBean.setTotalRecords(totalCount);

        //如果超过最大页数
        if(pageIndex > pageBean.getTotalPages()){
            pageIndex = pageBean.getTotalPages();
            if(pageIndex <= 0){
                pageIndex = 1;
            }
        }

        Map<String, Object> param = new HashMap<>();
        int start = (pageIndex - 1) * pageSize;
        param.put("start", start);
        param.put("pageSize", pageSize);
        List list = messageDao.getMessagePage(param);
        pageBean.setPageData(list);

        return pageBean;
    }

}
