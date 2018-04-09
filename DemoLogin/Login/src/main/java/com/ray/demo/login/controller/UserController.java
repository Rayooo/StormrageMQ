package com.ray.demo.login.controller;

import com.ray.demo.login.entity.DemoUser;
import com.ray.demo.login.service.UserService;
import com.ray.demo.login.util.BaseException;
import com.ray.demo.login.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public BaseResponse<DemoUser> login(DemoUser demoUser) throws BaseException {

        DemoUser user = userService.login(demoUser);
        return new BaseResponse<>(user);

    }


    @RequestMapping("/getUser")
    public BaseResponse<DemoUser> getUser(DemoUser demoUser) throws BaseException {

        DemoUser user = userService.getUserInfo(demoUser);
        if(user == null){
            throw new BaseException("用户不存在");
        }

        return new BaseResponse<>(user);

    }

}
