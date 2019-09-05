package com.soft.ware.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
/**
 * <p>
 * 会员表
 * </p>
 *
 * @author paulo123
 * @since 2019-09-02
 */
@TableName("tb_member")
public class TbMember extends Model<TbMember> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会员名称
     */
    private String name;
    /**
     * 会员编号
     */
    private String code;
    /**
     * 地址
     */
    private String address;
    /**
     * 手机
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 版本（乐观锁保留字段）
     */
    private Integer version;
    @TableField("user_id")
    private Integer userId;
    @TableField("create_time")
    private Date createTime;
    @TableField("creater")
    private String creater;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TbMember{" +
        "id=" + id +
        ", name=" + name +
        ", code=" + code +
        ", address=" + address +
        ", phone=" + phone +
        ", email=" + email +
        ", version=" + version +
        ", createTime=" + createTime +
        ", creater=" + creater +
        ", userId=" + userId +
        "}";
    }
}
