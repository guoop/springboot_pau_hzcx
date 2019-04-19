package com.soft.ware.rest.modular.order_app.model;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * <p>
 * 收银机订单信息
 * </p>
 *
 * @author yancc
 * @since 2019-04-19 13:48:01
 */
@TableName("t_order_app")
public class TOrderApp extends Model<TOrderApp> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
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
    private String moneyChannel;
    /**
     * 订单金额，即：买家购买商品的总价（优惠前）
     */
    private BigDecimal money;
    /**
     * 买家支付金额（优惠后）
     */
    private BigDecimal moneyPay;
    /**
     * 实收金额（包含找零金额）
     */
    private BigDecimal moneyRealIncome;
    /**
     * 找零金额
     */
    private BigDecimal moneyChange;
    /**
     * 支付明细(支付方式_支付金钱,支付方式_支付金钱)
     */
    private String channelPay;
    /**
     * 支付时间（即：订单在APP中的创建时间）
     */
    private Date payTime;
    /**
     * 订单同步到云端的时间
     */
    private Date createTime;
    /**
     * 订单结算人（收银员标识）
     */
    private String settlementer;
    /**
     * 所属人
     */
    private String ownerId;
    /**
     * 订单包含的商品信息（商品之间已英文逗号分隔，单个货物的的格式为：id__图片地址__名称__规格__数量__单价__总价）
     */
    private String goodsId;
    /**
     * 订单状态（0：为正常；1：退单；2：反结账）
     */
    private Integer status;
    /**
     * 备注信息
     */
    private String remark;


    public Long getId() {
        return id;
    }

    public TOrderApp setId(Long id){
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

    public TOrderApp setSource(Integer source) {
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

    public String getGoodsId() {
        return goodsId;
    }

    public TOrderApp setGoodsId( String goodsId) {
        this.goodsId = goodsId;return this;
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
        ", goodsId=" + goodsId +
        ", status=" + status +
        ", remark=" + remark +
        "}";
    }
}