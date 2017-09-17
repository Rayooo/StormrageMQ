package com.ray.demo.login.client.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.demo.login.client.common.Message;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Service;

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
            String s = mapper.writeValueAsString(message);
            ctx.writeAndFlush(Unpooled.copiedBuffer(s, CharsetUtil.UTF_8));
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

}
