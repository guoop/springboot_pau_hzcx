package com.soft.ware.rest.common.persistence.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
  * 第三方极光im信息表
*/
@TableName("s_im_groups")
public class ImGroups extends Model<ImGroups> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //群组名称
    private String name;
    //群类型（0：员工群；1：买家群）
    private Integer type;
    //flag （选填） 类型 1 - 私有群（默认）  2 - 公开群 不指定flag，默认为1
    private Integer flag;
    //群ID
    private String gid;
    //群描述
    private String desc;
    //IM返回结果
    private String body;
    //群组图像
    private String avatar;
    //商户下所创建的用户
    @JSONField(name = "owner_username")
    private String ownerUsername;
    //创建时间
    @JSONField(name = "create_time", format = "YYYY-MM-DD HH:mm:ss")
    private Date createTime;
    //更新时间
    @JSONField(name = "update_time", format = "YYYY-MM-DD HH:mm:ss")
    private Date updateTime;


    public String getId(){
        return id;
    }

    public ImGroups setId(String id){
        this.id = id;
        return this;
    }


    public String getName(){
        return name;
    }

    public ImGroups setName( String name){
        this.name = name;
        return this;
    }
    public Integer getType(){
        return type;
    }

    public ImGroups setType( Integer type){
        this.type = type;
        return this;
    }
    public Integer getFlag(){
        return flag;
    }

    public ImGroups setFlag( Integer flag){
        this.flag = flag;
        return this;
    }
    public String getGid(){
        return gid;
    }

    public ImGroups setGid( String gid){
        this.gid = gid;
        return this;
    }
    public String getDesc(){
        return desc;
    }

    public ImGroups setDesc( String desc){
        this.desc = desc;
        return this;
    }
    public String getBody(){
        return body;
    }

    public ImGroups setBody( String body){
        this.body = body;
        return this;
    }
    public String getAvatar(){
        return avatar;
    }

    public ImGroups setAvatar( String avatar){
        this.avatar = avatar;
        return this;
    }
    public String getOwnerUsername(){
        return ownerUsername;
    }

    public ImGroups setOwnerUsername( String ownerUsername){
        this.ownerUsername = ownerUsername;
        return this;
    }
    public Date getCreateTime(){
        return createTime;
    }

    public ImGroups setCreateTime( Date createTime){
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime(){
        return updateTime;
    }

    public ImGroups setUpdateTime( Date updateTime){
        this.updateTime = updateTime;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ImGroups{" +
        "id=" + id +
            ", name=" + name +
            ", type=" + type +
            ", flag=" + flag +
            ", gid=" + gid +
            ", desc=" + desc +
            ", body=" + body +
            ", avatar=" + avatar +
            ", ownerUsername=" + ownerUsername +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }

}