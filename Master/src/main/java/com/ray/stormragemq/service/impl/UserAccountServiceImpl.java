package com.ray.stormragemq.service.impl;

import com.ray.stormragemq.dao.UserAccountDao;
import com.ray.stormragemq.entity.UserAccountEntity;
import com.ray.stormragemq.service.UserAccountService;
import com.ray.stormragemq.util.BaseException;
import com.ray.stormragemq.util.Password;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;

/**
 * Created by Ray on 2017/6/30.
 */
@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountDao userAccountDao;

    @Autowired
    private final Key key;

    @Autowired
    public UserAccountServiceImpl(UserAccountDao userAccountDao, Key key) {
        this.userAccountDao = userAccountDao;
        this.key = key;
    }

    @Override
    public UserAccountEntity login(UserAccountEntity user) throws Exception {
        UserAccountEntity databaseUser = userAccountDao.getUserByUserName(user);
        if(databaseUser != null && Password.check(user.getPassword(), databaseUser.getPassword())){
            String compactJws = Jwts.builder()
                    .setSubject(databaseUser.getUserName())
                    .setId(databaseUser.getId().toString())
                    .signWith(SignatureAlgorithm.HS512, key)
                    .compact();
            //返回token
            databaseUser.setLoginToken(compactJws);
            userAccountDao.updateUser(databaseUser);
            return databaseUser;
        }
        else{
            throw new BaseException("用户名或密码错误");
        }

    }

    @Override
    @Transactional
    public void addUser(UserAccountEntity user) throws Exception {
        user.setPassword(Password.getSaltedHash(user.getPassword()));
        userAccountDao.insertUser(user);
    }

    @Override
    public boolean checkUser(String userName, String password) {
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
            return false;
        }

        UserAccountEntity user = new UserAccountEntity();
        user.setUserName(userName);
        UserAccountEntity databaseUser = userAccountDao.getUserByUserName(user);

        try {
            return databaseUser != null && Password.check(password, databaseUser.getPassword());
        } catch (Exception ignore) {}

        return false;
    }
}
