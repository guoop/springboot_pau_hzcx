package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
  * 商品分类
*/
@TableName("tbl_category")
public class TblCategory extends Model<TblCategory> {

    private static final long serialVersionUID = 1L;

    //：未删除；
    public static Integer is_delete_0 = 0;
    //：已删除）
    public static Integer is_delete_1 = 1;
    //：显示；
    public static Integer status_0 = 0;
    //：不显示；）
    public static Integer status_1 = 1;
    //：否；
    public static Integer is_system_0 = 0;
    //：是），系统类目不允许执行任何操作（比如香烟类目）
    public static Integer is_system_1 = 1;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //分类名称
    private String name;
    //子级分类名称（多个以英文逗号分隔）
    private String children;
    //描述信息
    private String description;
    //分类权重值（数值越大，排名越靠前）
    private Long weights;
    //分类的封面图
    private String cover;
    //分类属主（关联到sec_user表的ID字段）
    private String owner;
    //是否已删除（0：未删除；1：已删除）
    private Integer isDelete;
    //小程序端是否显示（0：显示；1：不显示；）
    private Integer status;
    //是否系统类目（0：否；1：是），系统类目不允许执行任何操作（比如香烟类目）
    private Integer isSystem;

    public String getName(){
        return name;
    }

    public void setName( String name){
        this.name = name;
    }
    public String getChildren(){
        return children;
    }

    public void setChildren( String children){
        this.children = children;
    }
    public String getDescription(){
        return description;
    }

    public void setDescription( String description){
        this.description = description;
    }
    public Long getWeights(){
        return weights;
    }

    public void setWeights( Long weights){
        this.weights = weights;
    }
    public String getCover(){
        return cover;
    }

    public void setCover( String cover){
        this.cover = cover;
    }
    public String getOwner(){
        return owner;
    }

    public void setOwner( String owner){
        this.owner = owner;
    }
    public Integer getIsDelete(){
        return isDelete;
    }

    public void setIsDelete( Integer isDelete){
        this.isDelete = isDelete;
    }
    public Integer getStatus(){
        return status;
    }

    public void setStatus( Integer status){
        this.status = status;
    }
    public Integer getIsSystem(){
        return isSystem;
    }

    public void setIsSystem( Integer isSystem){
        this.isSystem = isSystem;
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
        return "TblCategory{" +
        "id=" + id +
            ", name=" + name +
            ", children=" + children +
            ", description=" + description +
            ", weights=" + weights +
            ", cover=" + cover +
            ", owner=" + owner +
            ", isDelete=" + isDelete +
            ", status=" + status +
            ", isSystem=" + isSystem +
        "}";
    }


}