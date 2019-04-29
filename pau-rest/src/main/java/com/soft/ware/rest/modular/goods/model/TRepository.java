package com.soft.ware.rest.modular.goods.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品库
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@TableName("t_repository")
public class TRepository extends Model<TRepository> {

    private static final long serialVersionUID = 1L;

    //：不删除
    public static Integer is_delete_0 = 0;
    //：删除）
    public static Integer is_delete_1 = 1;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 商品唯一id
     */
    @TableField("goods_code")
    private String goodsCode;
    /**
     * 商品图片
     */
    private String pics;
    /**
     * 是否删除（0：不删除 1：删除）
     */
    @TableField("is_delete")
    private Integer isDelete;
    /**
     * 商户唯一id
     */
    @TableField("owner_id")
    private String ownerId;
    /**
     * 供应商id
     */
    @TableField("supplier_id")
    private String supplierId;
    /**
     * 仓库类型（食品类，玩具类）
     */
    private String type;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @TableField("create_time")
    private Date createTime;
    /**
     * 商品名字
     */
    private String name;
    /**
     * 商品单位
     */
    private String measurement;


    public String getId() {
        return id;
    }

    public TRepository setId(String id){
        this.id = id;return this;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public TRepository setGoodsCode( String goodsCode) {
        this.goodsCode = goodsCode;return this;
    }

    public String getPics() {
        return pics;
    }

    public TRepository setPics( String pics) {
        this.pics = pics;return this;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public TRepository setIsDelete( Integer isDelete) {
        this.isDelete = isDelete;return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public TRepository setOwnerId( String ownerId) {
        this.ownerId = ownerId;return this;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public TRepository setSupplierId( String supplierId) {
        this.supplierId = supplierId;return this;
    }

    public String getType() {
        return type;
    }

    public TRepository setType( String type) {
        this.type = type;return this;
    }

    public String getRemark() {
        return remark;
    }

    public TRepository setRemark( String remark) {
        this.remark = remark;return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TRepository setCreateTime( Date createTime) {
        this.createTime = createTime;return this;
    }

    public String getName() {
        return name;
    }

    public TRepository setName( String name) {
        this.name = name;return this;
    }

    public String getMeasurement() {
        return measurement;
    }

    public TRepository setMeasurement( String measurement) {
        this.measurement = measurement;return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TRepository{" +
        "id=" + id +
        ", goodsCode=" + goodsCode +
        ", pics=" + pics +
        ", isDelete=" + isDelete +
        ", ownerId=" + ownerId +
        ", supplierId=" + supplierId +
        ", type=" + type +
        ", remark=" + remark +
        ", createTime=" + createTime +
        ", name=" + name +
        ", measurement=" + measurement +
        "}";
    }
}
