package com.ray.stormragemq.config;

import com.ray.stormragemq.dao.UserAccountDao;
import com.ray.stormragemq.domain.UserAccountEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SpringMvcConfig {

    private static final String[] excludePathPatterns = {
            "/exchanger/*",
            "/constants/*",
            "/userAccount/login",
            "/userAccount/register"
    };

    private final UserAccountDao userAccountDao;

    @Autowired
    public SpringMvcConfig(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
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
                            response.sendRedirect("/#/error");
                            return false;
                        }
                        UserAccountEntity user = new UserAccountEntity();
                        user.setLoginToken(token);
                        user.setId(Integer.parseInt(id));
                        UserAccountEntity dataBaseUser = userAccountDao.getUserByToken(user);
                        return dataBaseUser != null;
                    }
                }).excludePathPatterns(excludePathPatterns);
            }

        };
    }

    public static String[] getExcludePathPatterns() {
        return excludePathPatterns;
    }
}
