package com.ray.demo.login.dao;

import com.ray.demo.login.entity.DemoUser;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoUserDao {

    DemoUser getUserByUserName(DemoUser user);


}
