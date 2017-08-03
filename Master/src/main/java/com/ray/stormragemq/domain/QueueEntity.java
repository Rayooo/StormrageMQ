package com.ray.stormragemq.domain;

import java.io.Serializable;
import java.util.Date;

public class QueueEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -3014709455163434800L;

    private String name;            //队列名称 唯一

    private String addressList;     //推送的主机名称列表

    private Date createTime;        //创建时间

    private Integer createUserId;   //创建的用户id

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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
}
