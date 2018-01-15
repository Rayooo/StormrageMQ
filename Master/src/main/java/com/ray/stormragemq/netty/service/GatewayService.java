package com.ray.stormragemq.netty.service;

import com.ray.stormragemq.constant.ConstantVariable;
import com.ray.stormragemq.entity.QueueEntity;
import com.ray.stormragemq.netty.ClientChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//存储Channel与消费者或生产者的对应关系
@Service
public class GatewayService {

    //key为uuid
    private Map<String, ClientChannel> map = new ConcurrentHashMap<>();

    @Autowired
    private Map<String, QueueEntity> queueMap;

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

    public ClientChannel getClientChannelByConsumerName(String consumerName){
        for(ClientChannel clientChannel : map.values()){
            if(clientChannel.getName().equals(consumerName)){
                return clientChannel;
            }
        }
        return null;
    }


    public void removeConsumerUuid(String uuid){
        queueMap.forEach((s, queueEntity) -> {
            queueEntity.removeConsumer(uuid);
        });
    }

    public void syncConsumerName(){
        queueMap.forEach((s, queueEntity) -> {

            List<String> uuidList = queueEntity.getConsumerUuidList();
            StringBuilder sb = new StringBuilder();

            for (String uuid : uuidList) {
                ClientChannel clientChannel = map.get(uuid);
                if(clientChannel != null) {
                    sb.append(clientChannel.getName()).append(ConstantVariable.SEPARATOR);
                }
            }
            if(sb.length() > 0){
                sb.deleteCharAt(sb.length() - ConstantVariable.SEPARATOR.length());
            }

            queueEntity.setAddressList(sb.toString());

        });
    }

}
