package com.ray.stormragemq.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Ray on 2017/6/22.
 */
@Component
@Qualifier("masterChannelInitializer")
public class MasterChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final MasterClientHandler masterClientHandler;

    @Autowired
    public MasterChannelInitializer(@Qualifier("masterClientHandler") MasterClientHandler masterClientHandler) {
        this.masterClientHandler = masterClientHandler;
    }


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline().addLast(masterClientHandler);

    }


}
