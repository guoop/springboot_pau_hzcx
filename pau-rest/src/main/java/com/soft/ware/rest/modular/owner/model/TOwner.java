package com.soft.ware.rest.modular.owner.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商户信息
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@TableName("t_owner")
public class TOwner extends Model<TOwner> {

    private static final long serialVersionUID = 1L;

    /**
     * 商家在系统中的唯一标识
     */
    private String id;
    /**
     * 商家姓名
     */
    private String name;
    /**
     * 商家电话
     */
    private String phone;
    /**
     * 商家地址
     */
    private String address;
    /**
     * 商家的描述信息
     */
    private String description;
    /**
     * 商铺的视频地址
     */
    @TableField("video_url")
    private String videoUrl;
    /**
     * 营业起始时间
     */
    @TableField("begin_time")
    private String beginTime;
    /**
     * 营业截止时间
     */
    @TableField("end_time")
    private String endTime;
    /**
     * 商品的默认描述
     */
    @TableField("default_desc")
    private String defaultDesc;
    /**
     * 默认退款原因
     */
    @TableField("default_refund_reason")
    private String defaultRefundReason;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;




    public TOwner() {
    }

    @TableField(exist = false)
    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getId() {
        return id;
    }

    public TOwner setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDefaultDesc() {
        return defaultDesc;
    }

    public void setDefaultDesc(String defaultDesc) {
        this.defaultDesc = defaultDesc;
    }

    public String getDefaultRefundReason() {
        return defaultRefundReason;
    }

    public void setDefaultRefundReason(String defaultRefundReason) {
        this.defaultRefundReason = defaultRefundReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.phone;
    }

    @Override
    public String toString() {
        return "TOwner{" +
        "id=" + id +
        ", name=" + name +
        ", phone=" + phone +
        ", address=" + address +
        ", description=" + description +
        ", videoUrl=" + videoUrl +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", defaultDesc=" + defaultDesc +
        ", defaultRefundReason=" + defaultRefundReason +
        ", createTime=" + createTime +
        "}";
    }
}
