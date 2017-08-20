package com.ray.demo.login.netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class StartUpClient {

    private final NettyClient nettyClient;

    private final ThreadPoolTaskExecutor executor;

    @Autowired
    public StartUpClient(NettyClient nettyClient, ThreadPoolTaskExecutor executor) {
        this.nettyClient = nettyClient;
        this.executor = executor;
    }


    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshEvent(){
        executor.execute(() -> {
            try {
                nettyClient.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
