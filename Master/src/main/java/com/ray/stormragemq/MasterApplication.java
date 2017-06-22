package com.ray.stormragemq;

import com.ray.stormragemq.netty.TCPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MasterApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(MasterApplication.class, args);

		//开启Netty服务
        TCPServer tcpServer = context.getBean(TCPServer.class);
        tcpServer.start();

	}
}
