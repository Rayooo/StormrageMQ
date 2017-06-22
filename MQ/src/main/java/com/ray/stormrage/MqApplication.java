package com.ray.stormrage;

import com.ray.stormrage.netty.TCPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MqApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(MqApplication.class, args);

		//开启Netty服务
		TCPServer tcpServer = context.getBean(TCPServer.class);
		tcpServer.start();

	}
}
