package com.ray.demo.loginconsumer1.service;

import com.ray.demo.loginconsumer1.client.common.SendCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    public SendCount sendCount;

    @RequestMapping("/test")
    public String test(){
        sendCount.addSendCount();
        return "success";
    }

}
