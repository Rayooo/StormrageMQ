package com.ray.demo.orderconsumer2.client.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Serializable {

    private static final long serialVersionUID = 876374167203513224L;

    //消息id
    private String uuid;

    //消息主要内容
    private String content;

    //账号(首次验证时使用)
    private String userName;

    //密码(首次验证时使用)
    private String password;

    //交换器名称,消费者投递消息时使用
    private String exchangerName;

    //创建时间
    private Date createTime;

    //消息类型  0 验证初始化消息  1 普通消息->Redis  2 重要消息->postgres
    private String type;

    //消息内容是那个类序列化而来的
    private String cls;

    //client的name(验证时使用)
    private String clientName;

    //client的Type(验证时使用)  1 生产者  2 消费者
    private int clientType;

    //消费者初始化时，监听哪个队列 以,分割
    private String queueNameList;

    //消费者确认收到消息时需要发送给消息队列的id
    private String confirmId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExchangerName() {
        return exchangerName;
    }

    public void setExchangerName(String exchangerName) {
        this.exchangerName = exchangerName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public String getQueueNameList() {
        return queueNameList;
    }

    public void setQueueNameList(String queueNameList) {
        this.queueNameList = queueNameList;
    }

    public String getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(String confirmId) {
        this.confirmId = confirmId;
    }

    public String toJson(){
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
