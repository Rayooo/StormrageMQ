package com.ray.stormragemq.constant;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 交换机类型
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  ExchangerEnum {

    DIRECT(1, "Direct交换器"),             //处理路由键
    FANOUT(2, "Fanout交换器"),             //将队列绑定到交换机上
    TOPIC(3, "Topic交换器");                //将路由键和某模式进行匹配

    private int type;

    private String describe;

    ExchangerEnum(int type, String describe) {
        this.type = type;
        this.describe = describe;
    }

    public int getType() {
        return type;
    }

    public String getDescribe() {
        return describe;
    }

    public static String getName(int type){
        for(ExchangerEnum e : ExchangerEnum.values()){
            if(e.getType() == type){
                return e.getDescribe();
            }
        }
        return null;
    }



}
