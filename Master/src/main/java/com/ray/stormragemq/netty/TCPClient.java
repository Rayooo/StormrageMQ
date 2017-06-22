package com.ray.stormragemq.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
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



    private Channel serverChannel;

    public void start() throws InterruptedException {
        serverChannel = bootstrap.connect().sync().channel().closeFuture().sync().channel();
    }

    @PreDestroy
    public void stop() {
        serverChannel.close();
        serverChannel.parent().close();
    }


}
