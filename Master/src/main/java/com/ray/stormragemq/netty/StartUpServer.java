package com.ray.stormragemq.netty;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

//启动Netty
@Component
public class StartUpServer {

    private final ThreadPoolTaskExecutor executor;

    private final NettyServer nettyServer;

    @Autowired
    public StartUpServer(NettyServer nettyServer, ThreadPoolTaskExecutor executor) {
        this.nettyServer = nettyServer;
        this.executor = executor;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshEvent(){
        executor.execute(() -> {
            try {
                nettyServer.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }




}
