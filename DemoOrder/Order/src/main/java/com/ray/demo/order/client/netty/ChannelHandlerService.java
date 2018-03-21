package com.ray.demo.order.client.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.demo.order.client.common.Message;
import com.ray.demo.order.client.util.LogUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChannelHandlerService {

    private ChannelHandlerContext ctx;

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public boolean sendMessage(Message message){
        if(ctx == null){
            return false;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            message.setUuid(UUID.randomUUID().toString());
            String s = mapper.writeValueAsString(message);
            LogUtil.logInfo("生产者发送消息：" + s);
            ctx.writeAndFlush(Unpooled.copiedBuffer(s, CharsetUtil.UTF_8));
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

}
