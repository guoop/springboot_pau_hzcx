package com.soft.ware.rest.modular.order.controller.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 支付后订单调用参数
 */
public class PayAfterOrderParam {

    //订单支付方式（0：在线支付；1：货到付款）
    private int moneyChannel;
    //商品名称（发送模板消息使用）
    private String goodsName;
    //订单备注信息（更新订单信息）
    private String remark;
    //订单来源（2：到店自提）
    private Integer source;
    //订单号
    private String orderNO;
    //package
    @JsonProperty(value = "package")
    private String pack;
    //是个long值
    private Long pickupTime;
    //formID
    private String formID;

    public int getMoneyChannel() {
        return moneyChannel;
    }

    public void setMoneyChannel(int moneyChannel) {
        this.moneyChannel = moneyChannel;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

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

    public Long getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Long pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }
}
