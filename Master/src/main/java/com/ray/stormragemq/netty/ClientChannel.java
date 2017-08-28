package com.ray.stormragemq.netty;

import com.ray.stormragemq.constant.ClientTypeEnum;
import io.netty.channel.socket.SocketChannel;

//封装了socketChannel
public class ClientChannel {

    private SocketChannel socketChannel;

    private String name;

    private ClientTypeEnum clientType;

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientTypeEnum getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeEnum clientType) {
        this.clientType = clientType;
    }
}
