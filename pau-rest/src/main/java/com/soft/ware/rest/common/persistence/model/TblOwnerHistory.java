package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
  * 
*/
@TableName("tbl_owner_history")
public class TblOwnerHistory extends Model<TblOwnerHistory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //
    private String owner;
    //
    private String name;
    //
    private String phone;
    //
    private Date inAt;
    //
    private Date outAt;
    //
    private String currentStatus;

    public String getOwner(){
        return owner;
    }

    public void setOwner( String owner){
        this.owner = owner;
    }
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
        return "TblOwnerHistory{" +
        "id=" + id +
            ", owner=" + owner +
            ", name=" + name +
            ", phone=" + phone +
            ", inAt=" + inAt +
            ", outAt=" + outAt +
            ", currentStatus=" + currentStatus +
        "}";
    }

}