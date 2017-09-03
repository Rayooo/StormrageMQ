package com.ray.stormragemq.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
        Map<String, ClientChannel> map = gatewayService.getChannels();
        map.forEach((s, ClientChannel) -> {
            ClientChannel.getSocketChannel().writeAndFlush(Unpooled.copiedBuffer("heartBeat", CharsetUtil.UTF_8));
        });
    }


}
