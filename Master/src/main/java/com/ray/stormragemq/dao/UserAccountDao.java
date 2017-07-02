package com.ray.stormragemq.dao;

import com.ray.stormragemq.domain.UserAccountEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by Ray on 2017/6/30.
 */
@Repository
public interface UserAccountDao {

    UserAccountEntity getUserByUserName(UserAccountEntity user);

    void insertUser(UserAccountEntity user);

    void updateUser(UserAccountEntity user);

}
