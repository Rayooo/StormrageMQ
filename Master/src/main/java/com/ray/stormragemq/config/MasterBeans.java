package com.ray.stormragemq.config;

import com.ray.stormragemq.dao.UserAccountDao;
import com.ray.stormragemq.domain.UserAccountEntity;
import com.ray.stormragemq.netty.TCPClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ray on 2017/6/25.
 */
@Configuration
public class MasterBeans {

    private final UserAccountDao userAccountDao;

    @Autowired
    public MasterBeans(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    //存放消费者服务器名称与Netty Channel的对应关系
    @Bean(name = "customersMap")
    public ConcurrentHashMap<String, TCPClient> customersMap(){
        return new ConcurrentHashMap<>();
    }

    //存放mq服务器和消息队列名称的对应关系,初始化一个空队列
    @Bean(name = "mqNameMap")
    public Map<String, Set<String>> mqNameMap() {
        Map<String, Set<String>> map = new ConcurrentHashMap<>();
        return map;
    }

    //spring mvc跨域支持
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new HandlerInterceptorAdapter() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                        if(request.getMethod().equals("OPTIONS")) return true;      //跨域的请求
                        String token = request.getHeader("token");
                        String id = request.getHeader("userid");
                        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(id) || !StringUtils.isNumeric(id)){
                            return false;
                        }
                        UserAccountEntity user = new UserAccountEntity();
                        user.setLoginToken(token);
                        user.setId(Integer.parseInt(id));
                        UserAccountEntity dataBaseUser = userAccountDao.getUserByToken(user);
                        return dataBaseUser != null;
                    }
                }).excludePathPatterns(
                        "/userAccount/login",
                        "/userAccount/register"
                        );
            }

        };
    }
}
