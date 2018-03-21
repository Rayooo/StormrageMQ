package com.ray.demo.loginconsumer1.dao;

import com.ray.demo.loginconsumer1.entity.DemoUser;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoUserDao {

    DemoUser getUserByUserName(DemoUser user);

    void updateUserPoints(DemoUser user);

}
