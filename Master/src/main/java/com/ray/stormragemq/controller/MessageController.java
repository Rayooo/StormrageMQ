package com.ray.stormragemq.controller;

import com.ray.stormragemq.common.Message;
import com.ray.stormragemq.service.MessageService;
import com.ray.stormragemq.util.BaseResponse;
import com.ray.stormragemq.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("/getMessageList")
    public BaseResponse<PageBean<Message>> getMessageList(@RequestBody Map<String, Integer> param){

        int pageIndex = 1;
        if(param.get("pageIndex") != null){
            pageIndex = param.get("pageIndex");
        }

        PageBean<Message> p =  messageService.getMessage(pageIndex);
        return new BaseResponse<>(p);
    }

}
