package com.ray.stormragemq.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.stormragemq.common.Message;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueMessageEntity implements Serializable {

    private static final long serialVersionUID = -4152074987376898477L;

    private String id;

    private String queueName;       //队列名称

    private String consumerName;    //消费者的名称

    private Date createTime;       //创建时间

    private boolean isReceived;     //是否完成接收

    private String messageId;      //消息id

    private Message message;        //消息实体，不序列化

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Object toJson() {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static QueueMessageEntity parseJson(Object json){
        ObjectMapper om = new ObjectMapper();
        try {
            return om.readValue((String) json, QueueMessageEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
