package com.ray.stormragemq.netty.service;

import com.ray.stormragemq.common.Message;
import com.ray.stormragemq.common.MessageTypeConstant;
import com.ray.stormragemq.netty.ClientChannel;
import com.ray.stormragemq.util.JsonUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@EnableScheduling
public class HeartBeatService {

    private final GatewayService gatewayService;

    @Autowired
    public HeartBeatService(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @Scheduled(cron = "*/20 * * * * *")
    public void sendHeartBeat(){

        Message message = new Message();
        message.setType(MessageTypeConstant.HEARTBEAT_MESSAGE_TYPE);
        message.setCreateTime(new Date());
        message.setContent("HeartBeat");
        String heartBeatMessage = JsonUtil.toJson(message);

        Map<String, ClientChannel> map = gatewayService.getChannels();
        map.forEach((s, ClientChannel) -> {
            ClientChannel.getSocketChannel().writeAndFlush(Unpooled.copiedBuffer(heartBeatMessage, CharsetUtil.UTF_8));
        });
    }


}
