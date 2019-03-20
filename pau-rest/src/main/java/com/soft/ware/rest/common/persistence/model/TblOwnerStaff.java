package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
  * 超市员工信息
*/
@TableName("tbl_owner_staff")
public class TblOwnerStaff extends Model<TblOwnerStaff> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //属主ID
    private String owner;
    //用户姓名
    private String name;
    //用户的手机号码
    private String phone;
    //收银APP登录密码
    private String password;
    //创建时间
    private Date createdAt;
    //用户拥有的功能列表（用于前端过滤）
    private String functionList;
    //用户拥有的URL列表（用于后端过滤）
    private String urlList;
    //用户可以管理的分类列表
    private String categoryList;
    //状态（0：正常；1：已禁用；2：已删除）
    private Integer status;

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
    public String getPassword(){
        return password;
    }

    public void setPassword( String password){
        this.password = password;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
    }
    public String getFunctionList(){
        return functionList;
    }

    public void setFunctionList( String functionList){
        this.functionList = functionList;
    }
    public String getUrlList(){
        return urlList;
    }

    public void setUrlList( String urlList){
        this.urlList = urlList;
    }
    public String getCategoryList(){
        return categoryList;
    }

    public void setCategoryList( String categoryList){
        this.categoryList = categoryList;
    }
    public Integer getStatus(){
        return status;
    }

    public void setStatus( Integer status){
        this.status = status;
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
        return "TblOwnerStaff{" +
        "id=" + id +
            ", owner=" + owner +
            ", name=" + name +
            ", phone=" + phone +
            ", password=" + password +
            ", createdAt=" + createdAt +
            ", functionList=" + functionList +
            ", urlList=" + urlList +
            ", categoryList=" + categoryList +
            ", status=" + status +
        "}";
    }

}