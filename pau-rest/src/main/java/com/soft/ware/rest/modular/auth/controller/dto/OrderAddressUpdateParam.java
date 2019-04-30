package com.soft.ware.rest.modular.auth.controller.dto;

/**
 * 更新订单的收货地址信息
 * 描述：订单生成后，允许买家更改订单的收货地址
 */
public class OrderAddressUpdateParam {

    //待更新收货地址的订单编号
    private String orderNO;
    //收货地址ID
    private Long addressID;


    public String gevoidNO() {
        return orderNO;
    }

    public void sevoidNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public Long getAddressID() {
        return addressID;
    }

    public void setAddressID(Long addressID) {
        this.addressID = addressID;
    }
}
