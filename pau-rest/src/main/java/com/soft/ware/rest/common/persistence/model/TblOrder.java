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

    public void setNo( String no){
        this.no = no;
    }
    public Integer getSource(){
        return source;
    }

    public void setSource(Integer source){
        this.source = source;
    }
    public Long getPickupNo(){
        return pickupNo;
    }

    public void setPickupNo( Long pickupNo){
        this.pickupNo = pickupNo;
    }
    public Date getPickupTime(){
        return pickupTime;
    }

    public void setPickupTime( Date pickupTime){
        this.pickupTime = pickupTime;
    }
    public Integer getMoneyChannel(){
        return moneyChannel;
    }

    public void setMoneyChannel( Integer moneyChannel){
        this.moneyChannel = moneyChannel;
    }
    public BigDecimal getMoney(){
        return money;
    }

    public void setMoney( BigDecimal money){
        this.money = money;
    }
    public BigDecimal getFreight(){
        return freight;
    }

    public void setFreight( BigDecimal freight){
        this.freight = freight;
    }
    public BigDecimal getPayMoney(){
        return payMoney;
    }

    public void setPayMoney( BigDecimal payMoney){
        this.payMoney = payMoney;
    }
    public Date getPayAt(){
        return payAt;
    }

    public void setPayAt( Date payAt){
        this.payAt = payAt;
    }
    public String getPayResponse(){
        return payResponse;
    }

    public void setPayResponse( String payResponse){
        this.payResponse = payResponse;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
    }
    public String getCreatedBy(){
        return createdBy;
    }

    public void setCreatedBy( String createdBy){
        this.createdBy = createdBy;
    }
    public String getSettlementBy(){
        return settlementBy;
    }

    public void setSettlementBy( String settlementBy){
        this.settlementBy = settlementBy;
    }
    public String getOwner(){
        return owner;
    }

    public void setOwner( String owner){
        this.owner = owner;
    }
    public String getConsigneeName(){
        return consigneeName;
    }

    public void setConsigneeName( String consigneeName){
        this.consigneeName = consigneeName;
    }
    public String getConsigneeMobile(){
        return consigneeMobile;
    }

    public void setConsigneeMobile( String consigneeMobile){
        this.consigneeMobile = consigneeMobile;
    }
    public String getConsigneeAddress(){
        return consigneeAddress;
    }

    public void setConsigneeAddress( String consigneeAddress){
        this.consigneeAddress = consigneeAddress;
    }
    public String getGoods(){
        return goods;
    }

    public void setGoods( String goods){
        this.goods = goods;
    }
    public Integer getStatus(){
        return status;
    }

    public void setStatus( Integer status){
        this.status = status;
    }
    public String getRemark(){
        return remark;
    }

    public void setRemark( String remark){
        this.remark = remark;
    }
    public String getConfirmBy(){
        return confirmBy;
    }

    public void setConfirmBy( String confirmBy){
        this.confirmBy = confirmBy;
    }
    public Date getConfirmAt(){
        return confirmAt;
    }

    public void setConfirmAt( Date confirmAt){
        this.confirmAt = confirmAt;
    }
    public Date getDistributionAt(){
        return distributionAt;
    }

    public void setDistributionAt( Date distributionAt){
        this.distributionAt = distributionAt;
    }
    public String getDistributionBy(){
        return distributionBy;
    }

    public void setDistributionBy( String distributionBy){
        this.distributionBy = distributionBy;
    }
    public Date getDoneAt(){
        return doneAt;
    }

    public void setDoneAt( Date doneAt){
        this.doneAt = doneAt;
    }
    public String getDoneBy(){
        return doneBy;
    }

    public void setDoneBy( String doneBy){
        this.doneBy = doneBy;
    }
    public Date getCancelAt(){
        return cancelAt;
    }

    public void setCancelAt( Date cancelAt){
        this.cancelAt = cancelAt;
    }
    public String getCancelBy(){
        return cancelBy;
    }

    public void setCancelBy( String cancelBy){
        this.cancelBy = cancelBy;
    }
    public String getCancelReason(){
        return cancelReason;
    }

    public void setCancelReason( String cancelReason){
        this.cancelReason = cancelReason;
    }
    public Date getRefundAt(){
        return refundAt;
    }

    public void setRefundAt( Date refundAt){
        this.refundAt = refundAt;
    }
    public String getRefundBy(){
        return refundBy;
    }

    public void setRefundBy( String refundBy){
        this.refundBy = refundBy;
    }
    public BigDecimal getRefundMoney(){
        return refundMoney;
    }

    public void setRefundMoney( BigDecimal refundMoney){
        this.refundMoney = refundMoney;
    }
    public String getRefundReason(){
        return refundReason;
    }

    public void setRefundReason( String refundReason){
        this.refundReason = refundReason;
    }
    public Integer getRefundStatus(){
        return refundStatus;
    }

    public void setRefundStatus( Integer refundStatus){
        this.refundStatus = refundStatus;
    }
    public String getRefundResponse(){
        return refundResponse;
    }

    public void setRefundResponse( String refundResponse){
        this.refundResponse = refundResponse;
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