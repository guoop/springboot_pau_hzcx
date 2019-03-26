package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
  * 商户信息
*/
@TableName("tbl_owner")
public class TblOwner extends Model<TblOwner> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //微信支付商户号
    private String shopId;
    //微信支付密钥
    private String shopSecret;
    //小程序ID
    private String appId;
    //小程序密钥
    private String appSecret;
    //小程序名称
    private String appName;
    //小程序二维码URL
    private String appQr;
    //商家在系统中的唯一标识
    private String owner;
    //商家姓名
    private String name;
    //商家电话
    private String phone;
    //商家地址
    private String address;
    //商家的描述信息
    private String description;
    //商铺的视频地址
    private String videoUrl;
    //营业起始时间
    private String timeBegin;
    //营业截止时间
    private String timeEnd;
    //商品的默认描述
    private String defaultDesc;
    //默认退款原因
    private String defaultRefundReason;
    //超市当前是否提供配送服务（0：否；1：是）
    private Integer delivery;
    //起配金额（元）
    private BigDecimal deliveryMoney;
    //当用户订单金额小于起配金额时的配送费
    private Integer deliveryLessMoney;
    //当用户订单金额大于等于起配金额时的配送费
    private Integer deliveryGreatMoney;
    //最远配送距离（如果省略或等于0，则不考虑配送距离）
    private Integer deliveryDistance;
    //超市当前是否支持货到付款（0：否；1：是）
    private Integer reachPay;
    //是否开启到店自提（0：否：1：是）
    private Integer selfPickup;
    //当有订单达到是，需要短信通知的手机号
    private String orderPhone;
    //更新时间
    private Date updatedAt;
    //更新人手机号码
    private String updatedBy;
    //该字段已废弃（商品的默认运费）
    private BigDecimal defaultFee;
    //该字段已废弃（运费的默认说明）
    private String defaultFeeDesc;

    public String getShopId(){
        return shopId;
    }

    public void setShopId( String shopId){
        this.shopId = shopId;
    }
    public String getShopSecret(){
        return shopSecret;
    }

    public void setShopSecret( String shopSecret){
        this.shopSecret = shopSecret;
    }
    public String getAppId(){
        return appId;
    }

    public TblOwner setAppId( String appId){
        this.appId = appId;
        return this;
    }
    public String getAppSecret(){
        return appSecret;
    }

    public void setAppSecret( String appSecret){
        this.appSecret = appSecret;
    }
    public String getAppName(){
        return appName;
    }

    public void setAppName( String appName){
        this.appName = appName;
    }
    public String getAppQr(){
        return appQr;
    }

    public void setAppQr( String appQr){
        this.appQr = appQr;
    }
    public String getOwner(){
        return owner;
    }

    public void setOwner( String owner){
        this.owner = owner;
    }
    public String getName(){
        return name;
    }

    public void setName( String name){
        this.name = name;
    }
    public String getPhone(){
        return phone;
    }

    public void setPhone( String phone){
        this.phone = phone;
    }
    public String getAddress(){
        return address;
    }

    public void setAddress( String address){
        this.address = address;
    }
    public String getDescription(){
        return description;
    }

    public void setDescription( String description){
        this.description = description;
    }
    public String getVideoUrl(){
        return videoUrl;
    }

    public void setVideoUrl( String videoUrl){
        this.videoUrl = videoUrl;
    }
    public String getTimeBegin(){
        return timeBegin;
    }

    public void setTimeBegin( String timeBegin){
        this.timeBegin = timeBegin;
    }
    public String getTimeEnd(){
        return timeEnd;
    }

    public void setTimeEnd( String timeEnd){
        this.timeEnd = timeEnd;
    }
    public String getDefaultDesc(){
        return defaultDesc;
    }

    public void setDefaultDesc( String defaultDesc){
        this.defaultDesc = defaultDesc;
    }
    public String getDefaultRefundReason(){
        return defaultRefundReason;
    }

    public void setDefaultRefundReason( String defaultRefundReason){
        this.defaultRefundReason = defaultRefundReason;
    }
    public Integer getDelivery(){
        return delivery;
    }

    public void setDelivery( Integer delivery){
        this.delivery = delivery;
    }
    public BigDecimal getDeliveryMoney(){
        return deliveryMoney;
    }

    public void setDeliveryMoney( BigDecimal deliveryMoney){
        this.deliveryMoney = deliveryMoney;
    }
    public Integer getDeliveryLessMoney(){
        return deliveryLessMoney;
    }

    public void setDeliveryLessMoney( Integer deliveryLessMoney){
        this.deliveryLessMoney = deliveryLessMoney;
    }
    public Integer getDeliveryGreatMoney(){
        return deliveryGreatMoney;
    }

    public void setDeliveryGreatMoney( Integer deliveryGreatMoney){
        this.deliveryGreatMoney = deliveryGreatMoney;
    }
    public Integer getDeliveryDistance(){
        return deliveryDistance;
    }

    public void setDeliveryDistance( Integer deliveryDistance){
        this.deliveryDistance = deliveryDistance;
    }
    public Integer getReachPay(){
        return reachPay;
    }

    public void setReachPay( Integer reachPay){
        this.reachPay = reachPay;
    }
    public Integer getSelfPickup(){
        return selfPickup;
    }

    public void setSelfPickup( Integer selfPickup){
        this.selfPickup = selfPickup;
    }
    public String getOrderPhone(){
        return orderPhone;
    }

    public void setOrderPhone( String orderPhone){
        this.orderPhone = orderPhone;
    }
    public Date getUpdatedAt(){
        return updatedAt;
    }

    public void setUpdatedAt( Date updatedAt){
        this.updatedAt = updatedAt;
    }
    public String getUpdatedBy(){
        return updatedBy;
    }

    public void setUpdatedBy( String updatedBy){
        this.updatedBy = updatedBy;
    }
    public BigDecimal getDefaultFee(){
        return defaultFee;
    }

    public void setDefaultFee( BigDecimal defaultFee){
        this.defaultFee = defaultFee;
    }
    public String getDefaultFeeDesc(){
        return defaultFeeDesc;
    }

    public void setDefaultFeeDesc( String defaultFeeDesc){
        this.defaultFeeDesc = defaultFeeDesc;
    }


    public  Long getId(){
        return id;
    }

    public  void setId(Long id){
        this.id = id;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TblOwner{" +
        "id=" + id +
            ", shopId=" + shopId +
            ", shopSecret=" + shopSecret +
            ", appId=" + appId +
            ", appSecret=" + appSecret +
            ", appName=" + appName +
            ", appQr=" + appQr +
            ", owner=" + owner +
            ", name=" + name +
            ", phone=" + phone +
            ", address=" + address +
            ", description=" + description +
            ", videoUrl=" + videoUrl +
            ", timeBegin=" + timeBegin +
            ", timeEnd=" + timeEnd +
            ", defaultDesc=" + defaultDesc +
            ", defaultRefundReason=" + defaultRefundReason +
            ", delivery=" + delivery +
            ", deliveryMoney=" + deliveryMoney +
            ", deliveryLessMoney=" + deliveryLessMoney +
            ", deliveryGreatMoney=" + deliveryGreatMoney +
            ", deliveryDistance=" + deliveryDistance +
            ", reachPay=" + reachPay +
            ", selfPickup=" + selfPickup +
            ", orderPhone=" + orderPhone +
            ", updatedAt=" + updatedAt +
            ", updatedBy=" + updatedBy +
            ", defaultFee=" + defaultFee +
            ", defaultFeeDesc=" + defaultFeeDesc +
        "}";
    }

}