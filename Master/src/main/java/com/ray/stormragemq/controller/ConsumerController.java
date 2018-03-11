package com.ray.stormragemq.controller;

import com.ray.stormragemq.netty.ClientChannel;
import com.ray.stormragemq.netty.service.GatewayService;
import com.ray.stormragemq.util.BaseException;
import com.ray.stormragemq.util.BaseResponse;
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


}
