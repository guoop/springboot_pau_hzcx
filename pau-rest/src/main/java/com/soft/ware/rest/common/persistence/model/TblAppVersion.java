package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
  * 收银APP版本信息
*/
@TableName("tbl_app_version")
public class TblAppVersion extends Model<TblAppVersion> {

    private static final long serialVersionUID = 1L;


    //：否；
    public static Integer is_force_0 = 0;
    //：是）
    public static Integer is_force_1 = 1;



    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //关联到tbl_app_base的id字段
    private Long baseId;
    //主版本号
    private Integer versionMain;
    //次版本号
    private Integer versionSecondary;
    //修复版本号
    private Integer versionBug;
    //版本号（5位主版本号.5位次版本号.5位修复版本号）
    private String version;
    //是否强制升级（0：否；1：是）
    private Integer isForce;
    //下载地址
    private String downloadUrl;
    //版本描述信息（HTML串）
    private String description;
    //
    private Date createdAt;

    public Long getBaseId(){
        return baseId;
    }

    public void setBaseId( Long baseId){
        this.baseId = baseId;
    }
    public Integer getVersionMain(){
        return versionMain;
    }

    public void setVersionMain( Integer versionMain){
        this.versionMain = versionMain;
    }
    public Integer getVersionSecondary(){
        return versionSecondary;
    }

    public void setVersionSecondary( Integer versionSecondary){
        this.versionSecondary = versionSecondary;
    }
    public Integer getVersionBug(){
        return versionBug;
    }

    public void setVersionBug( Integer versionBug){
        this.versionBug = versionBug;
    }
    public String getVersion(){
        return version;
    }

    public void setVersion( String version){
        this.version = version;
    }
    public Integer getIsForce(){
        return isForce;
    }

    public void setIsForce( Integer isForce){
        this.isForce = isForce;
    }
    public String getDownloadUrl(){
        return downloadUrl;
    }

    public void setDownloadUrl( String downloadUrl){
        this.downloadUrl = downloadUrl;
    }
    public String getDescription(){
        return description;
    }

    public void setDescription( String description){
        this.description = description;
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
        return "TblAppVersion{" +
        "id=" + id +
            ", baseId=" + baseId +
            ", versionMain=" + versionMain +
            ", versionSecondary=" + versionSecondary +
            ", versionBug=" + versionBug +
            ", version=" + version +
            ", isForce=" + isForce +
            ", downloadUrl=" + downloadUrl +
            ", description=" + description +
            ", createdAt=" + createdAt +
        "}";
    }

}