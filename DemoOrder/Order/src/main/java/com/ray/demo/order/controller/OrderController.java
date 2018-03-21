package com.ray.demo.order.controller;

import com.ray.demo.order.client.util.BaseResponse;
import com.ray.demo.order.service.OrderService;
import com.ray.demo.order.util.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/initOrder")
    public BaseResponse<String> initOrder(){

        //初始化订单数据
        orderService.initOrder();

        //开始发送
        orderService.startSendOrder();

        return new BaseResponse<>("success");
    }


    @RequestMapping("/addFinishOrder")
    public BaseResponse<String> addFinishOrder(@RequestBody Map<String, Integer> param) throws BaseException {

        if(param.get("id") == null){
            throw new BaseException("id错误");
        }

        Integer id = param.get("id");

        if(id == 1){
            orderService.addOrderOne();
        }

        if(id == 2){
            orderService.addOrderTwo();
        }

        return new BaseResponse<>("success");
    }

    @RequestMapping("/getOrderInfo")
    public BaseResponse<Map> getOrderInfo(){

        return new BaseResponse<>(orderService.getOrderInfo());

    }

}
