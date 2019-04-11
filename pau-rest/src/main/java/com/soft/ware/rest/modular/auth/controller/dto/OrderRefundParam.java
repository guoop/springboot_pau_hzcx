package com.soft.ware.rest.modular.auth.controller.dto;

import java.math.BigDecimal;

/**
 * 商家退款给用户接口参数
 */
public class OrderRefundParam {

    private String order;
    private String refundReason;
    private String refundType;
    private BigDecimal refundMoney;

    public String gevoid() {
        return order;
    }

    public void sevoid(String order) {
        this.order = order;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }


    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }
}
