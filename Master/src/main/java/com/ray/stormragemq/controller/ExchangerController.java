package com.ray.stormragemq.controller;

import com.ray.stormragemq.constant.ExchangerEnum;
import com.ray.stormragemq.domain.ExchangerEntity;
import com.ray.stormragemq.service.ExchangerService;
import com.ray.stormragemq.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/exchanger")
public class ExchangerController {

    private final ExchangerService exchangerService;

    @Autowired
    public ExchangerController(ExchangerService exchangerService) {
        this.exchangerService = exchangerService;
    }


    @RequestMapping("/listExchangerTypes")
    public BaseResponse<List> listExchangerTypes(){
        List<ExchangerEnum> list = new ArrayList<>();
        list.addAll(Arrays.asList(ExchangerEnum.values()));
        return new BaseResponse<>(list);
    }

    @RequestMapping("/addExchanger")
    public BaseResponse addExchanger(ExchangerEntity exchanger){



        return new BaseResponse();
    }

}
