package com.ray.stormragemq.controller;

import com.ray.stormragemq.dao.QueueMessageDao;
import com.ray.stormragemq.entity.QueueMessageEntity;
import com.ray.stormragemq.netty.ClientChannel;
import com.ray.stormragemq.netty.service.GatewayService;
import com.ray.stormragemq.util.BaseException;
import com.ray.stormragemq.util.BaseResponse;
import com.ray.stormragemq.util.LogUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private QueueMessageDao queueMessageDao;

    /**
     * 消费者确认能够接收下一个消息
     * */
    @RequestMapping("/addSendCount")
    public BaseResponse<String> addSendCount(@RequestBody Map<String, String> param) throws BaseException {

        if(StringUtils.isBlank(param.get("clientName")) && StringUtils.isBlank(param.get("sendCount"))){
            throw new BaseException("clientName不存在或sendCount为空");
        }

        String clientName = param.get("clientName");
        int sendCount = Integer.parseInt(param.get("sendCount"));

        ClientChannel clientChannel = gatewayService.getClientChannelByConsumerName(clientName);
        if(clientChannel != null){
            clientChannel.addSendCount(sendCount);
            return new BaseResponse<>("sendCount增加成功");
        }
        else{
            throw new BaseException("该消费者不存在");
        }

    }

    /**
     * 消费者确认接收到了消息
     * */
    @RequestMapping("/confirm")
    public BaseResponse<String> confirm(@RequestBody Map<String, String> param) throws BaseException {
        if(StringUtils.isBlank(param.get("queueMessageId"))){
            throw new BaseException("队列消息id为空，确认消息错误");
        }

        String id = param.get("queueMessageId");

        QueueMessageEntity qm = new QueueMessageEntity();
        qm.setId(id);
        qm.setSending(true);
        qm.setReceived(true);
        queueMessageDao.updateQueueMessage(qm);
        LogUtil.logInfo("ACK 消费者确认消费消息 " + id + " 成功");
        return new BaseResponse<>("确认" + id + "成功");
    }


}
