package com.soft.ware.rest.modular.order.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonFormat(timezone = "GMT+24",pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(timezone = "GMT+24",pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(timezone = "GMT+24",pattern = "yyyy-MM-dd HH:mm:ss")
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
     * 订单类型，只要出货就是一笔订单（1:线上订单，2：线下订单，3商品损耗，4：调货，5：商品丢失，6：商品过期，7：其他）
     */
    private Integer type;

    public Integer getType() {
        return type;
    }

    public TOrder setType(Integer type) {
        this.type = type;
        return this;
    }

    /**
     * 备注信息
     */
    private String remark;


    public Long getId() {
        return id;
    }

    public TOrder setId(Long id) {
        this.id = id;
        return  this;
    }

    public String gevoidNo() {
        return orderNo;
    }

    public TOrder sevoidNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public Integer getSource() {
        return source;
    }

    public TOrder setSource(Integer source) {
        this.source = source;
        return this;
    }

    public Long getPickupNo() {
        return pickupNo;
    }

    public TOrder setPickupNo(Long pickupNo) {
        this.pickupNo = pickupNo;
        return this;
    }

    public Date getPickupTime() {
        return pickupTime;
    }

    public TOrder setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
        return this;
    }

    public Integer getMoneyChannel() {
        return moneyChannel;
    }

    public TOrder setMoneyChannel(Integer moneyChannel) {
        this.moneyChannel = moneyChannel;
        return this;
    }

    public BigDecimal gevoidMoney() {
        return orderMoney;
    }

    public TOrder sevoidMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
        return this;
    }

    public BigDecimal getRunMoney() {
        return runMoney;
    }

    public TOrder setRunMoney(BigDecimal runMoney) {
        this.runMoney = runMoney;
        return this;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public TOrder setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
        return this;
    }

    public Date getPayTime() {
        return payTime;
    }

    public TOrder setPayTime(Date payTime) {
        this.payTime = payTime;
        return this;
    }

    public String getPayResponse() {
        return payResponse;
    }

    public TOrder setPayResponse(String payResponse) {
        this.payResponse = payResponse;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TOrder setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCreater() {
        return creater;
    }

    public TOrder setCreater(String creater) {
        this.creater = creater;
        return this;
    }

    public String getSettlementer() {
        return settlementer;
    }

    public TOrder setSettlementer(String settlementer) {
        this.settlementer = settlementer;
        return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public TOrder setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getAddressId() {
        return addressId;
    }

    public TOrder setAddressId(String addressId) {
        this.addressId = addressId;
        return this;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public TOrder setGoodsId(String goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public TOrder setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public TOrder setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public TOrder setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "void{" +
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
