package com.soft.ware.rest.modular.app_version.model;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>
 * 收银APP信息
 * </p>
 *
 * @author yancc
 * @since 2019-04-18 20:51:46
 */
@TableName("t_app_version")
public class TAppVersion extends Model<TAppVersion> {

    /**
     * android 收银客户端
     */
    public static String PLATFORM_CODE_APP_ANDROID = "app-android";
    /**
     * windows 收银客户端
     */
    public static String PLATFORM_CODE_APP_WINDOWS = "app-windows";

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 唯一编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 平台类型（android：安卓；window：PC）
     */
    private String platform;
    /**
     * 主版本号
     */
    private Integer versionMain;
    /**
     * 次版本号
     */
    private Integer versionSecondary;
    /**
     * 修复版本号
     */
    private Integer versionBug;
    /**
     * 版本号（5位主版本号.5位次版本号.5位修复版本号）
     */
    private String version;
    /**
     * 是否强制升级（0：否；1：是）
     */
    private Integer isForce;
    /**
     * 下载地址
     */
    private String downloadUrl;
    /**
     * 版本描述信息（HTML串）
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String creater;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private String updater;


    public String getId() {
        return id;
    }

    public TAppVersion setId(String id){
        this.id = id;return this;
    }

    public String getCode() {
        return code;
    }

    public TAppVersion setCode( String code) {
        this.code = code;return this;
    }

    public String getName() {
        return name;
    }

    public TAppVersion setName( String name) {
        this.name = name;return this;
    }

    public String getPlatform() {
        return platform;
    }

    public TAppVersion setPlatform( String platform) {
        this.platform = platform;return this;
    }

    public Integer getVersionMain() {
        return versionMain;
    }

    public TAppVersion setVersionMain( Integer versionMain) {
        this.versionMain = versionMain;return this;
    }

    public Integer getVersionSecondary() {
        return versionSecondary;
    }

    public TAppVersion setVersionSecondary( Integer versionSecondary) {
        this.versionSecondary = versionSecondary;return this;
    }

    public Integer getVersionBug() {
        return versionBug;
    }

    public TAppVersion setVersionBug( Integer versionBug) {
        this.versionBug = versionBug;return this;
    }

    public String getVersion() {
        return version;
    }

    public TAppVersion setVersion( String version) {
        this.version = version;return this;
    }

    public Integer getIsForce() {
        return isForce;
    }

    public TAppVersion setIsForce( Integer isForce) {
        this.isForce = isForce;return this;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public TAppVersion setDownloadUrl( String downloadUrl) {
        this.downloadUrl = downloadUrl;return this;
    }

    public String getDescription() {
        return description;
    }

    public TAppVersion setDescription( String description) {
        this.description = description;return this;
    }

    public Date getCreatedTime() {
        return createTime;
    }

    public TAppVersion setCreatedTime( Date createdTime) {
        this.createTime = createdTime;return this;
    }

    public String getCreater() {
        return creater;
    }

    public TAppVersion setCreater( String creater) {
        this.creater = creater;return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public TAppVersion setUpdateTime( Date updateTime) {
        this.updateTime = updateTime;return this;
    }

    public String getUpdater() {
        return updater;
    }

    public TAppVersion setUpdater( String updater) {
        this.updater = updater;return this;
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
        ", createdTime=" + createTime +
        ", creater=" + creater +
        ", updateTime=" + updateTime +
        ", updater=" + updater +
        "}";
    }
}