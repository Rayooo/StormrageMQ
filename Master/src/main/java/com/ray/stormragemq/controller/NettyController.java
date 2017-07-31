package com.ray.stormragemq.controller;

import com.ray.stormragemq.netty.NettyConfig;
import com.ray.stormragemq.netty.TCPClient;
import com.ray.stormragemq.util.BaseException;
import com.ray.stormragemq.util.BaseResponse;
import io.netty.bootstrap.Bootstrap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Created by Ray on 2017/6/23.
 */
@RestController
@RequestMapping("/netty")
public class NettyController {

    private final ConcurrentHashMap<String, TCPClient> customersMap;

    private final NettyConfig config;

    @Autowired
    public NettyController(@Qualifier("customersMap") ConcurrentHashMap<String, TCPClient> customersMap,@Qualifier("nettyConfig") NettyConfig config) {
        this.customersMap = customersMap;
        this.config = config;
    }


    //心跳
//    @RequestMapping("/heartBeat")
//    public BaseResponse<?> heartBeat(){
//        tcpClient.getSocketChannel().writeAndFlush(Unpooled.copiedBuffer("heartBeat", CharsetUtil.UTF_8));
//        return new BaseResponse<>();
//    }

    //当消费者启动的时候注册
    @RequestMapping("/customerRegister")
    public BaseResponse<?> mqRegister(String host, String port, String name) throws Exception {
        Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"); //检查IP合法性
        if(!PATTERN.matcher(host).matches() || !StringUtils.isNumeric(port)){
            throw new BaseException("ip或端口不合法");
        }

        Bootstrap b = config.bootstrap(host, Integer.parseInt(port));
        TCPClient client = new TCPClient(b);
        client.start();
        customersMap.put(name, client);
        BaseResponse<TCPClient> response = new BaseResponse<>();
        response.setResult(client);
        return response;
    }



}
