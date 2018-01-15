package com.ray.stormragemq.config;

import com.ray.stormragemq.entity.ExchangerEntity;
import com.ray.stormragemq.entity.QueueEntity;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ray on 2017/6/25.
 */
@Component
public class MasterBeans {

    //存放消费者服务器名称与Netty Channel的对应关系
//    @Bean(name = "customersMap")
//    public ConcurrentHashMap<String, TCPClient> customersMap(){
//        return new ConcurrentHashMap<>();
//    }

    //存放mq服务器和消息队列名称的对应关系,初始化一个空队列
//    @Bean(name = "mqNameMap")
//    public Map<String, Set<String>> mqNameMap() {
//        return new ConcurrentHashMap<>();
//    }

    //缓存Exchanger，在启动的时候初始化
    @Bean(name = "exchangerMap")
    public Map<String, ExchangerEntity> exchangerMap(){
        return new ConcurrentHashMap<>();
    }

    //缓存Queue  队列名 队列Entity
    @Bean(name = "queueMap")
    public Map<String, QueueEntity> queueMap(){
        return new ConcurrentHashMap<>();
    }

    //密码验证
    @Bean(name = "key")
    public Key key(){
        return MacProvider.generateKey();
    }

    //线程池
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(1000);
        return executor;
    }

}
