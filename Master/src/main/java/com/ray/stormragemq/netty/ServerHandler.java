package com.ray.stormragemq.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.stormragemq.common.Message;
import com.ray.stormragemq.service.UserAccountService;
import com.ray.stormragemq.util.BaseResponse;
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

    private final UserAccountService userAccountService;

    @Autowired
    public ServerHandler(GatewayService gatewayService, ThreadPoolTaskExecutor executor, UserAccountService userAccountService) {
        this.gatewayService = gatewayService;
        this.executor = executor;
        this.userAccountService = userAccountService;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String uuid = ctx.channel().id().asLongText();
        ClientChannel clientChannel = new ClientChannel();
        clientChannel.setSocketChannel((SocketChannel)ctx.channel());
        gatewayService.addGatewayChannel(uuid, clientChannel);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String uuid = ctx.channel().id().asLongText();
        gatewayService.removeGatewayChannel(uuid);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;

        executor.execute(() -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Message message = mapper.readValue(in.toString(CharsetUtil.UTF_8), Message.class);
                //普通消息
                if("1".equals(message.getType())){
                    System.out.println("普通消息");
                }

                //重要消息
                if("2".equals(message.getType())){
                    System.out.println("重要消息");
                }


                //验证初始化消息
                if("0".equals(message.getType())){
                    //密码错误情况下
                    if(!userAccountService.checkUser(message.getUserName(), message.getPassword())){
                        BaseResponse<String> response = new BaseResponse<>();
                        response.setMessage("密码错误");
                        ctx.writeAndFlush(Unpooled.copiedBuffer(mapper.writeValueAsString(response), CharsetUtil.UTF_8)).sync();
                        ctx.close();
                    }
                }



            } catch (IOException e) {
                e.printStackTrace();
                BaseResponse response = new BaseResponse();
                response.setError();
                ctx.writeAndFlush(response);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
