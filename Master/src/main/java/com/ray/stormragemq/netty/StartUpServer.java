package com.ray.stormragemq.netty;


import com.ray.stormragemq.netty.service.GatewayService;
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

    private final GatewayService gatewayService;

    @Autowired
    public StartUpServer(NettyServer nettyServer, ThreadPoolTaskExecutor executor, GatewayService gatewayService) {
        this.nettyServer = nettyServer;
        this.executor = executor;
        this.gatewayService = gatewayService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshEvent(){
        executor.execute(() -> {
            Thread.currentThread().setName("NettyServer");
            try {
                nettyServer.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }




}
