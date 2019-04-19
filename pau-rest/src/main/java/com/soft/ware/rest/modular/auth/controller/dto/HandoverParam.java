package com.soft.ware.rest.modular.auth.controller.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 //交接班请求参数
 */
public class HandoverParam {

    //操作人（登录）账号
    private String  phone;
    //操作人名字
    private String  name;
    //收银机编号
    private String  pospalcode;
    //账号登录时间
    private Long  optionstart;
    //交接班结束时间
    private Long optionat;
    //收银单数
    private Integer ordernum;
    //退单数
    private Integer ordertuinum;
    //反结账数
    private Integer  orderreturnnum;
    //总价
    private BigDecimal  allmoney;
    //收银总额
    private BigDecimal ordermoney;
    //时收
    private BigDecimal  money_shishou;
    //找零
    private BigDecimal  zhaoling;
    //会员充值
    private BigDecimal  membercz;
    //退款额
    private BigDecimal  ordertuimoney;
    //反结账额
    private BigDecimal  orderreturnmoney;
    //微信支付额
    private BigDecimal  wxpay;
    //支付宝支付额
    private BigDecimal  alipay;
    //银联支付额
    private BigDecimal  unionpay;
    //现金支付额
    private BigDecimal  moneypay;
    //现金会员支付额
    private BigDecimal  moneymemberpay;
    //微信支付单数
    private Integer  wxpaynum;
    //支付宝支付单数
    private Integer  alipaynum;
    //银联支付单数
    private Integer  unionpaynum;
    //现金支付单数
    private Integer  moneypaynum;
    //现金会员支付单数
    private Integer  momberpaynum;
    //第一笔收银订单时间
    private Long  fistordertime;
    //最后一笔订单时间
    private Long  lastordertime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPospalcode() {
        return pospalcode;
    }

    public void setPospalcode(String pospalcode) {
        this.pospalcode = pospalcode;
    }

    public Long getOptionstart() {
        return optionstart;
    }

    public void setOptionstart(Long optionstart) {
        this.optionstart = optionstart;
    }

    public Long getOptionat() {
        return optionat;
    }

    public void setOptionat(Long optionat) {
        this.optionat = optionat;
    }

    public int getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public Integer getOrdertuinum() {
        return ordertuinum;
    }

    public void setOrdertuinum(Integer ordertuinum) {
        this.ordertuinum = ordertuinum;
    }

    public Integer getOrderreturnnum() {
        return orderreturnnum;
    }

    public void setOrderreturnnum(Integer orderreturnnum) {
        this.orderreturnnum = orderreturnnum;
    }

    public BigDecimal getAllmoney() {
        return allmoney;
    }

    public void setAllmoney(BigDecimal allmoney) {
        this.allmoney = allmoney;
    }

    public BigDecimal getOrdermoney() {
        return ordermoney;
    }

    public void setOrdermoney(BigDecimal ordermoney) {
        this.ordermoney = ordermoney;
    }

    public BigDecimal getMoney_shishou() {
        return money_shishou;
    }

    public void setMoney_shishou(BigDecimal money_shishou) {
        this.money_shishou = money_shishou;
    }

    public BigDecimal getZhaoling() {
        return zhaoling;
    }

    public void setZhaoling(BigDecimal zhaoling) {
        this.zhaoling = zhaoling;
    }

    public BigDecimal getMembercz() {
        return membercz;
    }

    public void setMembercz(BigDecimal membercz) {
        this.membercz = membercz;
    }

    public BigDecimal getOrdertuimoney() {
        return ordertuimoney;
    }

    public void setOrdertuimoney(BigDecimal ordertuimoney) {
        this.ordertuimoney = ordertuimoney;
    }

    public BigDecimal getOrderreturnmoney() {
        return orderreturnmoney;
    }

    public void setOrderreturnmoney(BigDecimal orderreturnmoney) {
        this.orderreturnmoney = orderreturnmoney;
    }

    public BigDecimal getWxpay() {
        return wxpay;
    }

    public void setWxpay(BigDecimal wxpay) {
        this.wxpay = wxpay;
    }

    public BigDecimal getAlipay() {
        return alipay;
    }

    public void setAlipay(BigDecimal alipay) {
        this.alipay = alipay;
    }

    public BigDecimal getUnionpay() {
        return unionpay;
    }

    public void setUnionpay(BigDecimal unionpay) {
        this.unionpay = unionpay;
    }

    public BigDecimal getMoneypay() {
        return moneypay;
    }

    public void setMoneypay(BigDecimal moneypay) {
        this.moneypay = moneypay;
    }

    public BigDecimal getMoneymemberpay() {
        return moneymemberpay;
    }

    public void setMoneymemberpay(BigDecimal moneymemberpay) {
        this.moneymemberpay = moneymemberpay;
    }

    public Integer getWxpaynum() {
        return wxpaynum;
    }

    public void setWxpaynum(Integer wxpaynum) {
        this.wxpaynum = wxpaynum;
    }

    public Integer getAlipaynum() {
        return alipaynum;
    }

    public void setAlipaynum(Integer alipaynum) {
        this.alipaynum = alipaynum;
    }

    public Integer getUnionpaynum() {
        return unionpaynum;
    }

    public void setUnionpaynum(Integer unionpaynum) {
        this.unionpaynum = unionpaynum;
    }

    public Integer getMoneypaynum() {
        return moneypaynum;
    }

    public void setMoneypaynum(Integer moneypaynum) {
        this.moneypaynum = moneypaynum;
    }

    public Integer getMomberpaynum() {
        return momberpaynum;
    }

    public void setMomberpaynum(Integer momberpaynum) {
        this.momberpaynum = momberpaynum;
    }

    public Long getFistordertime() {
        return fistordertime;
    }

    public void setFistordertime(Long fistordertime) {
        this.fistordertime = fistordertime;
    }

    public Long getLastordertime() {
        return lastordertime;
    }

    public void setLastordertime(Long lastordertime) {
        this.lastordertime = lastordertime;
    }
}
