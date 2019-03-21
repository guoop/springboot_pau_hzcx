package com.soft.ware.rest.modular.auth.controller.dto;

import org.hibernate.validator.constraints.NotBlank;

public class OrderUpdateStatusParam {

    @NotBlank(message = "订单状态不能为空")
    private String status;
    @NotBlank(message = "订单号不能为空")
    private String no;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
