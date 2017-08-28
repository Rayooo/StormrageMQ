package com.ray.stormragemq.constant;

public enum ClientTypeEnum {

    PRODUCER("生产者"),
    CONSUMER("消费者");

    private String type;

    ClientTypeEnum(String type) {
        this.type = type;
    }
}
