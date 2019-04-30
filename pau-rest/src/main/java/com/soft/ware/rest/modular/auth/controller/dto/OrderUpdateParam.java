package com.soft.ware.rest.modular.auth.controller.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 更新订单信息
 *
 *
 *
 *
 *
 *
 */
public class OrderUpdateParam {

    //address:"北京市,北京市,东城区 ffsdfdsf"
    //created_at:"2019-03-27 11:27:34"
    //formID:"the formId is a mock one"
    //goodsName:"五彩豆70g "
    //moneyChannel:0
    //name:"yan'chuang"
    //orderNO:"42eba7e0504011e99d325b5a108c33ee"
    //package:"wx27112742075557f1fc57685f3454133749"
    //payMoney:"0.01"
    //pickup_time:1553657220000
    //source:2
    //telephone:"15136757969"

    //订单支付方式（0：在线支付；1：货到付款）
    private int moneyChannel;
    //下单时间（发送模板消息使用）
    private Date created_at;
    //订单金额（发送模板消息使用）
    private BigDecimal payMoney;
    //商品名称（发送模板消息使用）
    private String goodsName;
    //收货地址（发送模板消息使用）/收货人详细收货地址（更新订单信息）
    private String address;
    //收货人姓名（更新订单信息）
    private String name;
    //收货人手机号码（更新订单信息）
    private String telephone;
    //订单备注信息（更新订单信息）
    private String remark;
    //订单来源（2：到店自提）
    private Integer source;
    //订单号
    private String orderNO;
    //
    private String pack;
    //是个long值
    private Long pickup_time;
    //formID
    private String formID;


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

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String gevoidNO() {
        return orderNO;
    }

    public void sevoidNO(String orderNO) {
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

    /**
     * 辅助pack，因为package是关键字
     * @param pack
     * @return
     */
    public OrderUpdateParam setPackage(String pack){
        this.pack = pack;
        return this;
    }
}
