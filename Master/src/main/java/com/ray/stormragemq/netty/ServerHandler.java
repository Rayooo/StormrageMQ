package com.ray.stormragemq.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.stormragemq.common.Message;
import com.ray.stormragemq.common.MessageTypeConstant;
import com.ray.stormragemq.constant.ClientTypeEnum;
import com.ray.stormragemq.netty.service.GatewayService;
import com.ray.stormragemq.netty.service.MessageHandlerService;
import com.ray.stormragemq.service.MessageService;
import com.ray.stormragemq.util.BaseException;
import com.ray.stormragemq.util.BaseResponse;
import com.ray.stormragemq.util.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter{

    private final GatewayService gatewayService;

    private final ThreadPoolTaskExecutor executor;

    private final MessageHandlerService messageHandlerService;

    private final MessageService messageService;

    @Autowired
    public ServerHandler(GatewayService gatewayService, ThreadPoolTaskExecutor executor, MessageHandlerService messageHandlerService, MessageService messageService) {
        this.gatewayService = gatewayService;
        this.executor = executor;
        this.messageHandlerService = messageHandlerService;
        this.messageService = messageService;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String uuid = ctx.channel().id().asLongText();
        ClientChannel clientChannel = new ClientChannel();
        clientChannel.setSocketChannel((SocketChannel)ctx.channel());
        gatewayService.addGatewayChannel(uuid, clientChannel);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String uuid = ctx.channel().id().asLongText();

        ClientChannel clientChannel = gatewayService.getGatewayChannel(uuid);
        if(clientChannel != null){
            if(ClientTypeEnum.CONSUMER.getType() == clientChannel.getClientType()){
                LogUtil.logInfo("消费者断开连接, 消费者名称: " + clientChannel.getName());
            }
            else{
                LogUtil.logInfo("生产者断开连接, 生产者名称: " + clientChannel.getName());
            }
        }

        gatewayService.removeGatewayChannel(uuid);
        gatewayService.removeConsumerUuid(uuid);
        gatewayService.syncConsumerName();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;

        executor.execute(() -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Message message = mapper.readValue(in.toString(CharsetUtil.UTF_8), Message.class);
                messageService.saveMessage(message);

                //普通消息
                if(MessageTypeConstant.NORMAL_MESSAGE_TYPE.equals(message.getType())){
                    LogUtil.logInfo("消息队列收到普通消息: " + message.getContent());
                    //处理消息
                    messageHandlerService.handleMessage(message, false);
                }

                //重要消息
                if(MessageTypeConstant.IMPORTANT_MESSAGE_TYPE.equals(message.getType())){
                    LogUtil.logInfo("消息队列收到重要消息: " + message.getContent());
                    //处理消息
                    messageHandlerService.handleMessage(message, true);
                }


                //验证初始化消息
                if(MessageTypeConstant.INIT_MESSAGE_TYPE.equals(message.getType())){
                    messageHandlerService.handleInitMessage(message, ctx);
                }


            } catch (IOException e) {
                e.printStackTrace();
                BaseResponse response = new BaseResponse();
                response.setError();
                ctx.writeAndFlush(Unpooled.copiedBuffer(response.toJson(), CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BaseException e) {
                e.printStackTrace();
                BaseResponse response = new BaseResponse();
                response.setError();
                response.setMessage(e.getMessage());
                ctx.writeAndFlush(Unpooled.copiedBuffer(response.toJson(), CharsetUtil.UTF_8));
            } finally {
                in.release();
            }
        });


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


}
