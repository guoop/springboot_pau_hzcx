package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
  * 订单信息
*/
@TableName("tbl_order")
public class TblOrder extends Model<TblOrder> {

    private static final long serialVersionUID = 1L;


    //：微信小程序；
    public static int SOURCE_0 = 0;
    //：收银机；
    public static int SOURCE_1 = 1;
    //：到店自取订单）
    public static int source_2 = 2;
    //：在线支付；
    public static Integer money_channel_0 = 0;
    //：货到付款；
    public static Integer money_channel_1 = 1;
    //：现金；
    public static Integer money_channel_2 = 2;
    //：微信；
    public static Integer money_channel_3 = 3;
    //：支付宝；
    public static Integer money_channel_4 = 4;
    //：银联）
    public static Integer money_channel_5 = 5;
    //-
    public static Integer status_03 = -3;
    //：配送中；
    public static Integer status_2 = 2;
    //：待商家确认（在线支付支付成功、货到付款下单成功）；
    public static Integer status_1 = 1;
    //：待付款（新建订单）；
    public static Integer status_0 = 0;
    //：已完成（在线支付配送完成；货到付款付款功能）；
    public static Integer status_3 = 3;
    //：商家确认接单（主要用于配送前及时提醒买家商家已接单））
    public static Integer status_10 = 10;
    //-
    public static String cancel_by_01 = "-1";
    //：处理中；
    public static Integer refund_status_0 = 0;
    //：成功；
    public static Integer refund_status_1 = 1;
    //：失败）
    public static Integer refund_status_2 = 2;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //订单编号
    private String no;
    //订单来源（0：微信小程序；1：收银机；2：到店自取订单）
    private Integer source;
    //取货码（只有到店自取的订单有该字段）
    private Long pickupNo;
    //取货时间（只有到店自取的订单有该字段）
    private Date pickupTime;
    //付款方式（0：在线支付；1：货到付款；2：现金；3：微信；4：支付宝；5：银联）
    private Integer moneyChannel;
    //订单金额，即：买家购买商品的总价（不包含运费）
    private BigDecimal money;
    //运费
    private BigDecimal freight;
    //买家支付金额（包含购买商品的总价和运费）
    private BigDecimal payMoney;
    //成功付款时间（如果是在线支付，则标识回调成功时间）
    private Date payAt;
    //支付成功后的回调原始结果
    private String payResponse;
    //创建时间
    private Date createdAt;
    //创建人
    private String createdBy;
    //订单结算人（只有收银系统产生的订单存在该字段，标识结算人）
    private String settlementBy;
    //所属人
    private String owner;
    //收货人姓名
    private String consigneeName;
    //收货人电话
    private String consigneeMobile;
    //收货人详细地址
    private String consigneeAddress;
    //订单包含的商品信息（商品之间已英文逗号分隔，单个货物的的格式为：id__图片地址__名称__规格__数量__单价__总价）
    private String goods;
    //订单状态（-3：已删除；-2：过期失效；-1：手动取消；0：待付款（新建订单）；1：待商家确认（在线支付支付成功、货到付款下单成功）；2：配送中；3：已完成（在线支付配送完成；货到付款付款功能）；10：商家确认接单（主要用于配送前及时提醒买家商家已接单））
    private Integer status;
    //备注信息
    private String remark;
    //订单确认人
    private String confirmBy;
    //订单确认时间
    private Date confirmAt;
    //开始配送时间
    private Date distributionAt;
    //配送人
    private String distributionBy;
    //订单完成时间
    private Date doneAt;
    //订单完成人
    private String doneBy;
    //订单取消时间
    private Date cancelAt;
    //取消人（-1：系统自动取消超时订单）
    private String cancelBy;
    //订单取消原因
    private String cancelReason;
    //退款时间
    private Date refundAt;
    //退款人
    private String refundBy;
    //退款金额
    private BigDecimal refundMoney;
    //退款原因
    private String refundReason;
    //退款状态（0：处理中；1：成功；2：失败）
    private Integer refundStatus;
    //记录第三方返回结果
    private String refundResponse;

    public String getNo(){
        return no;
    }

    public TblOrder setNo( String no){
        this.no = no;
        return this;
    }
    public Integer getSource(){
        return source;
    }

    public TblOrder setSource(Integer source){
        this.source = source;
        return this;
    }
    public Long getPickupNo(){
        return pickupNo;
    }

    public TblOrder setPickupNo( Long pickupNo){
        this.pickupNo = pickupNo;
        return this;
    }
    public Date getPickupTime(){
        return pickupTime;
    }

    public TblOrder setPickupTime( Date pickupTime){
        this.pickupTime = pickupTime;
        return this;
    }
    public Integer getMoneyChannel(){
        return moneyChannel;
    }

    public TblOrder setMoneyChannel( Integer moneyChannel){
        this.moneyChannel = moneyChannel;
        return this;
    }
    public BigDecimal getMoney(){
        return money;
    }

    public TblOrder setMoney( BigDecimal money){
        this.money = money;
        return this;
    }
    public BigDecimal getFreight(){
        return freight;
    }

    public TblOrder setFreight( BigDecimal freight){
        this.freight = freight;
        return this;
    }
    public BigDecimal getPayMoney(){
        return payMoney;
    }

    public TblOrder setPayMoney( BigDecimal payMoney){
        this.payMoney = payMoney;
        return this;
    }
    public Date getPayAt(){
        return payAt;
    }

    public TblOrder setPayAt( Date payAt){
        this.payAt = payAt;
        return this;
    }
    public String getPayResponse(){
        return payResponse;
    }

    public TblOrder setPayResponse( String payResponse){
        this.payResponse = payResponse;
        return this;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public TblOrder setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
        return this;
    }
    public String getCreatedBy(){
        return createdBy;
    }

