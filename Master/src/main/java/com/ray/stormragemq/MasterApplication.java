package com.ray.stormragemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class MasterApplication {

	public static void main(String[] args) throws InterruptedException {

//        try {
//            Process p = Runtime.getRuntime().exec(new String[]{"bash","-c","cd /Users/Ray/Documents/StormrageMQ/Master/src/main/vue/stormrage && npm run build"});
//            StringBuffer output = new StringBuffer();
//            p.waitFor();
//            BufferedReader reader =
//                    new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line;
//            while ((line = reader.readLine())!= null) {
//                output.append(line).append("\n");
//            }
//            System.out.println(output);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        ConfigurableApplicationContext context = SpringApplication.run(MasterApplication.class, args);

    }
}
