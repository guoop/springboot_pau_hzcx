package com.soft.ware.rest.modular.auth.controller.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 订单状态更新接口 参数
 */
public class OrderUpdateStatusParam {

    @NotBlank(message = "订单状态不能为空")
    private String status;
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
