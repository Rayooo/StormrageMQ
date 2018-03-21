package com.ray.demo.order.client.util;

import java.io.Serializable;

/**
 * Created by Ray on 2017/6/23.
 *
 */
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -4810442863193796645L;

    public BaseResponse() {}

    public BaseResponse(T result) {
        this.result = result;
    }

    private String message = BaseResponseCode.SUCCESS.getDescribe();

    private Integer code = BaseResponseCode.SUCCESS.getCode();

    private T result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setError(){
        code = BaseResponseCode.ERROR.getCode();
        message = BaseResponseCode.ERROR.getDescribe();
    }

}
