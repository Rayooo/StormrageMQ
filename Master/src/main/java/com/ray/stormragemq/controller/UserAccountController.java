package com.ray.stormragemq.controller;

import com.ray.stormragemq.domain.UserAccountEntity;
import com.ray.stormragemq.service.UserAccountService;
import com.ray.stormragemq.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ray on 2017/6/30.
 */
@RestController
@RequestMapping("userAccount")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }


    @RequestMapping("login")
    public BaseResponse<UserAccountEntity> login(){
        UserAccountEntity user = new UserAccountEntity();
        user.setUserName("212133");
        return new BaseResponse<>(userAccountService.login(user));
    }


    @RequestMapping("addUser")
    public BaseResponse<String> addUser(){
        UserAccountEntity user = new UserAccountEntity();
        user.setUserName("212133");
        user.setPassword("123asd哈哈哈😂");
        userAccountService.addUser(user);
        return new BaseResponse<>("Success");
    }

}
