package com.ray.stormragemq.controller;

import com.ray.stormragemq.domain.ExchangerEntity;
import com.ray.stormragemq.domain.QueueDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;

    private final Map<String, ExchangerEntity> exchangerMap;

    @Autowired
    public QueueController(QueueService queueService, Map<String, ExchangerEntity> exchangerMap) {
        this.queueService = queueService;
        this.exchangerMap = exchangerMap;
    }

    @RequestMapping("/addQueue")
    public BaseResponse<String> addQueue(@RequestBody QueueDto queueDto, @ModelAttribute("userInfo") UserAccountEntity user) throws BaseException {
        QueueEntity queue = new QueueEntity();
        queue.setName(StringUtils.deleteWhitespace(queueDto.getName()));

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
    public BaseResponse<String> deleteQueue(@RequestBody QueueDto queueDto,  @ModelAttribute("userInfo") UserAccountEntity user) throws BaseException {
        QueueEntity queue = new QueueEntity(queueDto);
        if(queue.getId() != null){
            queueService.deleteQueue(queue, user);
            return new BaseResponse<>("success");
        }
        else {
            throw new BaseException("Queue id不能为空");
        }

    }

    @RequestMapping("/changeQueue")
    public BaseResponse<String> changeQueue(@RequestBody QueueDto queueDto) throws BaseException {
        QueueEntity queue = new QueueEntity(queueDto);

        queue.setName(StringUtils.deleteWhitespace(queue.getName()));

        if(StringUtils.isBlank(queue.getName())){
            throw new BaseException("队列名称不可为空");
        }
        if(queue.getId() == null){
            throw new BaseException("id错误");
        }
        queueService.changeQueue(queue);
        return new BaseResponse<>("success");
    }

    //返回这个队列被哪些交换器扫描到
    @RequestMapping("/getQueueInfo")
    public BaseResponse<QueueEntity> getQueueInfo(@RequestBody QueueDto queueDto){
        QueueEntity queue = new QueueEntity(queueDto);

        ArrayList<String> list = new ArrayList<>();
        exchangerMap.forEach((s, exchangerEntity) -> {
            if(exchangerEntity.getQueueList().contains(queue.getName())){
                list.add(s);
            }
        });
        queue.setExchangerList(list);
        return new BaseResponse<>(queue);
    }


}
