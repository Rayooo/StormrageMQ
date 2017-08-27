package com.ray.stormragemq.netty;

import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GatewayService {

    private Map<String, SocketChannel> map = new ConcurrentHashMap<>();

    public void addGatewayChannel(String name, SocketChannel channel){
        map.put(name, channel);
    }

    public Map<String, SocketChannel> getChannels(){
        return map;
    }

    public SocketChannel getGatewayChannel(String name){
        return map.get(name);
    }

    public void removeGatewayChannel(String name){
        map.remove(name);
    }

}
