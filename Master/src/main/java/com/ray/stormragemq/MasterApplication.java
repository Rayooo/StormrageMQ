package com.ray.stormragemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ImportResource({"classpath:spring-mybatis.xml"})
public class MasterApplication {
	public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(MasterApplication.class, args);

    }
}
