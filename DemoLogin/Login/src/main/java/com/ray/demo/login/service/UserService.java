package com.ray.demo.login.service;

import com.ray.demo.login.client.common.Message;
import com.ray.demo.login.client.common.MessageTypeConstant;
import com.ray.demo.login.client.netty.ChannelHandlerService;
import com.ray.demo.login.dao.DemoUserDao;
import com.ray.demo.login.entity.DemoUser;
import com.ray.demo.login.util.BaseException;
import com.ray.demo.login.util.JsonUtil;
import com.ray.demo.login.util.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private DemoUserDao demoUserDao;

    @Autowired
    private ChannelHandlerService channelHandlerService;


    public DemoUser login(DemoUser user) throws BaseException {
        DemoUser databaseUser = demoUserDao.getUserByUserName(user);
        try {
            if(databaseUser != null && Password.check(user.getPassword(), databaseUser.getPassword())){
                sendLoginMessage(databaseUser);
                return databaseUser;
            }
            else{
                throw new BaseException("用户名或密码错误");
            }
        } catch (Exception e) {
            throw new BaseException("用户名或密码错误");
        }
    }

    private void sendLoginMessage(DemoUser user){

        Message message = new Message();
        message.setType(MessageTypeConstant.NORMAL_MESSAGE_TYPE);
        message.setExchangerName("demo.login");
        message.setContent(JsonUtil.toJson(user));
        message.setCls("DemoUser");

        channelHandlerService.sendMessage(message);

    }

}
