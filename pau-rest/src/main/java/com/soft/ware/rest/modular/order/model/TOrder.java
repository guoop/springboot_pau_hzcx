package com.soft.ware.rest.modular.order.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 线上订单信息
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@TableName("t_order")
public class TOrder extends Model<TOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 订单编号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 订单来源（0：微信小程序；1：收银机；2：到店自取订单）
     */
    private Integer source;
    /**
     * 取货码（只有到店自取的订单有该字段）
     */
    @TableField("pickup_no")
    private Long pickupNo;
    /**
     * 取货时间（只有到店自取的订单有该字段）
     */
    @TableField("pickup_time")
    private Date pickupTime;
    /**
     * 付款方式（0：在线支付；1：货到付款；2：现金；3：微信；4：支付宝；5：银联）
     */
    @TableField("money_channel")
    private Integer moneyChannel;
    /**
     * 订单金额，即：买家购买商品的总价（不包含运费）
     */
    @TableField("order_money")
    private BigDecimal orderMoney;
    /**
     * 运费
     */
    @TableField("run_money")
    private BigDecimal runMoney;
    /**
     * 买家支付金额（包含购买商品的总价和运费）
     */
    @TableField("pay_money")
    private BigDecimal payMoney;
    /**
     * 成功付款时间（如果是在线支付，则标识回调成功时间）
     */
    @TableField("pay_time")
    private Date payTime;
    /**
     * 支付成功后的回调原始结果
     */
    @TableField("pay_response")
    private String payResponse;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人
     */
    private String creater;
    /**
     * 订单结算人（只有收银系统产生的订单存在该字段，标识结算人）
     */
    private String settlementer;
    /**
     * 所属人
     */
    @TableField("owner_id")
    private String ownerId;
    /**
     * 收货人详细地址
     */
    @TableField("address_id")
    private String addressId;
    /**
     * 订单包含的商品信息（商品之间已英文逗号分隔，单个货物的的格式为：id__图片地址__名称__规格__数量__单价__总价）
     */
    @TableField("goods_id")
    private String goodsId;
    /**
     * 当前订单版本
     */
    private Integer version;
    /**
     * 订单状态（-3：已删除；-2：过期失效；-1：手动取消；0：待付款（新建订单）；1：待商家确认（在线支付支付成功、货到付款下单成功）；2：配送中；3：已完成（在线支付配送完成；货到付款付款功能）；10：商家确认接单（主要用于配送前及时提醒买家商家已接单））
     */
    private Integer status;
    /**
     * 备注信息
     */
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Long getPickupNo() {
        return pickupNo;
    }

    public void setPickupNo(Long pickupNo) {
        this.pickupNo = pickupNo;
    }

    public Date getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Integer getMoneyChannel() {
        return moneyChannel;
    }

    public void setMoneyChannel(Integer moneyChannel) {
        this.moneyChannel = moneyChannel;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public BigDecimal getRunMoney() {
        return runMoney;
    }

    public void setRunMoney(BigDecimal runMoney) {
        this.runMoney = runMoney;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayResponse() {
        return payResponse;
    }

    public void setPayResponse(String payResponse) {
        this.payResponse = payResponse;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getSettlementer() {
        return settlementer;
    }

    public void setSettlementer(String settlementer) {
        this.settlementer = settlementer;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TOrder{" +
        "id=" + id +
        ", orderNo=" + orderNo +
        ", source=" + source +
        ", pickupNo=" + pickupNo +
        ", pickupTime=" + pickupTime +
        ", moneyChannel=" + moneyChannel +
        ", orderMoney=" + orderMoney +
        ", runMoney=" + runMoney +
        ", payMoney=" + payMoney +
        ", payTime=" + payTime +
        ", payResponse=" + payResponse +
        ", createTime=" + createTime +
        ", creater=" + creater +
        ", settlementer=" + settlementer +
        ", ownerId=" + ownerId +
        ", addressId=" + addressId +
        ", goodsId=" + goodsId +
        ", version=" + version +
        ", status=" + status +
        ", remark=" + remark +
        "}";
    }
}
