package com.ray.stormrage;

import com.ray.stormrage.netty.TCPServer;
import com.ray.stormrage.util.HttpUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class MqApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(MqApplication.class, args);


		new Thread(() -> {
			//开启Netty服务
			TCPServer tcpServer = context.getBean(TCPServer.class);
			try {
				tcpServer.start();		//会直接阻塞，需要使用多线程执行这段代码。
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();


		new Thread(() -> {
			//在master上注册MQ自己
			try {
				register(context);
			} catch (UnsupportedEncodingException | InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

	}

	private static void register(ConfigurableApplicationContext context) throws UnsupportedEncodingException, InterruptedException {
		//todo 改配置文件
		TimeUnit.SECONDS.sleep(2);
        ConfigurableEnvironment environment = context.getEnvironment();
		String url = environment.getProperty("register.api");

		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> nameValuePairs = new ArrayList<>();


		nameValuePairs.add(new BasicNameValuePair("host", environment.getProperty("register.host")));
		nameValuePairs.add(new BasicNameValuePair("port", environment.getProperty("tcp.port")));
		nameValuePairs.add(new BasicNameValuePair("name", environment.getProperty("register.name")));

		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		HttpUtil.sendHttpPost(httpPost);
	}


}
