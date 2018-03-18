package com.ray.demo.loginconsumer1.client.common;

import com.ray.demo.loginconsumer1.client.util.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConfirmMessage {

    @Value("${netty.remote.address}")
    private String address;

    @Value("${message.server.port}")
    private String port;

    @Value("${message.clientName}")
    private String clientName;


    public void confirmMessage(String queueMessageId){

        Map<String, String> map = new HashMap<>();
        map.put("queueMessageId", queueMessageId);

        String param = JsonUtil.toJson(map);
        String url = "http://" + address + ":" + port + "/consumer/confirm";
        SendCount.executePost(url, param);

    }

}
