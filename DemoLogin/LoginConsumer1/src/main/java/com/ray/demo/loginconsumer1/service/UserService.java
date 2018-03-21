package com.ray.demo.loginconsumer1.service;

import com.ray.demo.loginconsumer1.client.common.Message;
import com.ray.demo.loginconsumer1.client.util.JsonUtil;
import com.ray.demo.loginconsumer1.client.util.LogUtil;
import com.ray.demo.loginconsumer1.dao.DemoUserDao;
import com.ray.demo.loginconsumer1.entity.DemoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            demoUserDao.updateUserPoints(user);
            LogUtil.logInfo("已为" + user.getUserName() + "增加了积分");
        }
    }

}
