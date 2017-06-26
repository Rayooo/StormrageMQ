package com.ray.stormragemq.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.SocketChannel;


/**
 * Created by Ray on 2017/6/22.
 * 用来开启Netty服务
 */

public class TCPClient {

    private final Bootstrap bootstrap;

    public TCPClient(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    private SocketChannel socketChannel;

    private String address;

    public void start() throws InterruptedException {
        ChannelFuture future = bootstrap.connect().sync();
        if (future.isSuccess()) {
            socketChannel = (SocketChannel)future.channel();
            address = future.channel().remoteAddress().toString();
            address = address.replaceAll("/", "");
            System.out.println(address);
        }
    }

    public void stop() {
        socketChannel.close();
        socketChannel.parent().close();
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public String getAddress() {
        return address;
    }
}
