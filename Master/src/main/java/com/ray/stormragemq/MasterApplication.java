package com.ray.stormragemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@ImportResource({"classpath:spring-mybatis.xml"})
@PropertySource({"customer.properties"})
public class MasterApplication {
	public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(MasterApplication.class, args);

    }
}
