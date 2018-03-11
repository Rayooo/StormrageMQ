package com.ray.stormragemq.entity;

import java.io.Serializable;
import java.util.Date;

public class MessageStatisticsEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4113656996676115269L;

    private String name;

    private Integer count;

    private Date statisticsTime;

    private Date createTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getStatisticsTime() {
        return statisticsTime;
    }

    public void setStatisticsTime(Date statisticsTime) {
        this.statisticsTime = statisticsTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
