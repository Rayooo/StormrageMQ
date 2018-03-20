package com.ray.demo.login.entity;


import java.util.Date;

public class DemoUser {

    private Integer id;

    private String userName;

    private String password;

    private Date lastLoginTime;

    private Integer points;

    private String ticket;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getToken() {
        return ticket;
    }

    public void setToken(String ticket) {
        this.ticket = ticket;
    }
}
