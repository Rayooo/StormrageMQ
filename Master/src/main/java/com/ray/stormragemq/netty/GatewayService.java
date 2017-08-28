package com.ray.stormragemq.netty;

import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GatewayService {

    private Map<String, ClientChannel> map = new ConcurrentHashMap<>();

    public void addGatewayChannel(String id, ClientChannel channel){
        map.put(id, channel);
    }

    public Map<String, ClientChannel> getChannels(){
        return map;
    }

    public ClientChannel getGatewayChannel(String id){
        return map.get(id);
    }

    public void removeGatewayChannel(String id){
        map.remove(id);
    }

}
