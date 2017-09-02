package com.ray.demo.login.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class NettyClient {

    @Autowired
    private Bootstrap bootstrap;


    private SocketChannel socketChannel;

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void stop() {
        socketChannel.close();
        socketChannel.parent().close();
    }

    public void start() throws InterruptedException {
        ChannelFuture future = bootstrap.connect();

        future.addListener((ChannelFutureListener) channelFuture -> {
            if(channelFuture.isSuccess()){
                System.out.println("连接成功");
                socketChannel = (SocketChannel) channelFuture.channel();
            }
            else{
                System.out.println("连接失败，重试");
                channelFuture.channel().eventLoop().schedule(this::reConnect, 20, TimeUnit.SECONDS);
            }
        });
    }

    public void reConnect() {
        try {
            start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




}
