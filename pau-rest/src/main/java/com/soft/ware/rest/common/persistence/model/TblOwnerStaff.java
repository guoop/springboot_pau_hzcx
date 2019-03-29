package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
  * 超市员工信息
*/
@TableName("tbl_owner_staff")
public class TblOwnerStaff extends Model<TblOwnerStaff> {

    private static final long serialVersionUID = 1L;

    //：正常；
    public static Integer status_0 = 0;
    //：已禁用；
    public static Integer status_1 = 1;
    //：已删除）
    public static Integer status_2 = 2;

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


    public Integer getId(){
        return id;
    }

    public TblOwnerStaff setId(Integer id){
        this.id = id;
        return this;
    }


    public String getOwner(){
        return owner;
    }

    public TblOwnerStaff setOwner( String owner){
        this.owner = owner;
        return this;
    }
    public String getName(){
        return name;
    }

    public TblOwnerStaff setName( String name){
        this.name = name;
        return this;
    }
    public String getPhone(){
        return phone;
    }

    public TblOwnerStaff setPhone( String phone){
        this.phone = phone;
        return this;
    }
    public String getPassword(){
        return password;
    }

    public TblOwnerStaff setPassword( String password){
        this.password = password;
        return this;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public TblOwnerStaff setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
        return this;
    }
    public String getFunctionList(){
        return functionList;
    }

    public TblOwnerStaff setFunctionList( String functionList){
        this.functionList = functionList;
        return this;
    }
    public String getUrlList(){
        return urlList;
    }

    public TblOwnerStaff setUrlList( String urlList){
        this.urlList = urlList;
        return this;
    }
    public String getCategoryList(){
        return categoryList;
    }

    public TblOwnerStaff setCategoryList( String categoryList){
        this.categoryList = categoryList;
        return this;
    }
    public Integer getStatus(){
        return status;
    }

    public TblOwnerStaff setStatus( Integer status){
        this.status = status;
        return this;
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