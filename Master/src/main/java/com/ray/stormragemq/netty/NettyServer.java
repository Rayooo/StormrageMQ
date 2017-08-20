package com.ray.stormragemq.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

//开启netty服务
@Component
public class NettyServer {

    @Value("${tcp.port}")
    private int port;

    private final ServerBootstrap serverBootstrap;

    @Autowired
    public NettyServer(ServerBootstrap serverBootstrap) {
        this.serverBootstrap = serverBootstrap;
    }

    //配置端口
    public InetSocketAddress tcpPort(){
        return new InetSocketAddress(port);
    }

    private Channel serverChannel;

    public void start() throws InterruptedException {
        serverChannel = serverBootstrap.bind(port).sync().channel().closeFuture().sync().channel();
    }



}