    public TblOrder setCreatedBy( String createdBy){
        this.createdBy = createdBy;
        return this;
    }
    public String getSettlementBy(){
        return settlementBy;
    }

    public TblOrder setSettlementBy( String settlementBy){
        this.settlementBy = settlementBy;
        return this;
    }
    public String getOwner(){
        return owner;
    }

    public TblOrder setOwner( String owner){
        this.owner = owner;
        return this;
    }
    public String getConsigneeName(){
        return consigneeName;
    }

    public TblOrder setConsigneeName( String consigneeName){
        this.consigneeName = consigneeName;
        return this;
    }
    public String getConsigneeMobile(){
        return consigneeMobile;
    }

    public TblOrder setConsigneeMobile( String consigneeMobile){
        this.consigneeMobile = consigneeMobile;
        return this;
    }
    public String getConsigneeAddress(){
        return consigneeAddress;
    }

    public TblOrder setConsigneeAddress( String consigneeAddress){
        this.consigneeAddress = consigneeAddress;
        return this;
    }
    public String getGoods(){
        return goods;
    }

    public TblOrder setGoods( String goods){
        this.goods = goods;
        return this;
    }
    public Integer getStatus(){
        return status;
    }

    public TblOrder setStatus( Integer status){
        this.status = status;
        return this;
    }
    public String getRemark(){
        return remark;
    }

    public TblOrder setRemark( String remark){
        this.remark = remark;
        return this;
    }
    public String getConfirmBy(){
        return confirmBy;
    }

    public TblOrder setConfirmBy( String confirmBy){
        this.confirmBy = confirmBy;
        return this;
    }
    public Date getConfirmAt(){
        return confirmAt;
    }

    public TblOrder setConfirmAt( Date confirmAt){
        this.confirmAt = confirmAt;
        return this;
    }
    public Date getDistributionAt(){
        return distributionAt;
    }

    public TblOrder setDistributionAt( Date distributionAt){
        this.distributionAt = distributionAt;
        return this;
    }
    public String getDistributionBy(){
        return distributionBy;
    }

    public TblOrder setDistributionBy( String distributionBy){
        this.distributionBy = distributionBy;
        return this;
    }
    public Date getDoneAt(){
        return doneAt;
    }

    public TblOrder setDoneAt( Date doneAt){
        this.doneAt = doneAt;
        return this;
    }
    public String getDoneBy(){
        return doneBy;
    }

    public TblOrder setDoneBy( String doneBy){
        this.doneBy = doneBy;
        return this;
    }
    public Date getCancelAt(){
        return cancelAt;
    }

    public TblOrder setCancelAt( Date cancelAt){
        this.cancelAt = cancelAt;
        return this;
    }
    public String getCancelBy(){
        return cancelBy;
    }

    public TblOrder setCancelBy( String cancelBy){
        this.cancelBy = cancelBy;
        return this;
    }
    public String getCancelReason(){
        return cancelReason;
    }

    public TblOrder setCancelReason( String cancelReason){
        this.cancelReason = cancelReason;
        return this;
    }
    public Date getRefundAt(){
        return refundAt;
    }

    public TblOrder setRefundAt( Date refundAt){
        this.refundAt = refundAt;
        return this;
    }
    public String getRefundBy(){
        return refundBy;
    }

    public TblOrder setRefundBy( String refundBy){
        this.refundBy = refundBy;
        return this;
    }
    public BigDecimal getRefundMoney(){
        return refundMoney;
    }

    public TblOrder setRefundMoney( BigDecimal refundMoney){
        this.refundMoney = refundMoney;
        return this;
    }
    public String getRefundReason(){
        return refundReason;
    }

    public TblOrder setRefundReason( String refundReason){
        this.refundReason = refundReason;
        return this;
    }
    public Integer getRefundStatus(){
        return refundStatus;
    }

    public TblOrder setRefundStatus( Integer refundStatus){
        this.refundStatus = refundStatus;
        return this;
    }
    public String getRefundResponse(){
        return refundResponse;
    }

    public TblOrder setRefundResponse( String refundResponse){
        this.refundResponse = refundResponse;
        return this;
    }


    public Long getId(){
        return id;
    }

    public TblOrder setId(Long id){
        this.id = id;
        return this;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TblOrder{" +
        "id=" + id +
            ", no=" + no +
            ", source=" + source +
            ", pickupNo=" + pickupNo +
            ", pickupTime=" + pickupTime +
            ", moneyChannel=" + moneyChannel +
            ", money=" + money +
            ", freight=" + freight +
            ", payMoney=" + payMoney +
            ", payAt=" + payAt +
            ", payResponse=" + payResponse +
            ", createdAt=" + createdAt +
            ", createdBy=" + createdBy +
            ", settlementBy=" + settlementBy +
            ", owner=" + owner +
            ", consigneeName=" + consigneeName +
            ", consigneeMobile=" + consigneeMobile +
            ", consigneeAddress=" + consigneeAddress +
            ", goods=" + goods +
            ", status=" + status +
            ", remark=" + remark +
            ", confirmBy=" + confirmBy +
            ", confirmAt=" + confirmAt +
            ", distributionAt=" + distributionAt +
            ", distributionBy=" + distributionBy +
            ", doneAt=" + doneAt +
            ", doneBy=" + doneBy +
            ", cancelAt=" + cancelAt +
            ", cancelBy=" + cancelBy +
            ", cancelReason=" + cancelReason +
            ", refundAt=" + refundAt +
            ", refundBy=" + refundBy +
            ", refundMoney=" + refundMoney +
            ", refundReason=" + refundReason +
            ", refundStatus=" + refundStatus +
            ", refundResponse=" + refundResponse +
        "}";
    }

}