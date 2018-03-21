package com.ray.demo.loginconsumer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"customer.properties"})
@ImportResource({"classpath:spring-mybatis.xml"})
public class LoginConsumer2Application {

	public static void main(String[] args) {
		SpringApplication.run(LoginConsumer2Application.class, args);
	}
}
