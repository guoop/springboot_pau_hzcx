package com.soft.ware.rest.modular.wx_app.model;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
/**
  * 第三方小程序信息表
*/
@TableName("s_wx_app")
public class SWxApp extends Model<SWxApp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //商户唯一标识

    private String ownerId;
    //小程序appid

    private String appId;
    //小程序app秘钥

    private String appSecret;
    //小程序应用名称

    private String appName;
    //小程序二维码URL

    private String appQr;


    public String getId(){
        return id;
    }

    public SWxApp setId(String id){
        this.id = id;
        return this;
    }


    public String getOwnerId(){
        return ownerId;
    }

    public SWxApp setOwnerId( String ownerId){
        this.ownerId = ownerId;
        return this;
    }
    public String getAppId(){
        return appId;
    }

    public SWxApp setAppId( String appId){
        this.appId = appId;
        return this;
    }
    public String getAppSecret(){
        return appSecret;
    }

    public SWxApp setAppSecret( String appSecret){
        this.appSecret = appSecret;
        return this;
    }
    public String getAppName(){
        return appName;
    }

    public SWxApp setAppName( String appName){
        this.appName = appName;
        return this;
    }
    public String getAppQr(){
        return appQr;
    }

    public SWxApp setAppQr( String appQr){
        this.appQr = appQr;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SWxApp{" +
        "id=" + id +
            ", ownerId=" + ownerId +
            ", appId=" + appId +
            ", appSecret=" + appSecret +
            ", appName=" + appName +
            ", appQr=" + appQr +
        "}";
    }

}