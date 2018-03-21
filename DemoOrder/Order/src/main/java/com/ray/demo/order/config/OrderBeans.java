package com.ray.demo.order.config;

import com.ray.demo.order.entity.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class OrderBeans {

    @Bean
    public Map<String, Order> orderMap(){
        return new ConcurrentHashMap<>(1000);
    }

    @Bean
    public AtomicInteger sendOrder(){
        return new AtomicInteger(0);
    }

    @Bean
    public AtomicInteger finishedOrderOne(){
        return new AtomicInteger(0);
    }

    @Bean
    public AtomicInteger finishedOrderTwo(){
        return new AtomicInteger(0);
    }


}
