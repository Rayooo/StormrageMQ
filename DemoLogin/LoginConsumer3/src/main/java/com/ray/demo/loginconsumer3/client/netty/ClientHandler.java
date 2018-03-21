package com.ray.demo.loginconsumer3.client.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.demo.loginconsumer3.client.common.ConfirmMessage;
import com.ray.demo.loginconsumer3.client.common.Message;
import com.ray.demo.loginconsumer3.client.common.MessageTypeConstant;
import com.ray.demo.loginconsumer3.client.common.SendCount;
import com.ray.demo.loginconsumer3.client.util.JsonUtil;
import com.ray.demo.loginconsumer3.client.util.LogUtil;
import com.ray.demo.loginconsumer3.service.UserService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf>{

    @Autowired
    private NettyClient nettyClient;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private Environment env;

    @Autowired
    private ChannelHandlerService channelHandlerService;

    @Autowired
    private SendCount sendCount;

    @Autowired
    private ConfirmMessage confirmMessage;

    @Autowired
    private UserService userService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        LogUtil.logInfo("Client received: " + msg.toString(CharsetUtil.UTF_8));

        Message message = JsonUtil.toObject(msg.toString(CharsetUtil.UTF_8), Message.class);
        if(message == null){
            return;
        }

        if(MessageTypeConstant.HEARTBEAT_MESSAGE_TYPE.equals(message.getType())){
            LogUtil.logInfo("收到心跳消息，内容:" + message.getContent());
        }

        if(MessageTypeConstant.NORMAL_MESSAGE_TYPE.equals(message.getType())){
            //普通消息
            LogUtil.logInfo(message.getUuid() + "   " + message.getContent());

            try {
                userService.addPoint(message);
            }
            finally {
                sendCount.addSendCount();
            }

        }

        if(MessageTypeConstant.IMPORTANT_MESSAGE_TYPE.equals(message.getType())){
            LogUtil.logInfo(message.getUuid() + "  " + message.getConfirmId() + "  " + message.getContent());
            sendCount.addSendCount();

            LogUtil.logInfo("重要消息确认送达 队列消息id" + message.getConfirmId());
            confirmMessage.confirmMessage(message.getConfirmId());
        }


    }


    //初始化发送验证信息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Message message = new Message();
        message.setUuid(UUID.randomUUID().toString());
        message.setType(env.getProperty("message.type"));
        message.setUserName(env.getProperty("message.userName"));
        message.setPassword(env.getProperty("message.password"));
        message.setClientName(env.getProperty("message.clientName"));
        message.setClientType(Integer.parseInt(env.getProperty("message.clientType")));
        message.setQueueNameList(env.getProperty("message.queueNameList"));
        ObjectMapper mapper = new ObjectMapper();
        ctx.writeAndFlush(Unpooled.copiedBuffer(mapper.writeValueAsString(message), CharsetUtil.UTF_8));
        channelHandlerService.setCtx(ctx);

        executor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ignored) {
            }
            sendCount.addSendCount();

        });

    }

    //断开连接后
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {


        //断线重连机制
        nettyClient.reConnect();
    }
}
