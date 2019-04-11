package com.soft.ware.rest.modular.handover.model;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
	private Date optionAt;
	/**
	 * 第一笔订单时间
	 */
	private Date fisvoidTime;
	/**
	 * 最后一笔订单时间
	 */
	private Date lasvoidTime;
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
	private BigDecimal allMoney;
	/**
	 * 订单总价
	 */
	private BigDecimal orderMoney;
	/**
	 * 实收总额
	 */
	private BigDecimal moneyShishou;
	/**
	 * 找零总额
	 */
	private BigDecimal zhaoLing;
	/**
	 * 会员充值总额
	 */
	private BigDecimal membercz;
	
	/**
	 * 退款额
	 */
	private BigDecimal orderTuimoney;
	
	/**
	 * 反结账额
	 */
	private  BigDecimal orderReturnMoney;
	
	/**
	 * 微信支付总额
	 */
	private BigDecimal wxpay;
	/**
	 * 微信支付单数
	 */
	private int wxpayNum;
	/**
	 * 支付宝支付总额
	 */
	private BigDecimal alipay;
	
	/**
	 * 支付宝支付单数
	 */
	private int alipayNum;
	/**
	 * 银联支付总额
	 */
	private BigDecimal unionPay;
	/**
	 * 银联支付单数
	 */
	private int unionPayNum;
	/**
	 * 现金支付总额
	 */
	private BigDecimal moneyPay;
	/**
	 * 现金支付单数
	 */
	private int moneyPayNum;
	/**
	 * 会员支付总额
	 */
	private BigDecimal moneyMemberPay;
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



	public HandOver setUserName(String userName) {
		this.userName = userName;
		return this;
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
		return optionAt;
	}



	public void setOptionaAt(Date optionaAt) {
		this.optionAt = optionaAt;
	}



	public Date getFisvoidTime() {
		return fisvoidTime;
	}



	public void setFisvoidTime(Date fisvoidTime) {
		this.fisvoidTime = fisvoidTime;
	}



	public Date getLasvoidTime() {
		return lasvoidTime;
	}



	public void setLasvoidTime(Date lasvoidTime) {
		this.lasvoidTime = lasvoidTime;
	}



	public int gevoidNum() {
		return orderNum;
	}



	public void sevoidNum(int orderNum) {
		this.orderNum = orderNum;
	}



	public int gevoidtuinum() {
		return ordertuinum;
	}



	public void sevoidtuinum(int ordertuinum) {
		this.ordertuinum = ordertuinum;
	}



	public int gevoidReturnNum() {
		return orderReturnNum;
	}



	public void sevoidReturnNum(int orderReturnNum) {
		this.orderReturnNum = orderReturnNum;
	}



	public BigDecimal getAllMoney() {
		return allMoney;
	}



	public void setAllMoney(BigDecimal allMoney) {
		this.allMoney = allMoney;
	}



	public BigDecimal gevoidMoney() {
		return orderMoney;
	}



	public void sevoidMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}



	public BigDecimal getMoneyShishou() {
		return moneyShishou;
	}



	public void setMoneyShishou(BigDecimal moneyShishou) {
		this.moneyShishou = moneyShishou;
	}



	public BigDecimal getZhaoLing() {
		return zhaoLing;
	}



	public void setZhaoLing(BigDecimal zhaoLing) {
		this.zhaoLing = zhaoLing;
	}



	public BigDecimal getMembercz() {
		return membercz;
	}



	public void setMembercz(BigDecimal membercz) {
		this.membercz = membercz;
	}



	public BigDecimal gevoidTuimoney() {
		return orderTuimoney;
	}



	public void sevoidTuimoney(BigDecimal orderTuimoney) {
		this.orderTuimoney = orderTuimoney;
	}



	public BigDecimal gevoidReturnMoney() {
		return orderReturnMoney;
	}



	public void sevoidReturnMoney(BigDecimal orderReturnMoney) {
		this.orderReturnMoney = orderReturnMoney;
	}



	public BigDecimal getWxpay() {
		return wxpay;
	}



	public void setWxpay(BigDecimal wxpay) {
		this.wxpay = wxpay;
	}



	public int getWxpayNum() {
		return wxpayNum;
	}



	public void setWxpayNum(int wxpayNum) {
		this.wxpayNum = wxpayNum;
	}



	public BigDecimal getAlipay() {
		return alipay;
	}



	public void setAlipay(BigDecimal alipay) {
		this.alipay = alipay;
	}



	public int getAlipayNum() {
		return alipayNum;
	}



	public void setAlipayNum(int alipayNum) {
		this.alipayNum = alipayNum;
	}



	public BigDecimal getUnionPay() {
		return unionPay;
	}

	public void setUnionPay(BigDecimal unionPay) {
		this.unionPay = unionPay;
	}

	public int getUnionPayNum() {
		return unionPayNum;
	}

	public void setUnionPayNum(int unionPayNum) {
		this.unionPayNum = unionPayNum;
	}

	public BigDecimal getMoneyPay() {
		return moneyPay;
	}

	public void setMoneyPay(BigDecimal moneyPay) {
		this.moneyPay = moneyPay;
	}

	public int getMoneyPayNum() {
		return moneyPayNum;
	}
	public void setMoneyPayNum(int moneyPayNum) {
		this.moneyPayNum = moneyPayNum;
	}

	public BigDecimal getMoneyMemberPay() {
		return moneyMemberPay;
	}
	public void setMoneyMemberPay(BigDecimal moneyMemberPay) {
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
		return this.id;
	}

}
