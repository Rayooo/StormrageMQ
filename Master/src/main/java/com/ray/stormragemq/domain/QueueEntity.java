package com.ray.stormragemq.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -3014709455163434800L;

    private String name;            //队列名称 唯一

    private String addressList;     //推送的主机名称列表  消费者的名称

    private Date createTime;        //创建时间

    private Integer createUserId;   //创建的用户id

    private ArrayList<String> exchangerList;        //被扫描到的Exchanger，不存到数据库

    private BlockingQueue<QueueMessageEntity> blockingQueue = null;         //存储消息（不序列化）

    private List<String> consumerUuidList;               //存储消费者的uuid

    public List<String> getConsumerUuidList() {
        if(consumerUuidList == null){
            consumerUuidList = new ArrayList<>();
        }
        return consumerUuidList;
    }

    public void addConsumer(String uuid){
        if(consumerUuidList == null){
            consumerUuidList = new ArrayList<>();
        }
        if(StringUtils.isNotBlank(uuid)){
            consumerUuidList.add(uuid);
        }
    }

    public void removeConsumer(String uuid){
        if(consumerUuidList == null){
            consumerUuidList = new ArrayList<>();
            return;
        }

        consumerUuidList.remove(uuid);
    }


    public ArrayList<String> getExchangerList() {
        return exchangerList;
    }

    public void setExchangerList(ArrayList<String> exchangerList) {
        this.exchangerList = exchangerList;
    }

    public QueueEntity() {}

    public QueueEntity(Integer id) {
        super(id);
    }

    public QueueEntity(QueueDto queueDto) {
        if(queueDto.getId() != null){
            this.setId(queueDto.getId());
        }
        if(queueDto.getPageNo() != null){
            this.setPageNo(queueDto.getPageNo());
        }
        if(queueDto.getPageSize() != null){
            this.setPageSize(queueDto.getPageSize());
        }
        if(queueDto.getName() != null){
            this.name = queueDto.getName();
        }
        if(queueDto.getAddressList() != null){
            this.addressList = queueDto.getAddressList();
        }
        if(queueDto.getCreateTime() != null){
            this.createTime = queueDto.getCreateTime();
        }
        if(queueDto.getCreateUserId() != null){
            this.createUserId = queueDto.getCreateUserId();
        }
        if(queueDto.getExchangerList() != null){
            this.exchangerList = queueDto.getExchangerList();
        }
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

    public BlockingQueue<QueueMessageEntity> getBlockingQueue() {
        if(blockingQueue == null){
            blockingQueue = new LinkedBlockingQueue<>();
        }
        return blockingQueue;
    }



}
