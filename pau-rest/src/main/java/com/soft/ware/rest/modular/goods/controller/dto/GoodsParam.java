package com.soft.ware.rest.modular.goods.controller.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
  * 商品信息表
*/
public class GoodsParam {

    private String id ;
    private String name;
    private String code;
    private String unitId;
    private String status;
    private double price;
    private String pics;
    private int isPromotion;
    private String description;
    private int sells;
    private int sort;
    private String source;
    private String categoryId;
    private String creater;
    private Date createTime;
    private double money;
    private  Date startTime;
    private Date endTime;
    private String specsName;
    private String categoryName;
    private int stock;
    private int baseline;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPricel() {
        return price;
    }

    public void setPricel(double price) {
        this.price = price;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public int getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(int isPromotion) {
        this.isPromotion = isPromotion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSells() {
        return sells;
    }

    public void setSells(int sells) {
        this.sells = sells;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSpecsName() {
        return specsName;
    }

    public void setSpecsName(String specsName) {
        this.specsName = specsName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBaseline() {
        return baseline;
    }

    public void setBaseline(int baseline) {
        this.baseline = baseline;
    }

    @Override
    public String toString() {
        return "GoodsParam{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", unitId='" + unitId + '\'' +
                ", status='" + status + '\'' +
                ", pricel=" + price +
                ", pics='" + pics + '\'' +
                ", isPromotion=" + isPromotion +
                ", description='" + description + '\'' +
                ", sells=" + sells +
                ", sort=" + sort +
                ", source='" + source + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", creater='" + creater + '\'' +
                ", createTime=" + createTime +
                ", money=" + money +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", specsName='" + specsName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", stock=" + stock +
                ", baseline=" + baseline +
                '}';
    }
}