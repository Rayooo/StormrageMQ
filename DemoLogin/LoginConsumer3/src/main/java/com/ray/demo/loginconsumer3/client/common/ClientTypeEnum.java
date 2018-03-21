package com.ray.demo.loginconsumer3.client.common;

public enum ClientTypeEnum {

    PRODUCER(1, "生产者"),
    CONSUMER(2, "消费者");

    private int type;

    private String describe;

    ClientTypeEnum(int type, String describe) {
        this.type = type;
        this.describe = describe;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
