package com.soft.ware.rest.modular.order.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 线上订单信息
 * </p>
 *
 * @author paulo123
 * @since 2019-04-12
 */
@TableName("t_order")
public class TOrder extends Model<TOrder> {

    private static final long serialVersionUID = 1L;


    //：微信小程序；
    public static Integer SOURCE_0 = 0;
    //：收银机；
    public static Integer SOURCE_1 = 1;
    //：到店自取订单）
    public static Integer SOURCE_2 = 2;

    //：未删除；
    public static Integer is_delete_0 = 0;
    //：已删除）
    public static Integer is_delete_1 = 1;

    //：在线支付；
    public static Integer MONEY_CHANNEL_0 = 0;
    //：货到付款；
    public static Integer MONEY_CHANNEL_1 = 1;
    //：现金；
    public static Integer MONEY_CHANNEL_2 = 2;
    //：微信；
    public static Integer MONEY_CHANNEL_3 = 3;
    //：支付宝；
    public static Integer MONEY_CHANNEL_4 = 4;
    //：银联）
    public static Integer MONEY_CHANNEL_5 = 5;
    //聚合支付
    public static Integer MONEY_CHANNEL_6 = 6;
    //：配送中；
    public static Integer STATUS_2 = 2;
    //：待商家确认（在线支付支付成功、货到付款下单成功）；
    public static Integer STATUS_1 = 1;
    //：待付款（新建订单）；
    public static Integer STATUS_0 = 0;
    //：已完成（在线支付配送完成；货到付款付款功能）；
    public static Integer STATUS_3 = 3;
    //：商家确认接单（主要用于配送前及时提醒买家商家已接单））
    public static Integer STATUS_10 = 10;
    //手动取消
    public static Integer STATUS_01 = -1;
    //过期失效
    public static Integer STATUS_02 = -2;
    //已删除
    public static Integer STATUS_03 = -3;
    //-
    public static String CANCEL_BY_01 = "-1";
    //：处理中；
    public static Integer REFUND_STATUS_0 = 0;
    //：成功；
    public static Integer REFUND_STATUS_1 = 1;
    //：失败）
    public static Integer REFUND_STATUS_2 = 2;



    /**
     * 自增主键
     */
    @TableId(value = "id")
    private String id;
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
    private Integer pickupNo;
    /**
     * 取货时间（只有到店自取的订单有该字段）
     */
    @TableField("pickup_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+24")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+24")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+24")
    private Date createTime;
    /**
     * 创建人的openId
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
     * 订单类型只要出货就是一笔订单
     */
    private Integer type;
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
    /**
     * 取消人
     */
    private String canceler;
    /**
     * 取消时间
     */
    @TableField("cancel_time")
    private Date cancelTime;

    /**
     * 到店自提预留电话
     */
    private String phone;

    @TableField("is_delete")
    private Integer isDelete;
    /**
     * 订单取消原因
     */
    @TableField("cancel_reason")
    private String cancelReason;

    /**
     * 确认人
     */
    @TableField("confirmer")
    private String confirmer;
    /**
     * 确认时间
     */
    @TableField("confirm_time")
    private Date confirmTime;

    /**
     * 订单完成人
     */
    @TableField("doner")
    private String doner;
    /**
     * 订单完成时间
     */
    @TableField("done_time")
    private Date doneTime;
    /**
     * s配送人
     */
    @TableField("distributioner")
    private String distributioner;
    /**
     * 配送时间
     */
    @TableField("distribution_time")
    private Date distributionTime;

    public String getDistributioner() {
        return distributioner;
    }

    public void setDistributioner(String distributioner) {
        this.distributioner = distributioner;
    }

    public Date getDistributionTime() {
        return distributionTime;
    }

    public void setDistributionTime(Date distributionTime) {
        this.distributionTime = distributionTime;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getDoner() {
        return doner;
    }

    public void setDoner(String doner) {
        this.doner = doner;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    /**
     * 子订单
     */
    @TableField(exist = false)
    private List<TOrderChild> orderChildList = new ArrayList<>();

    public List<TOrderChild> getOrderChildList() {
        return orderChildList;
    }

    public void setOrderChildList(List<TOrderChild> orderChildList) {
        this.orderChildList = orderChildList;
    }

    public String getId() {
        return id;
    }

    public TOrder setId(String id){
        this.id = id;return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public TOrder setOrderNo( String orderNo) {
        this.orderNo = orderNo;return this;
    }

    public Integer getSource() {
        return source;
    }

    public TOrder setSource( Integer source) {
        this.source = source;return this;
    }

    public Integer getPickupNo() {
        return pickupNo;
    }

    public TOrder setPickupNo( Integer pickupNo) {
        this.pickupNo = pickupNo;return this;
    }

    public Date getPickupTime() {
        return pickupTime;
    }

    public TOrder setPickupTime( Date pickupTime) {
        this.pickupTime = pickupTime;return this;
    }

    public Integer getMoneyChannel() {
        return moneyChannel;
    }

    public TOrder setMoneyChannel( Integer moneyChannel) {
        this.moneyChannel = moneyChannel;return this;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public TOrder setOrderMoney( BigDecimal orderMoney) {
        this.orderMoney = orderMoney;return this;
    }

    public BigDecimal getRunMoney() {
        return runMoney;
    }

    public TOrder setRunMoney( BigDecimal runMoney) {
        this.runMoney = runMoney;return this;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public TOrder setPayMoney( BigDecimal payMoney) {
        this.payMoney = payMoney;return this;
    }

    public Date getPayTime() {
        return payTime;
    }

    public TOrder setPayTime( Date payTime) {
        this.payTime = payTime;return this;
    }

    public String getPayResponse() {
        return payResponse;
    }

    public TOrder setPayResponse( String payResponse) {
        this.payResponse = payResponse;return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TOrder setCreateTime( Date createTime) {
        this.createTime = createTime;return this;
    }

    public String getCreater() {
        return creater;
    }

    public TOrder setCreater( String creater) {
        this.creater = creater;return this;
    }

    public String getSettlementer() {
        return settlementer;
    }

    public TOrder setSettlementer( String settlementer) {
        this.settlementer = settlementer;return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public TOrder setOwnerId( String ownerId) {
        this.ownerId = ownerId;return this;
    }

    public String getAddressId() {
        return addressId;
    }

    public TOrder setAddressId( String addressId) {
        this.addressId = addressId;return this;
    }

    public Integer getType() {
        return type;
    }

    public TOrder setType( Integer type) {
        this.type = type;return this;
    }

    public Integer getVersion() {
        return version;
    }

    public TOrder setVersion( Integer version) {
        this.version = version;return this;
    }

    public Integer getStatus() {
        return status;
    }

    public TOrder setStatus( Integer status) {
        this.status = status;return this;
    }

    public String getRemark() {
        return remark;
    }

    public TOrder setRemark( String remark) {
        this.remark = remark;return this;
    }

    public String getCanceler() {
        return canceler;
    }

    public TOrder setCanceler( String canceler) {
        this.canceler = canceler;return this;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public TOrder setCancelTime( Date cancelTime) {
        this.cancelTime = cancelTime;return this;
    }

    public String getPhone() {
        return phone;
    }

    public TOrder setPhone(String phone) {
        this.phone = phone;return this;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public TOrder setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;return this;
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
        ", type=" + type +
        ", version=" + version +
        ", status=" + status +
        ", remark=" + remark +
        "}";
    }
}
