package com.ray.demo.loginconsumer2.dao;

import com.ray.demo.loginconsumer2.entity.DemoUser;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoUserDao {

    DemoUser getUserByUserName(DemoUser user);

    void updateUserLastLoginTime(DemoUser user);

}
