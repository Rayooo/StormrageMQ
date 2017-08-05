package com.ray.stormragemq.controller;

import com.ray.stormragemq.domain.QueueEntity;
import com.ray.stormragemq.domain.UserAccountEntity;
import com.ray.stormragemq.service.QueueService;
import com.ray.stormragemq.util.BaseException;
import com.ray.stormragemq.util.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;

    @Autowired
    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @RequestMapping("/addQueue")
    public BaseResponse<String> addQueue(@RequestBody QueueEntity queue, @ModelAttribute("userInfo") UserAccountEntity user) throws BaseException {
        if(StringUtils.isBlank(queue.getName())){
            throw new BaseException("队列名称不可为空");
        }
        queue.setCreateUserId(user.getId());
        queueService.addQueue(queue);
        return new BaseResponse<>("success");
    }

    @RequestMapping("/getQueueList")
    public BaseResponse<List> getQueueList(@ModelAttribute("userInfo") UserAccountEntity user){
        return new BaseResponse<>(queueService.getQueueListByUserId(user.getId()));
    }

    @RequestMapping("/deleteQueue")
    public BaseResponse<String> deleteQueue(@RequestBody QueueEntity queue,  @ModelAttribute("userInfo") UserAccountEntity user) throws BaseException {
        if(queue.getId() != null){
            queueService.deleteQueue(queue, user);
            return new BaseResponse<>("success");
        }
        else {
            throw new BaseException("Queue id不能为空");
        }

    }

    @RequestMapping("/changeQueue")
    public BaseResponse<String> changeQueue(@RequestBody QueueEntity queue) throws BaseException {
        queue.setName(queue.getName().trim());
        if(StringUtils.isBlank(queue.getName())){
            throw new BaseException("队列名称不可为空");
        }
        if(queue.getId() == null){
            throw new BaseException("id错误");
        }
        queueService.changeQueue(queue);
        return new BaseResponse<>("success");
    }


}
