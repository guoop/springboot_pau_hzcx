package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
  * 订单差价
*/
@TableName("tbl_order_money_diff")
public class TblOrderMoneyDiff extends Model<TblOrderMoneyDiff> {

    private static final long serialVersionUID = 1L;


    //：未补差价；
    public static Integer status_0 = 0;
    //：已补差价）
    public static Integer status_1 = 1;
    //：处理中；
    public static Integer refund_status_0 = 0;
    //：成功；
    public static Integer refund_status_1 = 1;
    //：失败）
    public static Integer refund_status_2 = 2;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //支付订单号
    private String no;
    //订单属主
    private String owner;
    //订单编号（关联到表tbl_order的no字段）
    private String orderNo;
    //小票金额
    private BigDecimal money;
    //差价（小票金额-订单金额，如果为正数则需要买家补足差价，如果为负数则需要商家退款差价）
    private BigDecimal moneyDiff;
    //小票图片地址
    private String pic;
    //添加时间
    private Date createdAt;
    //状态（0：未补差价；1：已补差价）
    private Integer status;
    //支付成功时间
    private Date payAt;
    //支付信息
    private String payResponse;
    //退款人
    private String refundBy;
    //退款时间
    private Date refundAt;
    //退款状态（0：处理中；1：成功；2：失败）
    private Integer refundStatus;
    //退款信息
    private String refundResponse;


    public Long getId(){
        return id;
    }

    public TblOrderMoneyDiff setId(Long id){
        this.id = id;
        return this;
    }


    public String getNo(){
        return no;
    }

    public TblOrderMoneyDiff setNo( String no){
        this.no = no;
        return this;
    }
    public String getOwner(){
        return owner;
    }

    public TblOrderMoneyDiff setOwner( String owner){
        this.owner = owner;
        return this;
    }
    public String getOrderNo(){
        return orderNo;
    }

    public TblOrderMoneyDiff setOrderNo( String orderNo){
        this.orderNo = orderNo;
        return this;
    }
    public BigDecimal getMoney(){
        return money;
    }

    public TblOrderMoneyDiff setMoney( BigDecimal money){
        this.money = money;
        return this;
    }
    public BigDecimal getMoneyDiff(){
        return moneyDiff;
    }

    public TblOrderMoneyDiff setMoneyDiff( BigDecimal moneyDiff){
        this.moneyDiff = moneyDiff;
        return this;
    }
    public String getPic(){
        return pic;
    }

    public TblOrderMoneyDiff setPic( String pic){
        this.pic = pic;
        return this;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public TblOrderMoneyDiff setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
        return this;
    }
    public Integer getStatus(){
        return status;
    }

    public TblOrderMoneyDiff setStatus( Integer status){
        this.status = status;
        return this;
    }
    public Date getPayAt(){
        return payAt;
    }

    public TblOrderMoneyDiff setPayAt( Date payAt){
        this.payAt = payAt;
        return this;
    }
    public String getPayResponse(){
        return payResponse;
    }

    public TblOrderMoneyDiff setPayResponse( String payResponse){
        this.payResponse = payResponse;
        return this;
    }
    public String getRefundBy(){
        return refundBy;
    }

    public TblOrderMoneyDiff setRefundBy( String refundBy){
        this.refundBy = refundBy;
        return this;
    }
    public Date getRefundAt(){
        return refundAt;
    }

    public TblOrderMoneyDiff setRefundAt( Date refundAt){
        this.refundAt = refundAt;
        return this;
    }
    public Integer getRefundStatus(){
        return refundStatus;
    }

    public TblOrderMoneyDiff setRefundStatus( Integer refundStatus){
        this.refundStatus = refundStatus;
        return this;
    }
    public String getRefundResponse(){
        return refundResponse;
    }

    public TblOrderMoneyDiff setRefundResponse( String refundResponse){
        this.refundResponse = refundResponse;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TblOrderMoneyDiff{" +
        "id=" + id +
            ", no=" + no +
            ", owner=" + owner +
            ", orderNo=" + orderNo +
            ", money=" + money +
            ", moneyDiff=" + moneyDiff +
            ", pic=" + pic +
            ", createdAt=" + createdAt +
            ", status=" + status +
            ", payAt=" + payAt +
            ", payResponse=" + payResponse +
            ", refundBy=" + refundBy +
            ", refundAt=" + refundAt +
            ", refundStatus=" + refundStatus +
            ", refundResponse=" + refundResponse +
        "}";
    }

}