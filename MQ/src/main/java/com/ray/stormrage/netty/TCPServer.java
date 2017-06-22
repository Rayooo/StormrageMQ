package com.ray.stormrage.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;


/**
 * Created by Ray on 2017/6/22.
 * 用来开启Netty服务
 */
@Component
public class TCPServer {

    private final ServerBootstrap serverBootstrap;

    private final InetSocketAddress tcpPort;


    @Autowired
    public TCPServer(@Qualifier("serverBootstrap") ServerBootstrap serverBootstrap, @Qualifier("tcpSocketAddress") InetSocketAddress tcpPort) {
        this.serverBootstrap = serverBootstrap;
        this.tcpPort = tcpPort;
    }



    private Channel serverChannel;

    public void start() throws InterruptedException {
        serverChannel = serverBootstrap.bind(tcpPort).sync().channel().closeFuture().sync().channel();
    }

    @PreDestroy
    public void stop() {
        serverChannel.close();
        serverChannel.parent().close();
    }


}
