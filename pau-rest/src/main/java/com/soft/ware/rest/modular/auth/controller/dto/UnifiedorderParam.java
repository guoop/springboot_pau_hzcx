package com.soft.ware.rest.modular.auth.controller.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 订单支付参数
 */
public class UnifiedorderParam {

    @NotBlank(message = "orderNO 不能为空")
    private String orderNO;
    @NotBlank(message = "source 不能为空")
    private Integer source;
    private String telephone;

    public String getOrderNO() {
        return orderNO;
    }

    public void setorderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
