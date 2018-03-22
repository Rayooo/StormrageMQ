package com.ray.demo.orderconsumer2.client.common;

import com.ray.demo.orderconsumer2.client.util.JsonUtil;
import com.ray.demo.orderconsumer2.client.util.LogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class SendCount {

    @Value("${netty.remote.address}")
    private String address;

    @Value("${message.server.port}")
    private String port;

    @Value("${message.clientName}")
    private String clientName;

    public void addSendCount(){

        String sendCount = "1";

        Map<String, String> map = new HashMap<>();
        map.put("clientName", clientName);
        map.put("sendCount", sendCount);

        String param = JsonUtil.toJson(map);
        String url = "http://" + address + ":" + port + "/consumer/addSendCount";
        LogUtil.logInfo("确认收到消息，接收下一条" + executePost(url, param));

    }


    public static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "zh-CN");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


}
