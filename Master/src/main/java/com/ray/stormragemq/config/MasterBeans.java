package com.ray.stormragemq.config;

import com.ray.stormragemq.dao.UserAccountDao;
import com.ray.stormragemq.domain.ExchangerEntity;
import com.ray.stormragemq.domain.QueueEntity;
import com.ray.stormragemq.domain.UserAccountEntity;
import com.ray.stormragemq.netty.TCPClient;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ray on 2017/6/25.
 */
@Component
public class MasterBeans {

    //存放消费者服务器名称与Netty Channel的对应关系
    @Bean(name = "customersMap")
    public ConcurrentHashMap<String, TCPClient> customersMap(){
        return new ConcurrentHashMap<>();
    }

    //存放mq服务器和消息队列名称的对应关系,初始化一个空队列
    @Bean(name = "mqNameMap")
    public Map<String, Set<String>> mqNameMap() {
        return new ConcurrentHashMap<>();
    }

    //缓存Exchanger，在启动的时候初始化
    @Bean(name = "exchangerMap")
    public Map<String, ExchangerEntity> exchangerMap(){
        return new ConcurrentHashMap<>();
    }

    @Bean(name = "queueMap")
    public Map<String, QueueEntity> queueMap(){
        return new ConcurrentHashMap<>();
    }

    @Bean(name = "key")
    public Key key(){
        return MacProvider.generateKey();
    }

}
