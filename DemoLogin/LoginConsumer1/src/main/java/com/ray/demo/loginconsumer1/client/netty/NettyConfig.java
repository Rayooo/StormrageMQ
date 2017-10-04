package com.ray.demo.loginconsumer1.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

@Component
public class NettyConfig {

    @Value("${netty.remote.port}")
    private int port;

    @Value("${netty.remote.address}")
    private String address;

    @Value("${boss.thread.count}")
    private int bossCount;

    @Value("${so.keepAlive}")
    private boolean keepAlive;

    @Value("${so.backlog}")
    private int backlog;

    @Bean(name = "group", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup group(){
        return new NioEventLoopGroup(bossCount);
    }

    @Autowired
    private ClientHandler clientHandler;

    private Map<ChannelOption, Object> tcpChannelOptions() {
        Map<ChannelOption, Object> options = new HashMap<>();
        options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
        options.put(ChannelOption.SO_BACKLOG, backlog);
        return options;
    }

    @Bean
    public Bootstrap bootstrap(){
        Bootstrap b = new Bootstrap();
        b.group(group())
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(address, port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(clientHandler);
                    }
                });

        return b;
    }




}
