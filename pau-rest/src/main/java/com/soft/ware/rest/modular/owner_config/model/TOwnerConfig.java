package com.soft.ware.rest.modular.owner_config.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * <p>
 * 商户配置表
 * </p>
 *
 * @author yancc
 * @since 2019-04-11 19:44:44
 */
@TableName("t_owner_config")
public class TOwnerConfig extends Model<TOwnerConfig> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 商户唯一标识
     */
    @TableField("owner_id")
    private String ownerId;
    /**
     * '超市当前是否提供配送服务（0：否；1：是）'
     */
    @TableField("is_delivery")
    private Integer isDelivery;
    /**
     * '是否开启到店自提（0：否：1：是）'
     */
    @TableField("is_self_pickup")
    private Integer isSelfPickup;
    /**
     * '超市当前是否支持货到付款（0：否；1：是）'
     */
    @TableField("is_reach_pay")
    private Integer isReachPay;
    /**
     * '起配金额（元）'
     */
    @TableField("delivery_money")
    private BigDecimal deliveryMoney;
    /**
     * 当用户订单金额小于起配金额时的配送费
     */
    @TableField("delivery_less_money")
    private BigDecimal deliveryLessMoney;
    /**
     * 当用户订单金额大于等于起配金额时的配送费
     */
    @TableField("delivery_great_money")
    private BigDecimal deliveryGreatMoney;
    /**
     * 最远配送距离（如果省略或等于0，则不考虑配送距离）
     */
    @TableField("delivery_distance")
    private Integer deliveryDistance;
    /**
     * 设置订单接受消息的手机号
     */
    @TableField("order_phone")
    private String orderPhone;


    public String getId() {
        return id;
    }

    public TOwnerConfig setId(String id){
        this.id = id;return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public TOwnerConfig setOwnerId( String ownerId) {
        this.ownerId = ownerId;return this;
    }

    public Integer getIsDelivery() {
        return isDelivery;
    }

    public TOwnerConfig setIsDelivery( Integer isDelivery) {
        this.isDelivery = isDelivery;return this;
    }

    public Integer getIsSelfPickup() {
        return isSelfPickup;
    }

    public TOwnerConfig setIsSelfPickup( Integer isSelfPickup) {
        this.isSelfPickup = isSelfPickup;return this;
    }

    public Integer getIsReachPay() {
        return isReachPay;
    }

    public TOwnerConfig setIsReachPay( Integer isReachPay) {
        this.isReachPay = isReachPay;return this;
    }

    public BigDecimal getDeliveryMoney() {
        return deliveryMoney;
    }

    public TOwnerConfig setDeliveryMoney( BigDecimal deliveryMoney) {
        this.deliveryMoney = deliveryMoney;return this;
    }

    public BigDecimal getDeliveryLessMoney() {
        return deliveryLessMoney;
    }

    public TOwnerConfig setDeliveryLessMoney( BigDecimal deliveryLessMoney) {
        this.deliveryLessMoney = deliveryLessMoney;return this;
    }

    public BigDecimal getDeliveryGreatMoney() {
        return deliveryGreatMoney;
    }

    public TOwnerConfig setDeliveryGreatMoney( BigDecimal deliveryGreatMoney) {
        this.deliveryGreatMoney = deliveryGreatMoney;return this;
    }

    public Integer getDeliveryDistance() {
        return deliveryDistance;
    }

    public TOwnerConfig setDeliveryDistance( Integer deliveryDistance) {
        this.deliveryDistance = deliveryDistance;return this;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public TOwnerConfig setOrderPhone( String orderPhone) {
        this.orderPhone = orderPhone;return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TOwnerConfig{" +
        "id=" + id +
        ", ownerId=" + ownerId +
        ", isDelivery=" + isDelivery +
        ", isSelfPickup=" + isSelfPickup +
        ", isReachPay=" + isReachPay +
        ", deliveryMoney=" + deliveryMoney +
        ", deliveryLessMoney=" + deliveryLessMoney +
        ", deliveryGreatMoney=" + deliveryGreatMoney +
        ", deliveryDistance=" + deliveryDistance +
        ", orderPhone=" + orderPhone +
        "}";
    }
}