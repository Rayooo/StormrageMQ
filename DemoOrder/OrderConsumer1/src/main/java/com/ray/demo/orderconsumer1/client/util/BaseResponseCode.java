package com.ray.demo.orderconsumer1.client.util;

/**
 * Created by Ray on 2017/6/23.
 */
public enum BaseResponseCode {

    SUCCESS(0, "运行成功"),

    ERROR(1, "系统异常");

    private int code;
    private String describe;

    BaseResponseCode(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
