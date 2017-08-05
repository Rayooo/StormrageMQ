package com.ray.stormragemq.servlet;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/druid/*", initParams = {
        @WebInitParam(name = "loginUsername", value = "ray"),
        @WebInitParam(name = "loginPassword", value = "123")
})
public class DruidStatView extends StatViewServlet {
    private static final long serialVersionUID = -8914644802051175002L;
}
