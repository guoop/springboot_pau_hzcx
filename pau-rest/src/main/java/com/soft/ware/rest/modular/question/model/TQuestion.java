package com.soft.ware.rest.modular.question.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 问题反馈
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@TableName("t_question")
public class TQuestion extends Model<TQuestion> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;
    /**
     * 收货地址-收货人姓名
     */
    private String name;
    /**
     * 收货地址-收货人电话
     */
    private String phone;
    /**
     * 收货地址-收货详细地址
     */
    private String address;
    /**
     * 用户填写的意见反馈信息
     */
    private String description;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 店铺唯一标识
     */
    private String owner;
    /**
     * 获取的反馈人OpenID
     */
    @TableField("open_id")
    private String openId;
    /**
     * 小程序获取信息-appid
     */
    @TableField("account_info")
    private String accountInfo;
    /**
     * 设备信息
     */
    @TableField("system_info")
    private String systemInfo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }

    public String getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(String systemInfo) {
        this.systemInfo = systemInfo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TQuestion{" +
        "id=" + id +
        ", name=" + name +
        ", phone=" + phone +
        ", address=" + address +
        ", description=" + description +
        ", createdAt=" + createdAt +
        ", owner=" + owner +
        ", openId=" + openId +
        ", accountInfo=" + accountInfo +
        ", systemInfo=" + systemInfo +
        "}";
    }
}
