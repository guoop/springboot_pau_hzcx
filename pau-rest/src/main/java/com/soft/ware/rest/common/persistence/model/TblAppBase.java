package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
/**
  * 收银APP基本信息
*/
@TableName("tbl_app_base")
public class TblAppBase extends Model<TblAppBase> {

    private static final long serialVersionUID = 1L;

    /**
     * android 收银客户端
     */
    public static String PLATFORM_CODE_APP_ANDROID = "app-android";
    /**
     * windows 收银客户端
     */
    public static String PLATFORM_CODE_APP_WINDOWS = "app-windows";



    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //唯一编码
    private String code;
    //名称
    private String name;
    //平台类型（android：安卓；window：PC）
    private String platform;

    public String getCode(){
        return code;
    }

    public void setCode( String code){
        this.code = code;
    }
    public String getName(){
        return name;
    }

    public void setName( String name){
        this.name = name;
    }
    public String getPlatform(){
        return platform;
    }

    public void setPlatform( String platform){
        this.platform = platform;
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
        return "TblAppBase{" +
        "id=" + id +
            ", code=" + code +
            ", name=" + name +
            ", platform=" + platform +
        "}";
    }

}