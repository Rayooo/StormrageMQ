package com.ray.demo.order.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    private JsonUtil(){}

    public static String toJson(Object o){
        ObjectMapper mapper = new ObjectMapper();
        String result;
        try {
            result = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            result = "";
        }

        return result;
    }

    public static <T> T toObject(String json, Class<T> tClass){
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
