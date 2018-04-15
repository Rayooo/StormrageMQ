package com.ray.demo.order.service;

import com.ray.demo.order.client.common.Message;
import com.ray.demo.order.client.common.MessageTypeConstant;
import com.ray.demo.order.client.netty.ChannelHandlerService;
import com.ray.demo.order.entity.Order;
import com.ray.demo.order.util.JsonUtil;
import com.ray.demo.order.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {

    @Autowired
    private Map<String, Order> orderMap;

    @Autowired
    private AtomicInteger finishedOrderOne;

    @Autowired
    private AtomicInteger finishedOrderTwo;

    @Autowired
    private AtomicInteger sendOrder;

    @Autowired
    private ChannelHandlerService channelHandlerService;

    private final static Integer ORDER_NUM = 40;

    public void initOrder(){

        sendOrder.set(0);
        finishedOrderOne.set(0);
        finishedOrderTwo.set(0);

        orderMap = new ConcurrentHashMap<>();

        Date now = new Date();
        for (int i = 0; i < ORDER_NUM; i++) {
            Order order = new Order();
            order.setUuid(UUID.randomUUID().toString());
            String content = "qd5WkFLM97Lq2WsKowUizqMTxXIdOPENBieb16YnXzA5bYa8CZueABGWTkOSf4AbawFf4yceyd5tUy9rc" +
                    "U5x1iAR6fODleQQWPHxjGdWGo6p6VverGM1W5v4xxc14CUqdM3rs3gUPaAMiyngWb1QLcI9V4oF28xebNgjU2EDw6W" +
                    "wdJAitH8lH5RFUCpj5BdGKJ2NEDyNt6h6QbXDAaTiBYqrBymfBszqiDDFwruLGT6vPMaUwhxeSc9d711A354B37sZS" +
                    "c2YYsfdDqJaABHCBX46BrwCVbyteSgM7ZGr8vs3PhIpMSJNaLCqInYDSprx3Dj2JCmXdEH4zyFPSKtFGyl6kSnorJZ" +
                    "345c8Awu5opsQ6GxQPIK5YdzfV1XNfSY9OchQE1uzXZfEqCJRsbaLwX6fV1qc8u3NQ20u5bqyppmdIuE7wfiVMGywrcVJ86b8DEvzsDgBrpmQ6JUxe";
            order.setContent(JsonUtil.toJson(content));
            order.setCreateTime(now);
            orderMap.put(order.getUuid(), order);
        }

    }

    public void addOrderOne(){
        finishedOrderOne.incrementAndGet();
    }

    public void addOrderTwo(){
        finishedOrderTwo.incrementAndGet();
    }

    public Map<String, Integer> getOrderInfo(){

        int sendOrderInt = sendOrder.intValue();
        Map<String, Integer> map = new HashMap<>();
        map.put("order", orderMap.size() - sendOrderInt);
        map.put("sendOrder", sendOrderInt);
        map.put("finishOne", finishedOrderOne.intValue());
        map.put("finishTwo", finishedOrderTwo.intValue());

        return map;
    }

    public void startSendOrder(){
        orderMap.forEach((s, order) -> {

            Message message = new Message();
            message.setType(MessageTypeConstant.IMPORTANT_MESSAGE_TYPE);
            message.setExchangerName("demo.order");
            message.setContent(JsonUtil.toJson(order));

            channelHandlerService.sendMessage(message);

            sendOrder.incrementAndGet();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }

}
