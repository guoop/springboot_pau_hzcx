package com.soft.ware.rest.modular.auth.controller.dto;

/**
 * 订单列表查询参数
 */
public class OrderParam {

    /**
     * 订单状态
     */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
