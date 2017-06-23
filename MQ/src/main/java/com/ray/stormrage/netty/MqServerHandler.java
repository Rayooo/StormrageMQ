package com.ray.stormrage.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Ray on 2017/6/22.
 */
@Component
@Qualifier("MqServerHandler")
@ChannelHandler.Sharable
public class MqServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("----active----");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received:" + in.toString(CharsetUtil.UTF_8));
        ctx.writeAndFlush(Unpooled.copiedBuffer(in.toString(CharsetUtil.UTF_8) + "哈哈哈哈哈哈", CharsetUtil.UTF_8));          //将接收到的消息写给发送者，不冲刷出战消息
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //通知ChannelInboundHandler 最后一次对channelRead()的调用是当前批量读取中的最后一条消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("channelReadComplete", CharsetUtil.UTF_8));
    }

}

