package com.soft.ware.rest.modular.order.controller.dto;

import java.util.Date;

/**
 * 支付后订单调用参数
 */
public class PayAfterOrderParam {

    //订单支付方式（0：在线支付；1：货到付款）
    private int moneyChannel;
    //下单时间（发送模板消息使用）
    private Date created_at;
    //商品名称（发送模板消息使用）
    private String goodsName;
    //订单备注信息（更新订单信息）
    private String remark;
    //订单来源（2：到店自提）
    private Integer source;
    //订单号
    private String orderNO;
    //package
    private String pack;
    //是个long值
    private Long pickup_time;
    //formID
    private String formID;

    private String addressId;

    public int getMoneyChannel() {
        return moneyChannel;
    }

    public void setMoneyChannel(int moneyChannel) {
        this.moneyChannel = moneyChannel;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
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

    public Long getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(Long pickup_time) {
        this.pickup_time = pickup_time;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}
