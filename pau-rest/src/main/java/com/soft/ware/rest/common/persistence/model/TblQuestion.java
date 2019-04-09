package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
  * 问题反馈
*/
@TableName("tbl_question")
public class TblQuestion extends Model<TblQuestion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //收货地址-收货人姓名
    private String name;
    //收货地址-收货人电话
    private String phone;
    //收货地址-收货详细地址
    private String address;
    //用户填写的意见反馈信息
    private String description;
    //创建时间
    private Date createdAt;
    //店铺唯一标识
    private String owner;
    //获取的反馈人OpenID
    private String openId;
    //小程序获取信息-appid
    private String accountInfo;
    //设备信息
    private String systemInfo;

    public String getName(){
        return name;
    }

    public void setName( String name){
        this.name = name;
    }
    public String getPhone(){
        return phone;
    }

    public void setPhone( String phone){
        this.phone = phone;
    }
    public String getAddress(){
        return address;
    }

    public void setAddress( String address){
        this.address = address;
    }
    public String getDescription(){
        return description;
    }

    public void setDescription( String description){
        this.description = description;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
    }
    public String getOwner(){
        return owner;
    }

    public void setOwner( String owner){
        this.owner = owner;
    }
    public String getOpenId(){
        return openId;
    }

    public void setOpenId( String openId){
        this.openId = openId;
    }
    public String getAccountInfo(){
        return accountInfo;
    }

    public void setAccountInfo( String accountInfo){
        this.accountInfo = accountInfo;
    }
    public String getSystemInfo(){
        return systemInfo;
    }

    public void setSystemInfo( String systemInfo){
        this.systemInfo = systemInfo;
    }


    public  Long getId(){
        return id;
    }

    public  void setId(Long id){
        this.id = id;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TblQuestion{" +
        "id=" + id +
            ", name=" + name +
            ", phone=" + phone +
            ", address=" + address +
            ", description=" + description +
            ", createdAt=" + createdAt +
            ", owner=" + owner +
            ", openId=" + openId +
            ", accountInfo=" + accountInfo +
            ", systemInfo=" + systemInfo +
        "}";
    }

}