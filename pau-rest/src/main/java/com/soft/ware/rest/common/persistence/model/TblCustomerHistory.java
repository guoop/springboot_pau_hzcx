package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
  * 记录买家端小程序用户使用小程序的痕迹
*/
@TableName("tbl_customer_history")
public class TblCustomerHistory extends Model<TblCustomerHistory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //用户在微信里的唯一标识
    private String openId;
    //用户添加时间
    private Date createdAt;
    //用户最后一次进入小程序的时间
    private Date inAt;
    //用户最后一次退出小程序的时间
    private Date outAt;
    //当前状态（in：使用小程序中；out：已退出小程序）
    private String currentStatus;
    //收货人姓名
    private String addressName;
    //收货人电话
    private String addressPhone;
    //收货人的详细地址
    private String addressDetail;
    //用户添加收货地址的时间
    private Date addressCreatedAt;
    //用户最后一次修改收货地址的时间
    private Date addressUpdatedAt;
    //小程序属主ID（关联到tbl_owner表的owner字段）
    private Integer owner;
    //小程序属主姓名
    private String ownerName;
    //小程序属主电话
    private String ownerPhone;
    //小程序ID
    private String mpId;
    //小程序名称
    private String mpName;

    public String getOpenId(){
        return openId;
    }

    public void setOpenId( String openId){
        this.openId = openId;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
    }
    public Date getInAt(){
        return inAt;
    }

    public void setInAt( Date inAt){
        this.inAt = inAt;
    }
    public Date getOutAt(){
        return outAt;
    }

    public void setOutAt( Date outAt){
        this.outAt = outAt;
    }
    public String getCurrentStatus(){
        return currentStatus;
    }

    public void setCurrentStatus( String currentStatus){
        this.currentStatus = currentStatus;
    }
    public String getAddressName(){
        return addressName;
    }

    public void setAddressName( String addressName){
        this.addressName = addressName;
    }
    public String getAddressPhone(){
        return addressPhone;
    }

    public void setAddressPhone( String addressPhone){
        this.addressPhone = addressPhone;
    }
    public String getAddressDetail(){
        return addressDetail;
    }

    public void setAddressDetail( String addressDetail){
        this.addressDetail = addressDetail;
    }
    public Date getAddressCreatedAt(){
        return addressCreatedAt;
    }

    public void setAddressCreatedAt( Date addressCreatedAt){
        this.addressCreatedAt = addressCreatedAt;
    }
    public Date getAddressUpdatedAt(){
        return addressUpdatedAt;
    }

    public void setAddressUpdatedAt( Date addressUpdatedAt){
        this.addressUpdatedAt = addressUpdatedAt;
    }
    public Integer getOwner(){
        return owner;
    }

    public void setOwner( Integer owner){
        this.owner = owner;
    }
    public String getOwnerName(){
        return ownerName;
    }

    public void setOwnerName( String ownerName){
        this.ownerName = ownerName;
    }
    public String getOwnerPhone(){
        return ownerPhone;
    }

    public void setOwnerPhone( String ownerPhone){
        this.ownerPhone = ownerPhone;
    }
    public String getMpId(){
        return mpId;
    }

    public void setMpId( String mpId){
        this.mpId = mpId;
    }
    public String getMpName(){
        return mpName;
    }

    public void setMpName( String mpName){
        this.mpName = mpName;
    }


    public  Integer getId(){
        return id;
    }

    public  void setId(Integer id){
        this.id = id;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TblCustomerHistory{" +
        "id=" + id +
            ", openId=" + openId +
            ", createdAt=" + createdAt +
            ", inAt=" + inAt +
            ", outAt=" + outAt +
            ", currentStatus=" + currentStatus +
            ", addressName=" + addressName +
            ", addressPhone=" + addressPhone +
            ", addressDetail=" + addressDetail +
            ", addressCreatedAt=" + addressCreatedAt +
            ", addressUpdatedAt=" + addressUpdatedAt +
            ", owner=" + owner +
            ", ownerName=" + ownerName +
            ", ownerPhone=" + ownerPhone +
            ", mpId=" + mpId +
            ", mpName=" + mpName +
        "}";
    }

}