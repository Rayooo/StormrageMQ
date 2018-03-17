package com.ray.stormragemq.util;

import java.util.ArrayList;
import java.util.List;

public class PageBean<T> {

    private int pageIndex = 1;      //需要显示的页码

    private int totalPages = 1;     //总页数

    private int pageSize = 10;      //每页记录数

    private int totalRecords = 0;   //总记录条数

    private boolean havePrePage = true;

    private boolean haveNextPage = true;

    private List<T> pageData = new ArrayList<>();

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;

        totalPages = totalRecords / pageSize + (totalRecords % pageSize == 0? 0 : 1);

        if(pageIndex == 1){
            havePrePage = false;
        }
        if(pageIndex == totalPages){
            haveNextPage = false;
        }

    }

    public boolean isHavePrePage() {
        return havePrePage;
    }

    public void setHavePrePage(boolean havePrePage) {
        this.havePrePage = havePrePage;
    }

    public boolean isHaveNextPage() {
        return haveNextPage;
    }

    public void setHaveNextPage(boolean haveNextPage) {
        this.haveNextPage = haveNextPage;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }
}
