package com.ray.demo.login.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class NettyClient {

    private final Bootstrap bootstrap;

    @Autowired
    public NettyClient(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    private SocketChannel socketChannel;

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void stop() {
        socketChannel.close();
        socketChannel.parent().close();
    }

    public void start() throws InterruptedException {
        ChannelFuture future = bootstrap.connect().sync();
        if(future.isSuccess()){
            socketChannel = (SocketChannel) future.channel();
            String address = future.channel().remoteAddress().toString();
            address = address.replaceAll("/", "");
            System.out.println(address);
        }
    }




}
