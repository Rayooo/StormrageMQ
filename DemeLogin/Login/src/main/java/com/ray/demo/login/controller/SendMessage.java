package com.ray.demo.login.controller;

import com.ray.demo.login.client.common.Message;
import com.ray.demo.login.client.netty.ChannelHandlerService;
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
        message.setExchangerName("an exchanger");
        message.setContent(content);

        channelHandlerService.sendMessage(message);



        return "success";
    }


}
