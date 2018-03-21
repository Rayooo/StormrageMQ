package com.ray.demo.loginconsumer2.service;

import com.ray.demo.loginconsumer2.client.common.Message;
import com.ray.demo.loginconsumer2.client.util.JsonUtil;
import com.ray.demo.loginconsumer2.client.util.LogUtil;
import com.ray.demo.loginconsumer2.dao.DemoUserDao;
import com.ray.demo.loginconsumer2.entity.DemoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private DemoUserDao demoUserDao;

    public void addPoint(Message message){

        if(message.getContent() == null || message.getCls() == null){
            LogUtil.logInfo("错误消息，已丢弃");
        }

        String content = message.getContent();
        DemoUser user = JsonUtil.toObject(content, DemoUser.class);

        if(user != null && user.getId() != null && user.getUserName() != null){
            user.setLastLoginTime(new Date());
            demoUserDao.updateUserLastLoginTime(user);
            LogUtil.logInfo("已为" + user.getUserName() + "修改了登录时间");
        }
    }

}
