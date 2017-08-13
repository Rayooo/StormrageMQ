package com.ray.stormragemq.config;

import com.ray.stormragemq.dao.UserAccountDao;
import com.ray.stormragemq.domain.UserAccountEntity;
import com.ray.stormragemq.util.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
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
import java.awt.*;
import java.security.Key;

@Component
public class SpringMvcConfig {

    private static final String[] excludePathPatterns = {
            "/exchanger/listExchangerTypes",
            "/constants/*",
            "/userAccount/login",
            "/userAccount/register"
    };

    private final UserAccountDao userAccountDao;

    private final Key key;

    @Autowired
    public SpringMvcConfig(UserAccountDao userAccountDao, Key key) {
        this.userAccountDao = userAccountDao;
        this.key = key;
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
                        Jws<Claims> claims;
                        try {
                             claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
                        }
                        catch (Exception e){
                            throw new BaseException("登录已过期，请重新登录");
                        }
                        if(claims == null){
                            throw new BaseException("登录已过期，请重新登录");
                        }
                        String userid = claims.getBody().getId();
                        String userName = claims.getBody().getSubject();
                        if(userid.equals(id)){
                            UserAccountEntity user = new UserAccountEntity();
                            user.setUserName(userName);
                            user.setLoginToken(token);
                            user.setId(Integer.parseInt(id));
                            UserAccountEntity dataBaseUser = userAccountDao.getUserByUserName(user);
                            if(dataBaseUser != null){
                                request.setAttribute("userInfo", dataBaseUser);
                                return true;
                            }
                        }
                        return false;
                    }
                }).excludePathPatterns(excludePathPatterns);
            }

        };
    }

    public static String[] getExcludePathPatterns() {
        return excludePathPatterns;
    }
}
