package com.ray.stormragemq.controller;

import com.ray.stormragemq.domain.UserAccountEntity;
import com.ray.stormragemq.service.UserAccountService;
import com.ray.stormragemq.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    public BaseResponse<UserAccountEntity> login(@RequestBody UserAccountEntity user) throws Exception {
        return new BaseResponse<>(userAccountService.login(user));
    }


    @RequestMapping("register")
    public BaseResponse<String> register(@RequestBody UserAccountEntity user) throws Exception {
        userAccountService.addUser(user);
        return new BaseResponse<>("Success");
    }

}
