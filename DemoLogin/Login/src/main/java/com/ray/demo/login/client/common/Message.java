package com.ray.demo.login.client.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Serializable {

    private static final long serialVersionUID = 876374167203513224L;

    //消息id
    private String uuid = UUID.randomUUID().toString();

    //消息主要内容
    private String content;

    //账号(首次验证时使用)
    private String userName;

    //密码(首次验证时使用)
    private String password;

    //交换器类型
    private String exchangerName;

    //创建时间
    private Date createTime = new Date();

    //消息类型  0 验证初始化消息  1 普通消息->Redis  2 重要消息->postgres
    private String type;

    //消息内容是那个类序列化而来的
    private String cls;

    //client的name(验证时使用)
    private String clientName;

    //client的Type(验证时使用)
    private int clientType;

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
        return (Date) createTime.clone();
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
}
