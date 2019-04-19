package com.soft.ware.rest.modular.auth.controller.dto;

import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.Map;

public class AddOrderParam {

    private String owner;
    //订单编号
    private String no;
    //（支付方式：1现金、2微信、3支付宝、4银联，多种支付方式以_
    private String money_channel;
    //（订单金额，单位元，精确到分），
    private BigDecimal money;
    //（订单来源：0：android收银机、1：windows收银机）
    private Integer source;
    //(订单优惠后金额)
    private BigDecimal money_dpay;
    //(实收)
    private BigDecimal money_shishou;
    //(找零)
    private BigDecimal money_zhaol;
    //(支付明细  格式(支付方式_支付金钱，支付方式_支付金钱))
    private String channel_pay;
    //pay_at
    private long pay_at;
    //(订单状态  0 为正常  1 ：退单  2 ：反结账)
    private Integer status;
    //（结算人标识（收银app登录账号）），
    private String settlement_by;
    //（订单包含的商品清单）,
    private String goods;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMoney_channel() {
        return money_channel;
    }

    public void setMoney_channel(String money_channel) {
        this.money_channel = money_channel;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public BigDecimal getMoney_dpay() {
        return money_dpay;
    }

    public void setMoney_dpay(BigDecimal money_dpay) {
        this.money_dpay = money_dpay;
    }

    public BigDecimal getMoney_shishou() {
        return money_shishou;
    }

    public void setMoney_shishou(BigDecimal money_shishou) {
        this.money_shishou = money_shishou;
    }

    public BigDecimal getMoney_zhaol() {
        return money_zhaol;
    }

    public void setMoney_zhaol(BigDecimal money_zhaol) {
        this.money_zhaol = money_zhaol;
    }

    public String getChannel_pay() {
        return channel_pay;
    }

    public void setChannel_pay(String channel_pay) {
        this.channel_pay = channel_pay;
    }

    public long getPay_at() {
        return pay_at;
    }

    public void setPay_at(long pay_at) {
        this.pay_at = pay_at;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSettlement_by() {
        return settlement_by;
    }

    public void setSettlement_by(String settlement_by) {
        this.settlement_by = settlement_by;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }


    public Map<Integer,BigDecimal> getPayMap(){
        String[] ss = getChannel_pay().split(",");
        Map<Integer, BigDecimal> map = Maps.newLinkedHashMap();
        String c;
        String n;
        for (String s : ss) {
            c = s.substring(0, s.indexOf("_"));
            n = s.substring(s.indexOf("_")+1);
            map.put(Integer.valueOf(c),BigDecimal.valueOf(Double.valueOf(n)));
        }

        return map;
    }
}
