package com.ray.stormragemq.service.impl;

import com.ray.stormragemq.dao.UserAccountDao;
import com.ray.stormragemq.domain.UserAccountEntity;
import com.ray.stormragemq.service.UserAccountService;
import com.ray.stormragemq.util.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Ray on 2017/6/30.
 */
@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountDao userAccountDao;

    @Autowired
    public UserAccountServiceImpl(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    @Override
    public UserAccountEntity login(UserAccountEntity user) {
        return userAccountDao.getUserByUserName(user);
    }

    @Override
    @Transactional
    public void addUser(UserAccountEntity user) throws Exception {
        user.setPassword(Password.getSaltedHash(user.getPassword()));
        userAccountDao.insertUser(user);
    }

}
