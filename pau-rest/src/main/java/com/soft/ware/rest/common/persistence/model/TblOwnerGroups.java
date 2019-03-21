package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
/**
  * IM群信息
*/
@TableName("tbl_owner_groups")
public class TblOwnerGroups extends Model<TblOwnerGroups> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //群属主
    private String owner;
    //群属性（0：员工群；1：买家群）
    private Integer type;
    //群ID
    private String gid;
    //IM返回结果
    private String body;

    public String getOwner(){
        return owner;
    }

    public void setOwner( String owner){
        this.owner = owner;
    }
    public Integer getType(){
        return type;
    }

    public void setType( Integer type){
        this.type = type;
    }
    public String getGid(){
        return gid;
    }

    public void setGid( String gid){
        this.gid = gid;
    }
    public String getBody(){
        return body;
    }

    public void setBody( String body){
        this.body = body;
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
        return "TblOwnerGroups{" +
        "id=" + id +
            ", owner=" + owner +
            ", type=" + type +
            ", gid=" + gid +
            ", body=" + body +
        "}";
    }

}