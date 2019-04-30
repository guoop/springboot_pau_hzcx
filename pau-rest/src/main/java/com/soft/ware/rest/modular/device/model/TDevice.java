package com.soft.ware.rest.modular.device.model;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>
 * 收银机表
 * </p>
 *
 * @author yancc
 * @since 2019-04-18 20:47:07
 */
@TableName("t_device")
public class TDevice extends Model<TDevice> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 商户唯一标识
     */
    private String ownerId;
    /**
     * 
     */
    private String mac;
    /**
     * 机器唯一编码
     */
    private String code;
    /**
     * 机器型号
     */
    private String model;
    /**
     * 机器规格
     */
    private String specs;
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

    public TDevice setId(String id){
        this.id = id;return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public TDevice setOwnerId( String ownerId) {
        this.ownerId = ownerId;return this;
    }

    public String getMac() {
        return mac;
    }

    public TDevice setMac( String mac) {
        this.mac = mac;return this;
    }

    public String getCode() {
        return code;
    }

    public TDevice setCode( String code) {
        this.code = code;return this;
    }

    public String getModel() {
        return model;
    }

    public TDevice setModel( String model) {
        this.model = model;return this;
    }

    public String getSpecs() {
        return specs;
    }

    public TDevice setSpecs( String specs) {
        this.specs = specs;return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TDevice setCreateTime( Date createTime) {
        this.createTime = createTime;return this;
    }

    public String getCreater() {
        return creater;
    }

    public TDevice setCreater( String creater) {
        this.creater = creater;return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public TDevice setUpdateTime( Date updateTime) {
        this.updateTime = updateTime;return this;
    }

    public String getUpdater() {
        return updater;
    }

    public TDevice setUpdater( String updater) {
        this.updater = updater;return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TDevice{" +
        "id=" + id +
        ", ownerId=" + ownerId +
        ", mac=" + mac +
        ", code=" + code +
        ", model=" + model +
        ", specs=" + specs +
        ", createTime=" + createTime +
        ", creater=" + creater +
        ", updateTime=" + updateTime +
        ", updater=" + updater +
        "}";
    }
}