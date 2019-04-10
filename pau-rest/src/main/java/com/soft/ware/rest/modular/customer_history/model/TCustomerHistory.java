package com.soft.ware.rest.modular.customer_history.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
/**
  * 记录买家端小程序用户使用小程序的痕迹
*/
@TableName("t_customer_history")
public class TCustomerHistory extends Model<TCustomerHistory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //用户在微信里的唯一标识

    private String openId;
    //商户唯一标识

    private String ownerId;
    //用户添加时间

    private Date createdTime;
    //用户最后一次进入小程序的时间

    private Date endInTime;
    //用户最后一次退出小程序的时间

    private Date endOutTime;
    //'当前状态（in：使用小程序中；out：已退出小程序）'

    private String currentStatus;
    //地址名称

    private String addressName;
    //电话

    private String addressPhone;
    //地址创建时间

    private Date addressCreatedTime;
    //地址更新时间

    private Date addressUpdatedTime;


    public String getId(){
        return id;
    }

    public TCustomerHistory setId(String id){
        this.id = id;
        return this;
    }


    public String getOpenId(){
        return openId;
    }

    public TCustomerHistory setOpenId( String openId){
        this.openId = openId;
        return this;
    }
    public String getOwnerId(){
        return ownerId;
    }

    public TCustomerHistory setOwnerId( String ownerId){
        this.ownerId = ownerId;
        return this;
    }
    public Date getCreatedTime(){
        return createdTime;
    }

    public TCustomerHistory setCreatedTime( Date createdTime){
        this.createdTime = createdTime;
        return this;
    }
    public Date getEndInTime(){
        return endInTime;
    }

    public TCustomerHistory setEndInTime( Date endInTime){
        this.endInTime = endInTime;
        return this;
    }
    public Date getEndOutTime(){
        return endOutTime;
    }

    public TCustomerHistory setEndOutTime( Date endOutTime){
        this.endOutTime = endOutTime;
        return this;
    }
    public String getCurrentStatus(){
        return currentStatus;
    }

    public TCustomerHistory setCurrentStatus( String currentStatus){
        this.currentStatus = currentStatus;
        return this;
    }
    public String getAddressName(){
        return addressName;
    }

    public TCustomerHistory setAddressName( String addressName){
        this.addressName = addressName;
        return this;
    }
    public String getAddressPhone(){
        return addressPhone;
    }

    public TCustomerHistory setAddressPhone( String addressPhone){
        this.addressPhone = addressPhone;
        return this;
    }
    public Date getAddressCreatedTime(){
        return addressCreatedTime;
    }

    public TCustomerHistory setAddressCreatedTime( Date addressCreatedTime){
        this.addressCreatedTime = addressCreatedTime;
        return this;
    }
    public Date getAddressUpdatedTime(){
        return addressUpdatedTime;
    }

    public TCustomerHistory setAddressUpdatedTime( Date addressUpdatedTime){
        this.addressUpdatedTime = addressUpdatedTime;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TCustomerHistory{" +
        "id=" + id +
            ", openId=" + openId +
            ", ownerId=" + ownerId +
            ", createdTime=" + createdTime +
            ", endInTime=" + endInTime +
            ", endOutTime=" + endOutTime +
            ", currentStatus=" + currentStatus +
            ", addressName=" + addressName +
            ", addressPhone=" + addressPhone +
            ", addressCreatedTime=" + addressCreatedTime +
            ", addressUpdatedTime=" + addressUpdatedTime +
        "}";
    }

}