package com.soft.ware.rest.modular.owner_config.model;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
/**
  * 商户配置表
*/
@TableName("t_owner_config")
public class TOwnerConfig extends Model<TOwnerConfig> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //商户唯一标识

    private String ownerId;
    //'超市当前是否提供配送服务（0：否；1：是）'

    private Integer isDelivery;
    //'是否开启到店自提（0：否：1：是）'

    private Integer isSelfPickup;
    //'超市当前是否支持货到付款（0：否；1：是）'

    private Integer isReachPay;
    //'起配金额（元）'

    private BigDecimal deliveryMoney;
    //当用户订单金额小于起配金额时的配送费

    private BigDecimal deliveryLessMoney;
    //当用户订单金额大于等于起配金额时的配送费

    private BigDecimal deliveryGreatMoney;
    //最远配送距离（如果省略或等于0，则不考虑配送距离）

    private Integer deliveryDistance;
    //设置订单接受消息的手机号

    private String orderPhone;


    public String getId(){
        return id;
    }

    public TOwnerConfig setId(String id){
        this.id = id;
        return this;
    }


    public String getOwnerId(){
        return ownerId;
    }

    public TOwnerConfig setOwnerId( String ownerId){
        this.ownerId = ownerId;
        return this;
    }
    public Integer getIsDelivery(){
        return isDelivery;
    }

    public TOwnerConfig setIsDelivery( Integer isDelivery){
        this.isDelivery = isDelivery;
        return this;
    }
    public Integer getIsSelfPickup(){
        return isSelfPickup;
    }

    public TOwnerConfig setIsSelfPickup( Integer isSelfPickup){
        this.isSelfPickup = isSelfPickup;
        return this;
    }
    public Integer getIsReachPay(){
        return isReachPay;
    }

    public TOwnerConfig setIsReachPay( Integer isReachPay){
        this.isReachPay = isReachPay;
        return this;
    }
    public BigDecimal getDeliveryMoney(){
        return deliveryMoney;
    }

    public TOwnerConfig setDeliveryMoney( BigDecimal deliveryMoney){
        this.deliveryMoney = deliveryMoney;
        return this;
    }
    public BigDecimal getDeliveryLessMoney(){
        return deliveryLessMoney;
    }

    public TOwnerConfig setDeliveryLessMoney( BigDecimal deliveryLessMoney){
        this.deliveryLessMoney = deliveryLessMoney;
        return this;
    }
    public BigDecimal getDeliveryGreatMoney(){
        return deliveryGreatMoney;
    }

    public TOwnerConfig setDeliveryGreatMoney( BigDecimal deliveryGreatMoney){
        this.deliveryGreatMoney = deliveryGreatMoney;
        return this;
    }
    public Integer getDeliveryDistance(){
        return deliveryDistance;
    }

    public TOwnerConfig setDeliveryDistance( Integer deliveryDistance){
        this.deliveryDistance = deliveryDistance;
        return this;
    }
    public String gevoidPhone(){
        return orderPhone;
    }

    public TOwnerConfig sevoidPhone( String orderPhone){
        this.orderPhone = orderPhone;
        return this;
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