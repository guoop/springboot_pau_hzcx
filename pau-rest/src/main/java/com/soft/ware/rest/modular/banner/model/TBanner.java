package com.soft.ware.rest.modular.banner.model;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
/**
  * 买家端首页横幅内容
*/
@TableName("t_banner")
public class TBanner extends Model<TBanner> {

    private static final long serialVersionUID = 1L;


    //：内链；
    public static Integer type_0 = 0;
    //：外链；
    public static Integer type_1 = 1;
    //：静态展示）
    public static Integer type_2 = 2;
    //：显示；
    public static Integer status_0 = 0;
    //：不显示；
    public static Integer status_1 = 1;
    //：已删除）
    public static Integer status_2 = 2;

    //：否；
    public static Integer is_delete_0 = 0;
    //：是）
    public static Integer is_delete_1 = 1;



    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //站位图片地址
    private String poster;
    //横幅名称
    private String name;
    //描述信息
    private String description;
    //横幅类型（0：内链；1：外链；2：静态展示）
    private Integer type;
    //配合【横幅类型】使用，标记链接地址
    private String target;
    //状态（0：显示；1：不显示；2：已删除）
    private Integer status;
    //商户唯一标识
    @JSONField(name = "owner_id")
    private String ownerId;
    //创建时间
    @JSONField(name = "created_time", format = "YYYY-MM-DD HH:mm:ss")
    private Date createdTime;
    //是否删除
    @JSONField(name = "is_delete")
    private Integer isDelete;


    public String getId(){
        return id;
    }

    public TBanner setId(String id){
        this.id = id;
        return this;
    }


    public String getPoster(){
        return poster;
    }

    public TBanner setPoster( String poster){
        this.poster = poster;
        return this;
    }
    public String getName(){
        return name;
    }

    public TBanner setName( String name){
        this.name = name;
        return this;
    }
    public String getDescription(){
        return description;
    }

    public TBanner setDescription( String description){
        this.description = description;
        return this;
    }
    public Integer getType(){
        return type;
    }

    public TBanner setType( Integer type){
        this.type = type;
        return this;
    }
    public String getTarget(){
        return target;
    }

    public TBanner setTarget( String target){
        this.target = target;
        return this;
    }
    public Integer getStatus(){
        return status;
    }

    public TBanner setStatus( Integer status){
        this.status = status;
        return this;
    }
    public String getOwnerId(){
        return ownerId;
    }

    public TBanner setOwnerId(String ownerId){
        this.ownerId = ownerId;
        return this;
    }
    public Date getCreatedTime(){
        return createdTime;
    }

    public TBanner setCreatedTime( Date createdTime){
        this.createdTime = createdTime;
        return this;
    }
    public Integer getIsDelete(){
        return isDelete;
    }

    public TBanner setIsDelete( Integer isDelete){
        this.isDelete = isDelete;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TBanner{" +
        "id=" + id +
            ", poster=" + poster +
            ", name=" + name +
            ", description=" + description +
            ", type=" + type +
            ", target=" + target +
            ", status=" + status +
            ", ownerId=" + ownerId +
            ", createdTime=" + createdTime +
            ", isDelete=" + isDelete +
        "}";
    }

}