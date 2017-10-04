package com.ray.stormragemq.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class QueueDto extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4014700173953271704L;

    private String name;            //队列名称 唯一

    private String addressList;     //推送的主机名称列表

    private Date createTime;        //创建时间

    private Integer createUserId;   //创建的用户id

    private ArrayList<String> exchangerList;        //被扫描到的Exchanger，不存到数据库

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressList() {
        return addressList;
    }

    public void setAddressList(String addressList) {
        this.addressList = addressList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public ArrayList<String> getExchangerList() {
        return exchangerList;
    }

    public void setExchangerList(ArrayList<String> exchangerList) {
        this.exchangerList = exchangerList;
    }
}
