package com.soft.ware.rest.modular.owner_staff.model;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
/**
  * 商户下的员工信息表
*/
@TableName("t_owner_staff")
public class TOwnerStaff extends Model<TOwnerStaff> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //商户唯一标识
    @JSONField(name = "owner_id")
    private String ownerId;
    //用户姓名
    private String name;
    //用户的手机号码
    private String phone;
    //收银APP登录密码
    private String password;
    //创建时间
    @JSONField(name = "created_at", format = "YYYY-MM-DD HH:mm:ss")
    private Date createdAt;
    //用户拥有的功能列表（用于前端过滤）
    @JSONField(name = "function_list")
    private String functionList;
    //用户拥有的URL列表（用于后端过滤）
    @JSONField(name = "url_list")
    private String urlList;
    //用户可以管理的分类列表
    @JSONField(name = "category_list")
    private String categoryList;
    //状态（0：正常；1：已禁用；2：已删除）
    private Integer status;
    //备注
    private String description;


    public String getId(){
        return id;
    }

    public TOwnerStaff setId(String id){
        this.id = id;
        return this;
    }


    public String getOwnerId(){
        return ownerId;
    }

    public TOwnerStaff setOwnerId( String ownerId){
        this.ownerId = ownerId;
        return this;
    }
    public String getName(){
        return name;
    }

    public TOwnerStaff setName( String name){
        this.name = name;
        return this;
    }
    public String getPhone(){
        return phone;
    }

    public TOwnerStaff setPhone( String phone){
        this.phone = phone;
        return this;
    }
    public String getPassword(){
        return password;
    }

    public TOwnerStaff setPassword( String password){
        this.password = password;
        return this;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public TOwnerStaff setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
        return this;
    }
    public String getFunctionList(){
        return functionList;
    }

    public TOwnerStaff setFunctionList( String functionList){
        this.functionList = functionList;
        return this;
    }
    public String getUrlList(){
        return urlList;
    }

    public TOwnerStaff setUrlList( String urlList){
        this.urlList = urlList;
        return this;
    }
    public String getCategoryList(){
        return categoryList;
    }

    public TOwnerStaff setCategoryList( String categoryList){
        this.categoryList = categoryList;
        return this;
    }
    public Integer getStatus(){
        return status;
    }

    public TOwnerStaff setStatus( Integer status){
        this.status = status;
        return this;
    }
    public String getDescription(){
        return description;
    }

    public TOwnerStaff setDescription( String description){
        this.description = description;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TOwnerStaff{" +
        "id=" + id +
            ", ownerId=" + ownerId +
            ", name=" + name +
            ", phone=" + phone +
            ", password=" + password +
            ", createdAt=" + createdAt +
            ", functionList=" + functionList +
            ", urlList=" + urlList +
            ", categoryList=" + categoryList +
            ", status=" + status +
            ", description=" + description +
        "}";
    }

}