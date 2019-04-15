package com.soft.ware.rest.modular.goods.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

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

    /**
     * 主键id
     */
    private String id;
    /**
     * 商品唯一id
     */
    @TableField("goods_id")
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsCode;
    }

    public void setGoodsId(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TRepository{" +
        "id=" + id +
        ", goodsId=" + goodsCode +
        ", pics=" + pics +
        ", isDelete=" + isDelete +
        ", ownerId=" + ownerId +
        ", supplierId=" + supplierId +
        ", type=" + type +
        ", remark=" + remark +
        "}";
    }
}
