package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
/**
  * 
*/
@TableName("sec_user")
public class SecUser extends Model<SecUser> {

    //男
    public final static String sex_m = "M";
    //女
    public final static String sex_f = "F";

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id_;

    //登录名
    private String username;
    //性别M/F
    private String sex;
    //姓名
    private String name;
    //登录密码
    private String password;
    //机构单位
    private String department;
    //手机号码
    private String mobile;
    //用户样式
    private String style;
    //电子邮件
    private String email;
    //是否启用（T/F）
    private String enable;
    //系统角色
    private String sysroles;
    //顺序号
    private Long ordernum;
    //用户描述
    private String description;
    //创建人
    private String creator;
    //创建时间
    private Long createStamp;
    //最后修改人
    private String modifier;
    //最后修改人
    private Long modifyStamp;
    //
    private String field01;
    //
    private String field02;
    //
    private String field03;
    //
    private String field04;
    //
    private String field05;
    //
    private String field06;
    //
    private String field07;
    //
    private String field08;
    //
    private String field09;
    //
    private String field10;
    //
    private String credentials;

    public String getUsername(){
        return username;
    }

    public void setUsername( String username){
        this.username = username;
    }
    public String getSex(){
        return sex;
    }

    public void setSex( String sex){
        this.sex = sex;
    }
    public String getName(){
        return name;
    }

    public void setName( String name){
        this.name = name;
    }
    public String getPassword(){
        return password;
    }

    public void setPassword( String password){
        this.password = password;
    }
    public String getDepartment(){
        return department;
    }

    public void setDepartment( String department){
        this.department = department;
    }
    public String getMobile(){
        return mobile;
    }

    public void setMobile( String mobile){
        this.mobile = mobile;
    }
    public String getStyle(){
        return style;
    }

    public void setStyle( String style){
        this.style = style;
    }
    public String getEmail(){
        return email;
    }

    public void setEmail( String email){
        this.email = email;
    }
    public String getEnable(){
        return enable;
    }

    public void setEnable( String enable){
        this.enable = enable;
    }
    public String getSysroles(){
        return sysroles;
    }

    public void setSysroles( String sysroles){
        this.sysroles = sysroles;
    }
    public Long getOrdernum(){
        return ordernum;
    }

    public void setOrdernum( Long ordernum){
        this.ordernum = ordernum;
    }
    public String getDescription(){
        return description;
    }

    public void setDescription( String description){
        this.description = description;
    }
    public String getCreator(){
        return creator;
    }

    public void setCreator( String creator){
        this.creator = creator;
    }
    public Long getCreateStamp(){
        return createStamp;
    }

    public void setCreateStamp( Long createStamp){
        this.createStamp = createStamp;
    }
    public String getModifier(){
        return modifier;
    }

    public void setModifier( String modifier){
        this.modifier = modifier;
    }
    public Long getModifyStamp(){
        return modifyStamp;
    }

    public void setModifyStamp( Long modifyStamp){
        this.modifyStamp = modifyStamp;
    }
    public String getField01(){
        return field01;
    }

    public void setField01( String field01){
        this.field01 = field01;
    }
    public String getField02(){
        return field02;
    }

    public void setField02( String field02){
        this.field02 = field02;
    }
    public String getField03(){
        return field03;
    }

    public void setField03( String field03){
        this.field03 = field03;
    }
    public String getField04(){
        return field04;
    }

    public void setField04( String field04){
        this.field04 = field04;
    }
    public String getField05(){
        return field05;
    }

    public void setField05( String field05){
        this.field05 = field05;
    }
    public String getField06(){
        return field06;
    }

    public void setField06( String field06){
        this.field06 = field06;
    }
    public String getField07(){
        return field07;
    }

    public void setField07( String field07){
        this.field07 = field07;
    }
    public String getField08(){
        return field08;
    }

    public void setField08( String field08){
        this.field08 = field08;
    }
    public String getField09(){
        return field09;
    }

    public void setField09( String field09){
        this.field09 = field09;
    }
    public String getField10(){
        return field10;
    }

    public void setField10( String field10){
        this.field10 = field10;
    }
    public String getCredentials(){
        return credentials;
    }

    public void setCredentials( String credentials){
        this.credentials = credentials;
    }


    public  Long getID_(){
        return id_;
    }

    public  void setID_(Long id_){
        this.id_ = id_;
    }


    @Override
    protected Serializable pkVal() {
        return this.id_;
    }

    @Override
    public String toString() {
        return "SecUser{" +
        "id_=" + id_ +
            ", username=" + username +
            ", sex=" + sex +
            ", name=" + name +
            ", password=" + password +
            ", department=" + department +
            ", mobile=" + mobile +
            ", style=" + style +
            ", email=" + email +
            ", enable=" + enable +
            ", sysroles=" + sysroles +
            ", ordernum=" + ordernum +
            ", description=" + description +
            ", creator=" + creator +
            ", createStamp=" + createStamp +
            ", modifier=" + modifier +
            ", modifyStamp=" + modifyStamp +
            ", field01=" + field01 +
            ", field02=" + field02 +
            ", field03=" + field03 +
            ", field04=" + field04 +
            ", field05=" + field05 +
            ", field06=" + field06 +
            ", field07=" + field07 +
            ", field08=" + field08 +
            ", field09=" + field09 +
            ", field10=" + field10 +
            ", credentials=" + credentials +
        "}";
    }

}