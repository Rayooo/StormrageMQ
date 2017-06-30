package com.ray.stormragemq.dao;

import com.ray.stormragemq.domain.UserAccountEntity;
import org.springframework.stereotype.Component;

/**
 * Created by Ray on 2017/6/30.
 */
@Component
public interface UserAccountDao {

    UserAccountEntity getUserByUserName(UserAccountEntity user);

    void insertUser(UserAccountEntity user);

}
