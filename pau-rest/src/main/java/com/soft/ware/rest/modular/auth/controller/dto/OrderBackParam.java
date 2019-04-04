package com.soft.ware.rest.modular.auth.controller.dto;

import java.math.BigDecimal;

/**
 *  收银app订单退款接口参数
 */
public class OrderBackParam {

    //订单编号
    private String orderNo;
    //退款方式（all 全退、part 部分退） 、
    private String refundType;
    //退款原因
    private String refundReason;
    //部分退款金额
    private BigDecimal refundMoney;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }
}
