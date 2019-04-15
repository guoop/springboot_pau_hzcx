package com.soft.ware.rest.modular.order.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author paulo123
 * @since 2019-04-11
 */
@TableName("t_order_child")
public class TOrderChild extends Model<TOrderChild> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.UUID)
    private String id;
    /**
     * 订单id
     */
    @TableField("order_id")
    private String orderId;
    /**
     * 商品id
     */
    @TableField("goods_id")
    private String goodsId;
    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;
    /**
     * 商品单价
     */
    @TableField("goods_price")
    private BigDecimal goodsPrice;
    /**
     * 商品图片
     */
    @TableField("goods_pic")
    private String goodsPic;
    /**
     * 商品规格表中的唯一id
     */
    @TableField("goods_spec_id")
    private String goodsSpecId;
    /**
     * 商品数量
     */
    @TableField("goods_num")
    private Integer goodsNum;
    /**
     * 单位表中的唯一id
     */
    @TableField("goods_unit_id")
    private String goodsUnitId;

    /**
     * 促销信息id
     */
    @TableField("goods_promotion_id")
    private String goodsPromotionId;

    /**
     * 创建时间，为了排序
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 库存
     */
    private int stock;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getGoodsSpecId() {
        return goodsSpecId;
    }

    public void setGoodsSpecId(String goodsSpecId) {
        this.goodsSpecId = goodsSpecId;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsUnitId() {
        return goodsUnitId;
    }

    public void setGoodsUnitId(String goodsUnitId) {
        this.goodsUnitId = goodsUnitId;
    }

    public String getGoodsPromotionId() {
        return goodsPromotionId;
    }

    public void setGoodsPromotionId(String goodsPromotionId) {
        this.goodsPromotionId = goodsPromotionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TOrderChild{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", goodsId=" + goodsId +
        ", goodsName=" + goodsName +
        ", goodsPrice=" + goodsPrice +
        ", goodsPic=" + goodsPic +
        ", goodsSpecId=" + goodsSpecId +
        ", goodsNum=" + goodsNum +
        ", goodsUnitId=" + goodsUnitId +
        "}";
    }
}
