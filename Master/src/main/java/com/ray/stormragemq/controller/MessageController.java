package com.ray.stormragemq.controller;

import com.ray.stormragemq.common.Message;
import com.ray.stormragemq.dao.QueueMessageDao;
import com.ray.stormragemq.entity.QueueMessageEntity;
import com.ray.stormragemq.service.MessageService;
import com.ray.stormragemq.util.BaseException;
import com.ray.stormragemq.util.BaseResponse;
import com.ray.stormragemq.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private QueueMessageDao queueMessageDao;

    /**
     * 消息控制面板
     * */
    @RequestMapping("/getMessageList")
    public BaseResponse<PageBean<Message>> getMessageList(@RequestBody Map<String, Integer> param){

        int pageIndex = 1;
        if(param.get("pageIndex") != null){
            pageIndex = param.get("pageIndex");
        }

        PageBean<Message> p =  messageService.getMessage(pageIndex);
        return new BaseResponse<>(p);
    }

    /**
     * 队列消息信息
     * */
    @RequestMapping("/getQueueMessageList")
    public BaseResponse<List<QueueMessageEntity>> getQueueMessageList(@RequestBody Message message) throws BaseException {

        if(message != null && message.getUuid() != null){
            List<QueueMessageEntity> list = queueMessageDao.getQueueMessageByMessageId(message.getUuid());
            return new BaseResponse<>(list);
        }
        else{
            throw new BaseException("消息id为空");
        }


    }

}
