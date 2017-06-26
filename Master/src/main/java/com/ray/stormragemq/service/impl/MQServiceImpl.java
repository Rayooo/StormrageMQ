package com.ray.stormragemq.service.impl;

import com.ray.stormragemq.service.MQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * Created by Ray on 2017/6/25.
 */
@Service("mqServiceImpl")
public class MQServiceImpl implements MQService {

    private final Map<String, Set<String>> mqNameMap;

    @Autowired
    public MQServiceImpl(@Qualifier("mqNameMap") Map<String, Set<String>> mqNameMap) {
        this.mqNameMap = mqNameMap;
    }


    @Override
    public String getMqServerName(String mqName) {
        for(Map.Entry<String, Set<String>> entry : mqNameMap.entrySet()){
            if(entry.getValue().contains(mqName)){
                return entry.getKey();
            }
        }
        return null;        //默认值
    }



}
