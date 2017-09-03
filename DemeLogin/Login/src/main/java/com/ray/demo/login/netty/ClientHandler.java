package com.ray.demo.login.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.demo.login.common.ClientTypeEnum;
import com.ray.demo.login.common.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf>{

    @Autowired
    private NettyClient nettyClient;

    @Autowired
    private ThreadPoolTaskExecutor executor;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("Client received: " + msg.toString(CharsetUtil.UTF_8));
    }


    //初始化发送验证信息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Message message = new Message();
        message.setUuid(UUID.randomUUID().toString());
        message.setType("0");
        message.setCreateTime(new Date());
        message.setUserName("ray");
        message.setPassword("123");
        message.setClientName("LoginProducer");
        message.setClientType(ClientTypeEnum.PRODUCER.getType());
        ObjectMapper mapper = new ObjectMapper();
        ctx.writeAndFlush(Unpooled.copiedBuffer(mapper.writeValueAsString(message), CharsetUtil.UTF_8));
    }

    //断开连接后
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {


        //断线重连机制
        nettyClient.reConnect();
    }
}
