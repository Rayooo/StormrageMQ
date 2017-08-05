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


}
