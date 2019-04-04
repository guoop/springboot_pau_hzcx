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


    public Long getId(){
        return id;
    }

    public TblOwner setId(Long id){
        this.id = id;
        return this;
    }


    public String getShopId(){
        return shopId;
    }

    public TblOwner setShopId( String shopId){
        this.shopId = shopId;
        return this;
    }
    public String getShopSecret(){
        return shopSecret;
    }

    public TblOwner setShopSecret( String shopSecret){
        this.shopSecret = shopSecret;
        return this;
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

    public TblOwner setAppSecret( String appSecret){
        this.appSecret = appSecret;
        return this;
    }
    public String getAppName(){
        return appName;
    }

    public TblOwner setAppName( String appName){
        this.appName = appName;
        return this;
    }
    public String getAppQr(){
        return appQr;
    }

    public TblOwner setAppQr( String appQr){
        this.appQr = appQr;
        return this;
    }
    public String getOwner(){
        return owner;
    }

    public TblOwner setOwner( String owner){
        this.owner = owner;
        return this;
    }
    public String getName(){
        return name;
    }

    public TblOwner setName( String name){
        this.name = name;
        return this;
    }
    public String getPhone(){
        return phone;
    }

    public TblOwner setPhone( String phone){
        this.phone = phone;
        return this;
    }
    public String getAddress(){
        return address;
    }

    public TblOwner setAddress( String address){
        this.address = address;
        return this;
    }
    public String getDescription(){
        return description;
    }

    public TblOwner setDescription( String description){
        this.description = description;
        return this;
    }
    public String getVideoUrl(){
        return videoUrl;
    }

    public TblOwner setVideoUrl( String videoUrl){
        this.videoUrl = videoUrl;
        return this;
    }
    public String getTimeBegin(){
        return timeBegin;
    }

    public TblOwner setTimeBegin( String timeBegin){
        this.timeBegin = timeBegin;
        return this;
    }
    public String getTimeEnd(){
        return timeEnd;
    }

    public TblOwner setTimeEnd( String timeEnd){
        this.timeEnd = timeEnd;
        return this;
    }
    public String getDefaultDesc(){
        return defaultDesc;
    }

    public TblOwner setDefaultDesc( String defaultDesc){
        this.defaultDesc = defaultDesc;
        return this;
    }
    public String getDefaultRefundReason(){
        return defaultRefundReason;
    }

    public TblOwner setDefaultRefundReason( String defaultRefundReason){
        this.defaultRefundReason = defaultRefundReason;
        return this;
    }
    public Integer getDelivery(){
        return delivery;
    }

    public TblOwner setDelivery( Integer delivery){
        this.delivery = delivery;
        return this;
    }
    public BigDecimal getDeliveryMoney(){
        return deliveryMoney;
    }

    public TblOwner setDeliveryMoney( BigDecimal deliveryMoney){
        this.deliveryMoney = deliveryMoney;
        return this;
    }
    public Integer getDeliveryLessMoney(){
        return deliveryLessMoney;
    }

    public TblOwner setDeliveryLessMoney( Integer deliveryLessMoney){
        this.deliveryLessMoney = deliveryLessMoney;
        return this;
    }
    public Integer getDeliveryGreatMoney(){
        return deliveryGreatMoney;
    }

    public TblOwner setDeliveryGreatMoney( Integer deliveryGreatMoney){
        this.deliveryGreatMoney = deliveryGreatMoney;
        return this;
    }
    public Integer getDeliveryDistance(){
        return deliveryDistance;
    }

    public TblOwner setDeliveryDistance( Integer deliveryDistance){
        this.deliveryDistance = deliveryDistance;
        return this;
    }
    public Integer getReachPay(){
        return reachPay;
    }

    public TblOwner setReachPay( Integer reachPay){
        this.reachPay = reachPay;
        return this;
    }
    public Integer getSelfPickup(){
        return selfPickup;
    }

    public TblOwner setSelfPickup( Integer selfPickup){
        this.selfPickup = selfPickup;
        return this;
    }
    public String getOrderPhone(){
        return orderPhone;
    }

    public TblOwner setOrderPhone( String orderPhone){
        this.orderPhone = orderPhone;
        return this;
    }
    public Date getUpdatedAt(){
        return updatedAt;
    }

    public TblOwner setUpdatedAt( Date updatedAt){
        this.updatedAt = updatedAt;
        return this;
    }
    public String getUpdatedBy(){
        return updatedBy;
    }

    public TblOwner setUpdatedBy( String updatedBy){
        this.updatedBy = updatedBy;
        return this;
    }
    public BigDecimal getDefaultFee(){
        return defaultFee;
    }

    public TblOwner setDefaultFee( BigDecimal defaultFee){
        this.defaultFee = defaultFee;
        return this;
    }
    public String getDefaultFeeDesc(){
        return defaultFeeDesc;
    }

    public TblOwner setDefaultFeeDesc( String defaultFeeDesc){
        this.defaultFeeDesc = defaultFeeDesc;
        return this;
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