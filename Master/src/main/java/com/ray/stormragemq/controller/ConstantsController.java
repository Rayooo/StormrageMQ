package com.ray.stormragemq.controller;

import com.ray.stormragemq.config.SpringMvcConfig;
import com.ray.stormragemq.util.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/constants")
public class ConstantsController {

    @RequestMapping("/getExcludePathPatterns")
    public BaseResponse getExcludePathPatterns(){
        return new BaseResponse<>(SpringMvcConfig.getExcludePathPatterns());
    }

}
