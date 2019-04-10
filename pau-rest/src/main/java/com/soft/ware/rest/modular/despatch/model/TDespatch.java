package com.soft.ware.rest.modular.despatch.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
/**
  * 配送表
*/
@TableName("t_despatch")
public class TDespatch extends Model<TDespatch> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //商户唯一标识

    private String ownerId;
    //配送时间

    private Date despatchTime;
    //配送电话
    private String phone;
    //配送状态（1待取货，2带配送，3配送中，4已完成，5已取消）
    private Integer status;
    //

    private Date createTime;
    //创建人
    private String creater;
    //是否删除（0：不删除，1：删除）

    private Integer isDelete;


    public String getId(){
        return id;
    }

    public TDespatch setId(String id){
        this.id = id;
        return this;
    }


    public String getOwnerId(){
        return ownerId;
    }

    public TDespatch setOwnerId( String ownerId){
        this.ownerId = ownerId;
        return this;
    }
    public Date getDespatchTime(){
        return despatchTime;
    }

    public TDespatch setDespatchTime( Date despatchTime){
        this.despatchTime = despatchTime;
        return this;
    }
    public String getPhone(){
        return phone;
    }

    public TDespatch setPhone( String phone){
        this.phone = phone;
        return this;
    }
    public Integer getStatus(){
        return status;
    }

    public TDespatch setStatus( Integer status){
        this.status = status;
        return this;
    }
    public Date getCreateTime(){
        return createTime;
    }

    public TDespatch setCreateTime( Date createTime){
        this.createTime = createTime;
        return this;
    }
    public String getCreater(){
        return creater;
    }

    public TDespatch setCreater( String creater){
        this.creater = creater;
        return this;
    }
    public Integer getIsDelete(){
        return isDelete;
    }

    public TDespatch setIsDelete( Integer isDelete){
        this.isDelete = isDelete;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TDespatch{" +
        "id=" + id +
            ", ownerId=" + ownerId +
            ", despatchTime=" + despatchTime +
            ", phone=" + phone +
            ", status=" + status +
            ", createTime=" + createTime +
            ", creater=" + creater +
            ", isDelete=" + isDelete +
        "}";
    }

}