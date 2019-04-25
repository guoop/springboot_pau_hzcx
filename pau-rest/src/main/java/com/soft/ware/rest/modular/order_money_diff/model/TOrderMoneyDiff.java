package com.soft.ware.rest.modular.order_money_diff.model;

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
 * 差价表
 * </p>
 *
 * @author yancc
 * @since 2019-04-13 11:46:45
 */
@TableName("t_order_money_diff")
public class TOrderMoneyDiff extends Model<TOrderMoneyDiff> {

    private static final long serialVersionUID = 1L;

    //：未补差价；
    public static Integer status_0 = 0;
    //：已补差价）
    public static Integer status_1 = 1;
    //：处理中；
    public static Integer refund_status_0 = 0;
    //：成功；
    public static Integer refund_status_1 = 1;
    //：失败）
    public static Integer refund_status_2 = 2;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 支付订单号(本表中生成的订单号需要用此订单号再次支付)
     */
    @TableField("pay_order_no")
    private String payOrderNo;
    /**
     * 商户唯一id
     */
    @TableField("owner_id")
    private String ownerId;
    /**
     * 订单编号(订单表中的订单号)
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 小票金额
     */
    private BigDecimal money;
    /**
     * 差价（小票金额-订单金额，如果为正数则需要买家补足差价，如果为负数则需要商家退款差价）
     */
    @TableField("money_diff")
    private BigDecimal moneyDiff;
    /**
     * 小票图片地址
     */
    private String pic;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人(买家姓名)
     */
    private String creater;
    /**
     * 状态（0：未补差价；1：已补差价）
     */
    private Integer status;
    /**
     * 支付成功时间
     */
    @TableField("pay_time")
    private Date payTime;
    /**
     * 支付信息
     */
    @TableField("pay_response")
    private String payResponse;
    /**
     * 是否删除（0：不删除，1：删除）
     */
    @TableField("is_delete")
    private Integer isDelete;
    /**
     * 退款人(买家openId)
     */
    @TableField("refunder")
    private String refunder;
    /**
     * 退款时间
     */
    @TableField("refund_time")
    private Date refundTime;
    /**
     * 退款状态(0: 处理中 1：成功 2: 失败)
     */
    @TableField("refund_status")
    private Integer refundStatus;
    /**
     * 退款响应
     */
    @TableField("refund_response")
    private String refundResponse;


    public String getId() {
        return id;
    }

    public TOrderMoneyDiff setId(String id){
        this.id = id;return this;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public TOrderMoneyDiff setPayOrderNo( String payOrderNo) {
        this.payOrderNo = payOrderNo;return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public TOrderMoneyDiff setOwnerId( String ownerId) {
        this.ownerId = ownerId;return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public TOrderMoneyDiff setOrderNo( String orderNo) {
        this.orderNo = orderNo;return this;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public TOrderMoneyDiff setMoney( BigDecimal money) {
        this.money = money;return this;
    }

    public BigDecimal getMoneyDiff() {
        return moneyDiff;
    }

    public TOrderMoneyDiff setMoneyDiff( BigDecimal moneyDiff) {
        this.moneyDiff = moneyDiff;return this;
    }

    public String getPic() {
        return pic;
    }

    public TOrderMoneyDiff setPic( String pic) {
        this.pic = pic;return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TOrderMoneyDiff setCreateTime( Date createTime) {
        this.createTime = createTime;return this;
    }

    public String getCreater() {
        return creater;
    }

    public TOrderMoneyDiff setCreater( String creater) {
        this.creater = creater;return this;
    }

    public Integer getStatus() {
        return status;
    }

    public TOrderMoneyDiff setStatus( Integer status) {
        this.status = status;return this;
    }

    public Date getPayTime() {
        return payTime;
    }

    public TOrderMoneyDiff setPayTime( Date payTime) {
        this.payTime = payTime;return this;
    }

    public String getPayResponse() {
        return payResponse;
    }

    public TOrderMoneyDiff setPayResponse( String payResponse) {
        this.payResponse = payResponse;return this;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public TOrderMoneyDiff setIsDelete( Integer isDelete) {
        this.isDelete = isDelete;return this;
    }

    public String getRefunder() {
        return refunder;
    }

    public TOrderMoneyDiff setRefunder( String refunder) {
        this.refunder = refunder;return this;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public TOrderMoneyDiff setRefundTime( Date refundTime) {
        this.refundTime = refundTime;return this;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public TOrderMoneyDiff setRefundStatus( Integer refundStatus) {
        this.refundStatus = refundStatus;return this;
    }

    public String getRefundResponse() {
        return refundResponse;
    }

    public TOrderMoneyDiff setRefundResponse( String refundResponse) {
        this.refundResponse = refundResponse;return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TOrderMoneyDiff{" +
        "id=" + id +
        ", payOrderNo=" + payOrderNo +
        ", ownerId=" + ownerId +
        ", orderNo=" + orderNo +
        ", money=" + money +
        ", moneyDiff=" + moneyDiff +
        ", pic=" + pic +
        ", createTime=" + createTime +
        ", creater=" + creater +
        ", status=" + status +
        ", payTime=" + payTime +
        ", payResponse=" + payResponse +
        ", isDelete=" + isDelete +
        ", refunder=" + refunder +
        ", refundTime=" + refundTime +
        ", refundStatus=" + refundStatus +
        ", refundResponse=" + refundResponse +
        "}";
    }
}