package com.ray.demo.loginconsumer3.dao;

import com.ray.demo.loginconsumer3.entity.DemoUser;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoUserDao {

    DemoUser getUserByUserName(DemoUser user);

    void updateUserTicket(DemoUser user);

}
