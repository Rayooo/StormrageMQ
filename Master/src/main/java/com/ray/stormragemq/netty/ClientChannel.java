package com.ray.stormragemq.netty;

import com.ray.stormragemq.constant.ClientTypeEnum;
import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.atomic.AtomicInteger;

//封装了socketChannel
public class ClientChannel {

    private SocketChannel socketChannel;

    private String name;

    private int clientType;

    private volatile AtomicInteger sendCount = new AtomicInteger(0);      //消费者的可剩余发送次数

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

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public AtomicInteger getSendCount() {
        return sendCount;
    }

    public void setSendCount(AtomicInteger sendCount) {
        this.sendCount = sendCount;
    }

    public void addSendCount(int count){
        sendCount.incrementAndGet();
    }

    public boolean canSend(){
        if(sendCount.intValue() > 0){
            sendCount.decrementAndGet();
            return true;
        }
        else {
            return false;
        }
    }

}
