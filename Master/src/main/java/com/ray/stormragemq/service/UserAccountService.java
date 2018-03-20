package com.ray.stormragemq.service;

import com.ray.stormragemq.entity.UserAccountEntity;
import com.ray.stormragemq.util.BaseException;

/**
 * Created by Ray on 2017/6/30.
 */
public interface UserAccountService {

    UserAccountEntity login(UserAccountEntity user) throws Exception;

    void addUser(UserAccountEntity user) throws Exception;

    boolean checkUser(String userName, String password);

    void changePassword(UserAccountEntity user, UserAccountEntity newUser) throws BaseException;

}
