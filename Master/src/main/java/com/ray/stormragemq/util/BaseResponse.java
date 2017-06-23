package com.ray.stormragemq.util;

import java.io.Serializable;

/**
 * Created by Ray on 2017/6/23.
 *
 */
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -4810442863193796645L;

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



}
