package com.ray.stormragemq.controller;

import com.ray.stormragemq.constant.ExchangerEnum;
import com.ray.stormragemq.domain.ExchangerEntity;
import com.ray.stormragemq.domain.UserAccountEntity;
import com.ray.stormragemq.service.ExchangerService;
import com.ray.stormragemq.util.BaseException;
import com.ray.stormragemq.util.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exchanger")
public class ExchangerController {

    private final ExchangerService exchangerService;

    private final Map<String, ExchangerEntity> exchangerMap;

    @Autowired
    public ExchangerController(ExchangerService exchangerService, Map<String, ExchangerEntity> exchangerMap) {
        this.exchangerService = exchangerService;
        this.exchangerMap = exchangerMap;
    }


    @RequestMapping("/listExchangerTypes")
    public BaseResponse<List> listExchangerTypes(){
        List<ExchangerEnum> list = new ArrayList<>();
        list.addAll(Arrays.asList(ExchangerEnum.values()));
        return new BaseResponse<>(list);
    }

    @RequestMapping("/addExchanger")
    public BaseResponse<String> addExchanger(@RequestBody ExchangerEntity exchanger, @ModelAttribute("userInfo") UserAccountEntity user) throws BaseException {
        if(StringUtils.isBlank(exchanger.getName()) || StringUtils.isBlank(exchanger.getContent())){
            throw new BaseException("交换机名字或内容不可为空");
        }
        if(ExchangerEnum.getName(exchanger.getType()) == null){
            throw new BaseException("交换机类型错误");
        }

        exchanger.setCreateUserId(user.getId());
        exchangerService.addExchanger(exchanger);
        return new BaseResponse<>("SUCCESS");
    }

    @RequestMapping("/getExchangerList")
    public BaseResponse<List> getExchangerList(@ModelAttribute("userInfo") UserAccountEntity user){
        return new BaseResponse<>(exchangerService.getExchangerListByUser(user.getId()));
    }

}
