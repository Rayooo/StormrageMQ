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
import java.util.HashMap;
import java.util.Map;

@Service
@EnableScheduling
public class HeartBeatService {

    private final GatewayService gatewayService;

    @Autowired
    public HeartBeatService(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

//    @Scheduled(cron = "*/10 * * * * *")
    public void sendHeartBeat(){

        Message message = new Message();
        message.setType(MessageTypeConstant.HEARTBEAT_MESSAGE_TYPE);
        message.setCreateTime(new Date());

        Map<String, Object> p = new HashMap<>();
        p.put("heartBeat", "heartBeat");
        p.put("uuid", "XbnqKfGvAxuS51US405ZhYhIOtZJcUf4F8nadVInVoU=$J5d1fFuVJxnHhkV+EfO6GkQ7GTcEHLPopVPtHPa+Tn8=");
        p.put("uuid2", "XbnqKfGvAxuS51US405ZhYhIOtZJcUf4F8nadVInVoU=$J5d1fFuVJxnHhkV+EfO6GkQ7GTcEHLPopVPtHPa+Tn8=");
        p.put("uuid3", "XbnqKfGvAxuS51US405ZhYhIOtZJcUf4F8nadVInVoU=$J5d1fFuVJxnHhkV+EfO6GkQ7GTcEHLPopVPtHPa+Tn8=");
        p.put("uuid4", "XbnqKfGvAxuS51US405ZhYhIOtZJcUf4F8nadVInVoU=$J5d1fFuVJxnHhkV+EfO6GkQ7GTcEHLPopVPtHPa+Tn8=");
        p.put("uuid5", "XbnqKfGvAxuS51US405ZhYhIOtZJcUf4F8nadVInVoU=$J5d1fFuVJxnHhkV+EfO6GkQ7GTcEHLPopVPtHPa+Tn8=");
        p.put("uuid6", "XbnqKfGvAxuS51US405ZhYhIOtZJcUf4F8nadVInVoU=$J5d1fFuVJxnHhkV+EfO6GkQ7GTcEHLPopVPtHPa+Tn8=");
        message.setContent(JsonUtil.toJson(p));

        String heartBeatMessage = JsonUtil.toJson(message);

        Map<String, ClientChannel> map = gatewayService.getChannels();
        map.forEach((s, ClientChannel) -> {
            ClientChannel.getSocketChannel().writeAndFlush(Unpooled.copiedBuffer(heartBeatMessage, CharsetUtil.UTF_8));
        });
    }


}
