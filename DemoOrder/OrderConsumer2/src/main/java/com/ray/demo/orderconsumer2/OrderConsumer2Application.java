package com.ray.demo.orderconsumer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"customer.properties"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class OrderConsumer2Application {

	public static void main(String[] args) {
		SpringApplication.run(OrderConsumer2Application.class, args);
	}
}
