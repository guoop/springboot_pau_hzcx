package com.soft.ware.rest.modular.order_app.model;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
  * 收银机订单信息
*/
@TableName("t_order_app")
public class TOrderApp extends Model<TOrderApp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //订单编号
    private String no;
    //订单来源（0：安卓收银机；1：Windows收银机）
    private Short source;
    //付款方式（1：现金支付；2：微信支付；3：支付宝支付；4；银联支付），多种支付方式以_分割
    @JSONField(name = "money_channel")
    private String moneyChannel;
    //订单金额，即：买家购买商品的总价（优惠前）
    private BigDecimal money;
    //买家支付金额（优惠后）
    @JSONField(name = "money_pay")
    private BigDecimal moneyPay;
    //实收金额（包含找零金额）
    @JSONField(name = "money_real_income")
    private BigDecimal moneyRealIncome;
    //找零金额
    @JSONField(name = "money_change")
    private BigDecimal moneyChange;
    //支付明细(支付方式_支付金钱,支付方式_支付金钱)
    @JSONField(name = "channel_pay")
    private String channelPay;
    //支付时间（即：订单在APP中的创建时间）
    @JSONField(name = "pay_time", format = "YYYY-MM-DD HH:mm:ss")
    private Date payTime;
    //订单同步到云端的时间
    @JSONField(name = "create_time", format = "YYYY-MM-DD HH:mm:ss")
    private Date createTime;
    //订单结算人（收银员标识）
    @JSONField(name = "settlementer")
    private String settlementer;
    //商户唯一id
    private String owner_id;
    //订单包含的商品信息
    private String goods_id;
    //订单状态（0：为正常；1：退单；2：反结账）
    private Integer status;
    //备注信息
    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Long getId() {
        return id;
    }

    public TOrderApp setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNo() {
        return no;
    }

    public TOrderApp setNo(String no) {
        this.no = no;
        return this;
    }

    public Short getSource() {
        return source;
    }

    public TOrderApp setSource(Short source) {
        this.source = source;
        return this;
    }

    public String getMoneyChannel() {
        return moneyChannel;
    }

    public TOrderApp setMoneyChannel(String moneyChannel) {
        this.moneyChannel = moneyChannel;
        return this;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public TOrderApp setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }

    public BigDecimal getMoneyPay() {
        return moneyPay;
    }

    public TOrderApp setMoneyPay(BigDecimal moneyPay) {
        this.moneyPay = moneyPay;
        return this;
    }

    public BigDecimal getMoneyRealIncome() {
        return moneyRealIncome;
    }

    public TOrderApp setMoneyRealIncome(BigDecimal moneyRealIncome) {
        this.moneyRealIncome = moneyRealIncome;
        return  this;
    }

    public BigDecimal getMoneyChange() {
        return moneyChange;
    }

    public TOrderApp setMoneyChange(BigDecimal moneyChange) {
        this.moneyChange = moneyChange;
        return this;
    }

    public String getChannelPay() {
        return channelPay;
    }

    public TOrderApp setChannelPay(String channelPay) {
        this.channelPay = channelPay;
        return this;
    }

    public Date getPayTime() {
        return payTime;
    }

    public TOrderApp setPayTime(Date payTime) {
        this.payTime = payTime;
        return this;
    }

    public Date getCreatedTime() {
        return createTime;
    }

    public TOrderApp setCreatedTime(Date createdTime) {
        this.createTime = createdTime;
        return this;
    }

    public String getSettlementer() {
        return settlementer;
    }

    public TOrderApp setSettlementer(String settlementer) {
        this.settlementer = settlementer;
        return this;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}