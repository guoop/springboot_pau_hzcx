package com.soft.ware.rest.modular.order_app.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收银机订单信息
 * </p>
 *
 * @author yancc
 * @since 2019-04-27 10:24:11
 */
@TableName("t_order_app")
public class TOrderApp extends Model<TOrderApp> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 订单编号
     */
    private String no;
    /**
     * 订单来源（0：安卓收银机；1：Windows收银机）
     */
    private Integer source;
    /**
     * 付款方式（1：现金支付；2：微信支付；3：支付宝支付；4；银联支付），多种支付方式以_分割
     */
    @TableField("money_channel")
    private String moneyChannel;
    /**
     * 订单金额，即：买家购买商品的总价（优惠前）
     */
    private BigDecimal money;
    /**
     * 买家支付金额（优惠后）
     */
    @TableField("money_pay")
    private BigDecimal moneyPay;
    /**
     * 实收金额（包含找零金额）
     */
    @TableField("money_real_income")
    private BigDecimal moneyRealIncome;
    /**
     * 找零金额
     */
    @TableField("money_change")
    private BigDecimal moneyChange;
    /**
     * 支付明细(支付方式_支付金钱,支付方式_支付金钱)
     */
    @TableField("channel_pay")
    private String channelPay;
    /**
     * 支付时间（即：订单在APP中的创建时间）
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @TableField("pay_time")
    private Date payTime;
    /**
     * 订单同步到云端的时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;
    /**
     * 订单结算人（收银员标识）
     */
    private String settlementer;
    /**
     * 所属人
     */
    @TableField("owner_id")
    private String ownerId;
    /**
     * 订单状态（0：为正常；1：退单；2：反结账）
     */
    private Integer status;
    /**
     * 备注信息
     */
    private String remark;

    @TableField(exist = false)
    private List<Map<String,Object>> listGoods = new ArrayList<>();


    public String getId() {
        return id;
    }

    public TOrderApp setId(String id){
        this.id = id;return this;
    }

    public String getNo() {
        return no;
    }

    public TOrderApp setNo( String no) {
        this.no = no;return this;
    }

    public Integer getSource() {
        return source;
    }

    public TOrderApp setSource( Integer source) {
        this.source = source;return this;
    }

    public String getMoneyChannel() {
        return moneyChannel;
    }

    public TOrderApp setMoneyChannel( String moneyChannel) {
        this.moneyChannel = moneyChannel;return this;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public TOrderApp setMoney( BigDecimal money) {
        this.money = money;return this;
    }

    public BigDecimal getMoneyPay() {
        return moneyPay;
    }

    public TOrderApp setMoneyPay( BigDecimal moneyPay) {
        this.moneyPay = moneyPay;return this;
    }

    public BigDecimal getMoneyRealIncome() {
        return moneyRealIncome;
    }

    public TOrderApp setMoneyRealIncome( BigDecimal moneyRealIncome) {
        this.moneyRealIncome = moneyRealIncome;return this;
    }

    public BigDecimal getMoneyChange() {
        return moneyChange;
    }

    public TOrderApp setMoneyChange( BigDecimal moneyChange) {
        this.moneyChange = moneyChange;return this;
    }

    public String getChannelPay() {
        return channelPay;
    }

    public TOrderApp setChannelPay( String channelPay) {
        this.channelPay = channelPay;return this;
    }

    public Date getPayTime() {
        return payTime;
    }

    public TOrderApp setPayTime( Date payTime) {
        this.payTime = payTime;return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TOrderApp setCreateTime( Date createTime) {
        this.createTime = createTime;return this;
    }

    public String getSettlementer() {
        return settlementer;
    }

    public TOrderApp setSettlementer( String settlementer) {
        this.settlementer = settlementer;return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public TOrderApp setOwnerId( String ownerId) {
        this.ownerId = ownerId;return this;
    }

    public Integer getStatus() {
        return status;
    }

    public TOrderApp setStatus( Integer status) {
        this.status = status;return this;
    }

    public String getRemark() {
        return remark;
    }

    public TOrderApp setRemark( String remark) {
        this.remark = remark;return this;
    }

    public List<Map<String, Object>> getListGoods() {
        return listGoods;
    }

    public void setListGoods(List<Map<String, Object>> listGoods) {
        this.listGoods = listGoods;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TOrderApp{" +
        "id=" + id +
        ", no=" + no +
        ", source=" + source +
        ", moneyChannel=" + moneyChannel +
        ", money=" + money +
        ", moneyPay=" + moneyPay +
        ", moneyRealIncome=" + moneyRealIncome +
        ", moneyChange=" + moneyChange +
        ", channelPay=" + channelPay +
        ", payTime=" + payTime +
        ", createTime=" + createTime +
        ", settlementer=" + settlementer +
        ", ownerId=" + ownerId +
        ", status=" + status +
        ", remark=" + remark +
        "}";
    }
}