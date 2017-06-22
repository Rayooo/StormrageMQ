package com.ray.stormragemq.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ray on 2017/6/22.
 */

@Configuration
@PropertySource("classpath:netty.properties")
public class NettyConfig {

    @Value("${tcp.port}")
    private int port;

    @Value("${boss.thread.count}")
    private int bossCount;

    @Value("${worker.thread.count}")
    private int workerCount;

    @Value("${so.keepAlive}")
    private boolean keepAlive;

    @Value("${so.backlog}")
    private int backlog;

    private final MasterChannelInitializer masterChannelInitializer;

    @Autowired
    public NettyConfig(MasterChannelInitializer masterChannelInitializer) {
        this.masterChannelInitializer = masterChannelInitializer;
    }


    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup(){
        return new NioEventLoopGroup(bossCount);
    }

    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup(){
        return new NioEventLoopGroup(workerCount);
    }

    //配置TCP
    @Bean(name = "tcpChannelOptions")
    public Map<ChannelOption, Object> tcpChannelOptions() {
        Map<ChannelOption, Object> options = new HashMap<>();
        options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
        options.put(ChannelOption.SO_BACKLOG, backlog);
        return options;
    }

    //配置bootstrap
    @Bean(name = "serverBootstrap")
    public ServerBootstrap bootstrap(){
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(masterChannelInitializer);

        Map<ChannelOption, Object> tcpChannelOptions = tcpChannelOptions();
        tcpChannelOptions.forEach(b::option);

        return b;
    }

    //配置端口
    @Bean(name = "tcpSocketAddress")
    public InetSocketAddress tcpPort(){
        return new InetSocketAddress(port);
    }



}
