package com.ray.stormragemq.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ray on 2017/6/30.
 */
public class UserAccountEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = -4352289535055795726L;

    private String userName;                //用户名
    
    private String password;                //密码
    
    private String headImage;               //头像
    
    private Date createTime;                //创建时间

    private String loginToken;              //登录Token
    
    private Integer isDeleted;              //是否被删除了

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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
