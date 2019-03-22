package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
  * 买家端首页横幅内容
*/
@TableName("tbl_banner")
public class TblBanner extends Model<TblBanner> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
    //属主
    private String owner;
    //添加时间
    private Date createdAt;

    public String getPoster(){
        return poster;
    }

    public void setPoster( String poster){
        this.poster = poster;
    }
    public String getName(){
        return name;
    }

    public void setName( String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }

    public void setDescription( String description){
        this.description = description;
    }
    public Integer getType(){
        return type;
    }

    public void setType( Integer type){
        this.type = type;
    }
    public String getTarget(){
        return target;
    }

    public void setTarget( String target){
        this.target = target;
    }
    public Integer getStatus(){
        return status;
    }

    public void setStatus( Integer status){
        this.status = status;
    }
    public String getOwner(){
        return owner;
    }

    public TblBanner setOwner( String owner){
        this.owner = owner;
        return this;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
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
        return "TblBanner{" +
        "id=" + id +
            ", poster=" + poster +
            ", name=" + name +
            ", description=" + description +
            ", type=" + type +
            ", target=" + target +
            ", status=" + status +
            ", owner=" + owner +
            ", createdAt=" + createdAt +
        "}";
    }

}