package com.ray.stormragemq.domain;

import java.io.Serializable;
import java.util.Date;

public class ExchangerEntity extends BaseEntity implements Serializable{
    
    private static final long serialVersionUID = 3951303791897018345L;
    
    private Integer type;                //交换器类型 见ExchangerEnum
    
    private String name;                //交换器名称
    
    private String content;             //具体内容， 如果是direct就保存路由键，fanout保存队列数组，topic保存正则表达式
    
    private Date createTime;            //创建时间
    
    private Integer createUserId;       //创建用户

    private String[] queueList;         //存放扫描到的队列, 不序列化

    public String[] getQueueList() {
        return queueList;
    }

    public void setQueueList(String[] queueList) {
        this.queueList = queueList;
    }

    public ExchangerEntity() {}

    public ExchangerEntity(Integer id) {
        super(id);
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
