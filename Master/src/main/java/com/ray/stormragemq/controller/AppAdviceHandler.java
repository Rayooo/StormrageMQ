package com.ray.stormragemq.controller;

import com.ray.stormragemq.entity.UserAccountEntity;
import com.ray.stormragemq.util.BaseException;
import com.ray.stormragemq.util.BaseResponse;
import com.ray.stormragemq.util.BaseResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ray on 2017/6/23.
 */
@ControllerAdvice
public class AppAdviceHandler {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public BaseResponse<?> handleException(Exception e, HttpServletRequest request){
        BaseResponse<?> response = new BaseResponse<>();
        response.setCode(BaseResponseCode.ERROR.getCode());

        if(e instanceof BaseException && StringUtils.isNoneEmpty(e.getMessage())){
            response.setMessage(e.getMessage());
        }
        else {
            e.printStackTrace();
            response.setMessage(BaseResponseCode.ERROR.getDescribe());
        }

        return response;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(true);
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ModelAttribute("userInfo")
    public UserAccountEntity getUser(HttpServletRequest request) {
        return (UserAccountEntity) request.getAttribute("userInfo");
    }

}
