package com.soft.ware.rest.modular.im.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>
 * 第三方极光im信息表
 * </p>
 *
 * @author yancc
 * @since 2019-04-13 14:07:16
 */
@TableName("s_im_groups")
public class SImGroups extends Model<SImGroups> {

    private static final long serialVersionUID = 1L;

    //：员工群；
    public static Integer type_0 = 0;
    //：买家群）
    public static Integer type_1 = 1;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 群组名称
     */
    private String name;
    /**
     * 群类型（0：员工群；1：买家群）
     */
    private Integer type;
    /**
     * flag （选填） 类型 1 - 私有群（默认）  2 - 公开群 不指定flag，默认为1
     */
    private Integer flag;
    /**
     * 群ID
     */
    private String gid;
    /**
     * 群描述
     */
    private String desc;
    /**
     * IM返回结果
     */
    private String body;
    /**
     * 群组图像
     */
    private String avatar;
    /**
     * 商户下所创建的用户
     */
    @TableField("owner_username")
    private String ownerUsername;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 属主id
     */
    @TableField("owner_id")
    private String ownerId;


    public String getId() {
        return id;
    }

    public SImGroups setId(String id){
        this.id = id;return this;
    }

    public String getName() {
        return name;
    }

    public SImGroups setName( String name) {
        this.name = name;return this;
    }

    public Integer getType() {
        return type;
    }

    public SImGroups setType( Integer type) {
        this.type = type;return this;
    }

    public Integer getFlag() {
        return flag;
    }

    public SImGroups setFlag( Integer flag) {
        this.flag = flag;return this;
    }

    public String getGid() {
        return gid;
    }

    public SImGroups setGid( String gid) {
        this.gid = gid;return this;
    }

    public String getDesc() {
        return desc;
    }

    public SImGroups setDesc( String desc) {
        this.desc = desc;return this;
    }

    public String getBody() {
        return body;
    }

    public SImGroups setBody( String body) {
        this.body = body;return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public SImGroups setAvatar( String avatar) {
        this.avatar = avatar;return this;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public SImGroups setOwnerUsername( String ownerUsername) {
        this.ownerUsername = ownerUsername;return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SImGroups setCreateTime( Date createTime) {
        this.createTime = createTime;return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public SImGroups setUpdateTime( Date updateTime) {
        this.updateTime = updateTime;return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public SImGroups setOwnerId( String ownerId) {
        this.ownerId = ownerId;return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SImGroups{" +
        "id=" + id +
        ", name=" + name +
        ", type=" + type +
        ", flag=" + flag +
        ", gid=" + gid +
        ", desc=" + desc +
        ", body=" + body +
        ", avatar=" + avatar +
        ", ownerUsername=" + ownerUsername +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", ownerId=" + ownerId +
        "}";
    }
}