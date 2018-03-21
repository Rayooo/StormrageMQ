package com.ray.demo.order.controller;

import com.ray.demo.order.client.common.Message;
import com.ray.demo.order.client.netty.ChannelHandlerService;
import com.ray.demo.order.client.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessage {

    private final ChannelHandlerService channelHandlerService;

    @Autowired
    public SendMessage(ChannelHandlerService channelHandlerService) {
        this.channelHandlerService = channelHandlerService;
    }

    @RequestMapping("/netty")
    public String netty(String content){

        Message message = new Message();
        message.setType("1");
        message.setExchangerName("com.a.haha.*");
        message.setContent(content);

        channelHandlerService.sendMessage(message);

        LogUtil.logInfo(content);

        return "success";
    }

    @RequestMapping("/netty2")
    public String netty2(String content){

        Message message = new Message();
        message.setType("2");
        message.setExchangerName("qwe");
        message.setContent(content);

        channelHandlerService.sendMessage(message);

        LogUtil.logInfo(content);

        return "success";

    }


}
