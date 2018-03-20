package com.ray.stormragemq.controller;

import com.ray.stormragemq.entity.UserAccountEntity;
import com.ray.stormragemq.service.UserAccountService;
import com.ray.stormragemq.util.BaseException;
import com.ray.stormragemq.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by Ray on 2017/6/30.
 */
@RestController
@RequestMapping("/userAccount")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }


    @RequestMapping("/login")
    public BaseResponse<UserAccountEntity> login(@RequestBody UserAccountEntity user) throws Exception {
        return new BaseResponse<>(userAccountService.login(user));
    }


    @RequestMapping("/register")
    public BaseResponse<String> register(@RequestBody UserAccountEntity user) throws Exception {
        userAccountService.addUser(user);
        return new BaseResponse<>("Success");
    }

    @RequestMapping("/changePassword")
    public BaseResponse<String> changePassword(@RequestBody HashMap<String, String> param) throws BaseException {

        if(param.get("id") == null || param.get("userName") == null
            || param.get("password") == null || param.get("newPassword") == null){
            throw new BaseException("参数为空");
        }

        UserAccountEntity user = new UserAccountEntity();
        UserAccountEntity newUser = new UserAccountEntity();

        user.setId(Integer.parseInt(param.get("id")));
        user.setUserName(param.get("userName"));
        user.setPassword(param.get("password"));
        newUser.setId(Integer.parseInt(param.get("id")));
        newUser.setUserName(param.get("userName"));
        newUser.setPassword(param.get("newPassword"));

        userAccountService.changePassword(user, newUser);

        return new BaseResponse<>("success");
    }


}
