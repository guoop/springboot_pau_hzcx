package com.soft.ware.rest.common.persistence.model;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
  * im用户维护表
*/
@TableName("s_im_user")
public class ImUser extends Model<ImUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //商户唯一id

    private String ownerId;
    //用户名称
    private String username;
    //用户密码。极光IM服务器会MD5加密保存
    private String password;
    //用户昵称
    private String nickname;
    //头像
    private String avatar;
    //生日
    private String birthday;
    //签名
    private String signature;
    //性别
    private Integer gender;
    //地区
    private String region;
    //地址
    private String address;
    //用户自定义请求体
    private String body;
    //备注
    private String remark;
    //时间

    private Date createTime;
    //创建人
    private String creater;


    public String getId(){
        return id;
    }

    public ImUser setId(String id){
        this.id = id;
        return this;
    }


    public String getOwnerId(){
        return ownerId;
    }

    public ImUser setOwnerId( String ownerId){
        this.ownerId = ownerId;
        return this;
    }
    public String getUsername(){
        return username;
    }

    public ImUser setUsername( String username){
        this.username = username;
        return this;
    }
    public String getPassword(){
        return password;
    }

    public ImUser setPassword( String password){
        this.password = password;
        return this;
    }
    public String getNickname(){
        return nickname;
    }

    public ImUser setNickname( String nickname){
        this.nickname = nickname;
        return this;
    }
    public String getAvatar(){
        return avatar;
    }

    public ImUser setAvatar( String avatar){
        this.avatar = avatar;
        return this;
    }
    public String getBirthday(){
        return birthday;
    }

    public ImUser setBirthday( String birthday){
        this.birthday = birthday;
        return this;
    }
    public String getSignature(){
        return signature;
    }

    public ImUser setSignature( String signature){
        this.signature = signature;
        return this;
    }
    public Integer getGender(){
        return gender;
    }

    public ImUser setGender( Integer gender){
        this.gender = gender;
        return this;
    }
    public String getRegion(){
        return region;
    }

    public ImUser setRegion( String region){
        this.region = region;
        return this;
    }
    public String getAddress(){
        return address;
    }

    public ImUser setAddress( String address){
        this.address = address;
        return this;
    }
    public String getBody(){
        return body;
    }

    public ImUser setBody( String body){
        this.body = body;
        return this;
    }
    public String getRemark(){
        return remark;
    }

    public ImUser setRemark( String remark){
        this.remark = remark;
        return this;
    }
    public Date getCreateTime(){
        return createTime;
    }

    public ImUser setCreateTime( Date createTime){
        this.createTime = createTime;
        return this;
    }
    public String getCreater(){
        return creater;
    }

    public ImUser setCreater( String creater){
        this.creater = creater;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ImUser{" +
        "id=" + id +
            ", ownerId=" + ownerId +
            ", username=" + username +
            ", password=" + password +
            ", nickname=" + nickname +
            ", avatar=" + avatar +
            ", birthday=" + birthday +
            ", signature=" + signature +
            ", gender=" + gender +
            ", region=" + region +
            ", address=" + address +
            ", body=" + body +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", creater=" + creater +
        "}";
    }

}