package com.soft.ware.rest.modular.auth.util;

import com.soft.ware.core.base.controller.BaseController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page<T> {

    //总条数
    private long total;
    //每页多少条
    private long size;
    //偏移量
    private long offset;
    //总页数
    private long pages;
    //当前第几页
    private long page;
    //查询结果
    private List<T> records;


    public Page() {
        size = 20;
        offset = 1;
        records = new ArrayList<>();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
        if (page > total) {
            this.page = 1;
        }
        if (total < 1) {
            this.pages = total;
            this.offset = 1;
        }else{
            this.pages = total % size > 0 ? total / size + 1 : total / size;
        }
        this.offset = this.page * size;
    }

    public long getLimit() {
        return size;
    }

    public void setLimit(long limit) {
        this.size = limit < 1 ? 1 : limit;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }


    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }


    public Map toMap(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("limit", getLimit());
        map.put("page", getPage());
        map.put("records", getRecords());
        map.put("code", BaseController.SUCCESS);
        return map;
    }
}
