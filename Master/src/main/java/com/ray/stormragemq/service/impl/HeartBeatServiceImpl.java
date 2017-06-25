package com.ray.stormragemq.service.impl;

import com.ray.stormragemq.netty.TCPClient;
import com.ray.stormragemq.service.HeartBeatService;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ray on 2017/6/25.
 */
@Service("heartBeatService")
@EnableScheduling
public class HeartBeatServiceImpl implements HeartBeatService {

    private final ConcurrentHashMap<String, TCPClient> mqMap;

    @Autowired
    public HeartBeatServiceImpl(@Qualifier("mqMap") ConcurrentHashMap<String, TCPClient> mqMap) {
        this.mqMap = mqMap;
    }

    //发送心跳
    @Scheduled(cron = "*/10 * * * * *")
    @Override
    public void sendHeartBeat() {
        mqMap.forEach((s, tcpClient) -> {
            try {
                boolean isConnected = false;
                for (int i = 0;i < 3; ++i){
                    ChannelFuture channelFuture = tcpClient.getSocketChannel().writeAndFlush(Unpooled.copiedBuffer("heartBeat", CharsetUtil.UTF_8)).sync();
                    if(channelFuture.isSuccess()){
                        isConnected = true;
                        break;
                    }
                }
                if(!isConnected){
                    mqMap.remove(s);
                }
            } catch (Exception e) {
                mqMap.remove(s);
            }
        });
    }
}
