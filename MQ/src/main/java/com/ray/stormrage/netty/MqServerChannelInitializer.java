package com.ray.stormrage.netty;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Ray on 2017/6/22.
 */
@Component
@Qualifier("mqServerChannelInitializer")
public class MqServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final ChannelInboundHandlerAdapter mqServerHandler;

    @Autowired
    public MqServerChannelInitializer(ChannelInboundHandlerAdapter mqServerHandler) {
        this.mqServerHandler = mqServerHandler;
    }


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(mqServerHandler);

    }


}
