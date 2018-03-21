package com.ray.demo.order.service;

import com.ray.demo.order.client.common.Message;
import com.ray.demo.order.client.common.MessageTypeConstant;
import com.ray.demo.order.client.netty.ChannelHandlerService;
import com.ray.demo.order.entity.Order;
import com.ray.demo.order.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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

    private final static Integer ORDER_NUM = 5;

    public void initOrder(){

        sendOrder.set(0);
        finishedOrderOne.set(0);
        finishedOrderTwo.set(0);

        orderMap.clear();
        Date now = new Date();
        for (int i = 0; i < ORDER_NUM; i++) {
            Order order = new Order();
            order.setUuid(UUID.randomUUID().toString());
            order.setContent(Integer.toString(i));
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
