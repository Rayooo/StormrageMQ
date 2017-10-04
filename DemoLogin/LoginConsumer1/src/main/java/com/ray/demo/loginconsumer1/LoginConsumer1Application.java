package com.ray.demo.loginconsumer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"customer.properties"})
public class LoginConsumer1Application {

	public static void main(String[] args) {
		SpringApplication.run(LoginConsumer1Application.class, args);
	}
}
