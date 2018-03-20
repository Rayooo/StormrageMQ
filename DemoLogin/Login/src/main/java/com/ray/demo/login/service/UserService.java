package com.ray.demo.login.service;

import com.ray.demo.login.dao.DemoUserDao;
import com.ray.demo.login.entity.DemoUser;
import com.ray.demo.login.util.BaseException;
import com.ray.demo.login.util.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private DemoUserDao demoUserDao;

    public DemoUser login(DemoUser user) throws BaseException {
        DemoUser databaseUser = demoUserDao.getUserByUserName(user);
        try {
            if(databaseUser != null && Password.check(user.getPassword(), databaseUser.getPassword())){
                return databaseUser;
            }
            else{
                throw new BaseException("用户名或密码错误");
            }
        } catch (Exception e) {
            throw new BaseException("用户名或密码错误");
        }


    }
    

}
