package com.ray.demo.orderconsumer2.service;

import com.ray.demo.orderconsumer2.client.common.Message;
import com.ray.demo.orderconsumer2.client.common.SendCount;
import com.ray.demo.orderconsumer2.client.util.JsonUtil;
import com.ray.demo.orderconsumer2.client.util.LogUtil;
import com.ray.demo.orderconsumer2.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService {

    @Value("${producer.host}")
    private String host;

    @Value("${producer.port}")
    private String port;

    @Value("${producer.api}")
    private String api;

    @Value("${producer.consumerNo}")
    private String consumerNo;



    public void confirmOrder(){
        String url = "http://" + host + ":" + port + api;

        Map<String, String> map = new HashMap<>();
        map.put("id", consumerNo);
        String param = JsonUtil.toJson(map);

        LogUtil.logInfo("确认订单" + SendCount.executePost(url, param));
    }

    public void handleMessage(Message message){
        Order order = JsonUtil.toObject(message.getContent(), Order.class);

        if(order != null && order.getUuid() != null){
            LogUtil.logInfo("消费者处理订单，订单ID: " + order.getUuid());

            try {
                //消费者处理一个订单要3秒
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            confirmOrder();
        }

    }


}
