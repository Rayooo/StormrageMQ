package com.ray.stormragemq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * Created by Ray on 2017/6/30.
 */
public class BaseEntity {

    private String id;

    @JsonIgnore
    private Integer pageSize;        //分页查询条数

    @JsonIgnore
    private Integer pageNo;         //页码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}