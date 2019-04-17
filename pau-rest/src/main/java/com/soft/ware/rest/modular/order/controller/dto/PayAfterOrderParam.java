package com.soft.ware.rest.modular.order.controller.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 支付后订单调用参数
 */
public class PayAfterOrderParam {

    //订单号
    private String orderNO;
    //package
    @JsonProperty(value = "package")
    private String pack;
    //formID
    private String formID;

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }
}
