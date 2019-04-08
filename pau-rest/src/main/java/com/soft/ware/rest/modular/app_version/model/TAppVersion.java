package com.soft.ware.rest.modular.app_version.model;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
/**
  * 收银APP信息
*/
@TableName("t_app_version")
public class TAppVersion extends Model<TAppVersion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //唯一编码
    private String code;
    //名称
    private String name;
    //平台类型（android：安卓；window：PC）
    private String platform;
    //主版本号
    @JSONField(name = "version_main")
    private Integer versionMain;
    //次版本号
    @JSONField(name = "version_secondary")
    private Integer versionSecondary;
    //修复版本号
    @JSONField(name = "version_bug")
    private Integer versionBug;
    //版本号（5位主版本号.5位次版本号.5位修复版本号）
    private String version;
    //是否强制升级（0：否；1：是）
    @JSONField(name = "is_force")
    private Integer isForce;
    //下载地址
    @JSONField(name = "download_url")
    private String downloadUrl;
    //版本描述信息（HTML串）
    private String description;
    //创建时间
    @JSONField(name = "created_time", format = "YYYY-MM-DD HH:mm:ss")
    private Date createdTime;
    //创建人
    private String creater;
    //更新时间
    @JSONField(name = "update_time", format = "YYYY-MM-DD HH:mm:ss")
    private Date updateTime;
    //更新人
    private String updater;


    public String getId(){
        return id;
    }

    public TAppVersion setId(String id){
        this.id = id;
        return this;
    }


    public String getCode(){
        return code;
    }

    public TAppVersion setCode( String code){
        this.code = code;
        return this;
    }
    public String getName(){
        return name;
    }

    public TAppVersion setName( String name){
        this.name = name;
        return this;
    }
    public String getPlatform(){
        return platform;
    }

    public TAppVersion setPlatform( String platform){
        this.platform = platform;
        return this;
    }
    public Integer getVersionMain(){
        return versionMain;
    }

    public TAppVersion setVersionMain( Integer versionMain){
        this.versionMain = versionMain;
        return this;
    }
    public Integer getVersionSecondary(){
        return versionSecondary;
    }

    public TAppVersion setVersionSecondary( Integer versionSecondary){
        this.versionSecondary = versionSecondary;
        return this;
    }
    public Integer getVersionBug(){
        return versionBug;
    }

    public TAppVersion setVersionBug( Integer versionBug){
        this.versionBug = versionBug;
        return this;
    }
    public String getVersion(){
        return version;
    }

    public TAppVersion setVersion( String version){
        this.version = version;
        return this;
    }
    public Integer getIsForce(){
        return isForce;
    }

    public TAppVersion setIsForce( Integer isForce){
        this.isForce = isForce;
        return this;
    }
    public String getDownloadUrl(){
        return downloadUrl;
    }

    public TAppVersion setDownloadUrl( String downloadUrl){
        this.downloadUrl = downloadUrl;
        return this;
    }
    public String getDescription(){
        return description;
    }

    public TAppVersion setDescription( String description){
        this.description = description;
        return this;
    }
    public Date getCreatedTime(){
        return createdTime;
    }

    public TAppVersion setCreatedTime( Date createdTime){
        this.createdTime = createdTime;
        return this;
    }
    public String getCreater(){
        return creater;
    }

    public TAppVersion setCreater( String creater){
        this.creater = creater;
        return this;
    }
    public Date getUpdateTime(){
        return updateTime;
    }

    public TAppVersion setUpdateTime( Date updateTime){
        this.updateTime = updateTime;
        return this;
    }
    public String getUpdater(){
        return updater;
    }

    public TAppVersion setUpdater( String updater){
        this.updater = updater;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TAppVersion{" +
        "id=" + id +
            ", code=" + code +
            ", name=" + name +
            ", platform=" + platform +
            ", versionMain=" + versionMain +
            ", versionSecondary=" + versionSecondary +
            ", versionBug=" + versionBug +
            ", version=" + version +
            ", isForce=" + isForce +
            ", downloadUrl=" + downloadUrl +
            ", description=" + description +
            ", createdTime=" + createdTime +
            ", creater=" + creater +
            ", updateTime=" + updateTime +
            ", updater=" + updater +
        "}";
    }

}