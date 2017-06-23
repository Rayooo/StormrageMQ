package com.ray.stormragemq.controller;

import com.ray.stormragemq.netty.TCPClient;
import com.ray.stormragemq.util.BaseResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ray on 2017/6/23.
 */
@RestController
@RequestMapping("/netty")
public class NettyController {


    private final TCPClient tcpClient;

    @Autowired
    public NettyController(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }


    @RequestMapping("/heartBeat")
    public BaseResponse<?> heartBeat(){
        tcpClient.getSocketChannel().writeAndFlush(Unpooled.copiedBuffer("heartBeat", CharsetUtil.UTF_8));
        return new BaseResponse<>();
    }



}
