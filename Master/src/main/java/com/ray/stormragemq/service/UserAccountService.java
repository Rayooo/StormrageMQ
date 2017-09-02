package com.ray.stormragemq.service;

import com.ray.stormragemq.domain.UserAccountEntity;

/**
 * Created by Ray on 2017/6/30.
 */
public interface UserAccountService {

    UserAccountEntity login(UserAccountEntity user) throws Exception;

    void addUser(UserAccountEntity user) throws Exception;

    boolean checkUser(String userName, String password);
}
