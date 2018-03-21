package com.ray.demo.order.client.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.demo.order.client.common.Message;
import com.ray.demo.order.client.common.MessageTypeConstant;
import com.ray.demo.order.client.util.LogUtil;
import com.ray.demo.order.util.JsonUtil;
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

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        try {
            Message message = JsonUtil.toObject(msg.toString(CharsetUtil.UTF_8), Message.class);
            if(message == null){
                return;
            }
            if(MessageTypeConstant.HEARTBEAT_MESSAGE_TYPE.equals(message.getType())){
                LogUtil.logInfo("收到心跳消息");
            }else{
                LogUtil.logInfo("消费者收到消息: " + message.getUuid() + "   " + message.getContent());
            }

        }catch (Exception e){
            e.printStackTrace();
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
        ObjectMapper mapper = new ObjectMapper();
        ctx.writeAndFlush(Unpooled.copiedBuffer(mapper.writeValueAsString(message), CharsetUtil.UTF_8));
        channelHandlerService.setCtx(ctx);
    }

    //断开连接后
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {


        //断线重连机制
        nettyClient.reConnect();
    }
}
