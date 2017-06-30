package com.ray.stormragemq.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ray on 2017/6/30.
 */
public class UserAccountEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = -4352289535055795726L;

    private String userName;
    
    private String password;
    
    private String headImage;
    
    private Date createTime;

    private String loginToken;
    
    private Integer isPassed;
    
    private Integer isDeleted;
    

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

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public Integer getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Integer isPassed) {
        this.isPassed = isPassed;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
