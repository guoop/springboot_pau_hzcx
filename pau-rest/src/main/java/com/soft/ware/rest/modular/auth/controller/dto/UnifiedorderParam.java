package com.soft.ware.rest.modular.auth.controller.dto;

/**
 * 订单支付参数
 */
public class UnifiedorderParam {

    private String orderNO;
    private Integer source;
    private String telephone;

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
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
