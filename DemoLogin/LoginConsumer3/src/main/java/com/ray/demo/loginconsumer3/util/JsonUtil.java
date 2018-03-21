package com.ray.demo.loginconsumer3.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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




}
