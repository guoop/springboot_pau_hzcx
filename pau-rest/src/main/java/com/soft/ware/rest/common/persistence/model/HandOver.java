package com.soft.ware.rest.common.persistence.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;

public class HandOver extends Model<HandOver>{
	
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 收银员所属店铺标识
	 */
	private String owner;
	/**
	 * 收银员标识
	 */
	private String userPhone;
	/**
	 * 收银员姓名
	 */
	private String userName;
	/**
	 * 收银机编号
	 */
	private String pospalcode;
	/**
	 * 同步时间
	 */
	private Date syncAt;
	/**
	 * 账号登录时间
	 */
	private Date optionStart;
	/**
	 * 交接班结束时间
	 */
	private Date optionaAt;
	/**
	 * 第一笔订单时间
	 */
	private Date fistOrderTime;
	/**
	 * 最后一笔订单时间
	 */
	private Date lastOrderTime;
	/**
	 * 订单数量
	 */
	private int orderNum;
	/**
	 * 退单数
	 */
	private int ordertuinum;
	
	/**
	 * 反结账数
	 */
	private int orderReturnNum;
	
	/**
	 * 商品总价
	 */
	private Double allMoney;
	/**
	 * 订单总价
	 */
	private Double orderMoney;
	/**
	 * 实收总额
	 */
	private Double moneyShishou;
	/**
	 * 找零总额
	 */
	private Double zhaoLing;
	/**
	 * 会员充值总额
	 */
	private Double membercz;
	
	/**
	 * 退款额
	 */
	private Double orderTuimoney;
	
	/**
	 * 反结账额
	 */
	private  Double orderReturnMoney;
	
	/**
	 * 微信支付总额
	 */
	private Double wxpay;
	/**
	 * 微信支付单数
	 */
	private int wxpayNum;
	/**
	 * 支付宝支付总额
	 */
	private Double alipay;
	
	/**
	 * 支付宝支付单数
	 */
	private int alipayNum;
	/**
	 * 银联支付总额
	 */
	private Double unionPay;
	/**
	 * 银联支付单数
	 */
	private int unionPayNum;
	/**
	 * 现金支付总额
	 */
	private Double moneyPay;
	/**
	 * 现金支付单数
	 */
	private int moneyPayNum;
	/**
	 * 会员支付总额
	 */
	private Double moneyMemberPay;
	/**
	 * 会员支付单数
	 */
	private int memberPayNum;
	
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getOwner() {
		return owner;
	}



	public void setOwner(String owner) {
		this.owner = owner;
	}



	public String getUserPhone() {
		return userPhone;
	}



	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPospalcode() {
		return pospalcode;
	}



	public void setPospalcode(String pospalcode) {
		this.pospalcode = pospalcode;
	}



	public Date getSyncAt() {
		return syncAt;
	}



	public void setSyncAt(Date syncAt) {
		this.syncAt = syncAt;
	}



	public Date getOptionStart() {
		return optionStart;
	}



	public void setOptionStart(Date optionStart) {
		this.optionStart = optionStart;
	}



	public Date getOptionaAt() {
		return optionaAt;
	}



	public void setOptionaAt(Date optionaAt) {
		this.optionaAt = optionaAt;
	}



	public Date getFistOrderTime() {
		return fistOrderTime;
	}



	public void setFistOrderTime(Date fistOrderTime) {
		this.fistOrderTime = fistOrderTime;
	}



	public Date getLastOrderTime() {
		return lastOrderTime;
	}



	public void setLastOrderTime(Date lastOrderTime) {
		this.lastOrderTime = lastOrderTime;
	}



	public int getOrderNum() {
		return orderNum;
	}



	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}



	public int getOrdertuinum() {
		return ordertuinum;
	}



	public void setOrdertuinum(int ordertuinum) {
		this.ordertuinum = ordertuinum;
	}



	public int getOrderReturnNum() {
		return orderReturnNum;
	}



	public void setOrderReturnNum(int orderReturnNum) {
		this.orderReturnNum = orderReturnNum;
	}



	public Double getAllMoney() {
		return allMoney;
	}



	public void setAllMoney(Double allMoney) {
		this.allMoney = allMoney;
	}



	public Double getOrderMoney() {
		return orderMoney;
	}



	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}



	public Double getMoneyShishou() {
		return moneyShishou;
	}



	public void setMoneyShishou(Double moneyShishou) {
		this.moneyShishou = moneyShishou;
	}



	public Double getZhaoLing() {
		return zhaoLing;
	}



	public void setZhaoLing(Double zhaoLing) {
		this.zhaoLing = zhaoLing;
	}



	public Double getMembercz() {
		return membercz;
	}



	public void setMembercz(Double membercz) {
		this.membercz = membercz;
	}



	public Double getOrderTuimoney() {
		return orderTuimoney;
	}



	public void setOrderTuimoney(Double orderTuimoney) {
		this.orderTuimoney = orderTuimoney;
	}



	public Double getOrderReturnMoney() {
		return orderReturnMoney;
	}



	public void setOrderReturnMoney(Double orderReturnMoney) {
		this.orderReturnMoney = orderReturnMoney;
	}



	public Double getWxpay() {
		return wxpay;
	}



	public void setWxpay(Double wxpay) {
		this.wxpay = wxpay;
	}



	public int getWxpayNum() {
		return wxpayNum;
	}



	public void setWxpayNum(int wxpayNum) {
		this.wxpayNum = wxpayNum;
	}



	public Double getAlipay() {
		return alipay;
	}



	public void setAlipay(Double alipay) {
		this.alipay = alipay;
	}



	public int getAlipayNum() {
		return alipayNum;
	}



	public void setAlipayNum(int alipayNum) {
		this.alipayNum = alipayNum;
	}



	public Double getUnionPay() {
		return unionPay;
	}

	public void setUnionPay(Double unionPay) {
		this.unionPay = unionPay;
	}

	public int getUnionPayNum() {
		return unionPayNum;
	}

	public void setUnionPayNum(int unionPayNum) {
		this.unionPayNum = unionPayNum;
	}

	public Double getMoneyPay() {
		return moneyPay;
	}

	public void setMoneyPay(Double moneyPay) {
		this.moneyPay = moneyPay;
	}

	public int getMoneyPayNum() {
		return moneyPayNum;
	}
	public void setMoneyPayNum(int moneyPayNum) {
		this.moneyPayNum = moneyPayNum;
	}

	public Double getMoneyMemberPay() {
		return moneyMemberPay;
	}
	public void setMoneyMemberPay(Double moneyMemberPay) {
		this.moneyMemberPay = moneyMemberPay;
	}

	public int getMemberPayNum() {
		return memberPayNum;
	}

	public void setMemberPayNum(int memberPayNum) {
		this.memberPayNum = memberPayNum;
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}

}
