package com.ray.stormragemq.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;


/**
 * Created by Ray on 2017/6/22.
 * 用来开启Netty服务
 */
@Component
public class TCPClient {

    private final Bootstrap bootstrap;

    @Autowired
    public TCPClient(@Qualifier("bootstrap") Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }



    private SocketChannel socketChannel;

    public void start() throws InterruptedException {
        ChannelFuture future = bootstrap.connect().sync();
        if (future.isSuccess()) {
            socketChannel = (SocketChannel)future.channel();
        }
    }

    @PreDestroy
    public void stop() {
        socketChannel.close();
        socketChannel.parent().close();
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}
