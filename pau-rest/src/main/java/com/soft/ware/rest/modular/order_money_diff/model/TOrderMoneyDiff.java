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
 * @since 2019-04-26 09:57:22
 */
@TableName("t_order_money_diff")
public class TOrderMoneyDiff extends Model<TOrderMoneyDiff> {

    private static final long serialVersionUID = 1L;


    //：未补差价；
    public static Integer status_0 = 0;
    //：已补差价）
    public static Integer status_1 = 1;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 支付订单号(本表中生成的订单号需要用此订单号再次支付)
     */
    private String no;
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
    @TableField("ticket_money")
    private BigDecimal ticketMoney;
    /**
     * 差价（小票金额-订单金额，如果为正数则需要买家补足差价，如果为负数则需要商家退款差价）
     */
    private BigDecimal money;
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
     * 创建人(卖家姓名)
     */
    private String creater;
    /**
     * 差价状态（0：未补差价；1：已补差价）
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
    private String response;
    /**
     * 是否删除（0：不删除，1：删除）
     */
    @TableField("is_delete")
    private Integer isDelete;
    /**
     * 退款原因
     */
    private String reason;


    public String getId() {
        return id;
    }

    public TOrderMoneyDiff setId(String id){
        this.id = id;return this;
    }

    public String getNo() {
        return no;
    }

    public TOrderMoneyDiff setNo( String no) {
        this.no = no;return this;
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

    public BigDecimal getTicketMoney() {
        return ticketMoney;
    }

    public TOrderMoneyDiff setTicketMoney( BigDecimal ticketMoney) {
        this.ticketMoney = ticketMoney;return this;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public TOrderMoneyDiff setMoney( BigDecimal money) {
        this.money = money;return this;
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

    public String getResponse() {
        return response;
    }

    public TOrderMoneyDiff setResponse( String response) {
        this.response = response;return this;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public TOrderMoneyDiff setIsDelete( Integer isDelete) {
        this.isDelete = isDelete;return this;
    }

    public String getReason() {
        return reason;
    }

    public TOrderMoneyDiff setReason( String reason) {
        this.reason = reason;return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TOrderMoneyDiff{" +
        "id=" + id +
        ", no=" + no +
        ", ownerId=" + ownerId +
        ", orderNo=" + orderNo +
        ", ticketMoney=" + ticketMoney +
        ", money=" + money +
        ", pic=" + pic +
        ", createTime=" + createTime +
        ", creater=" + creater +
        ", status=" + status +
        ", payTime=" + payTime +
        ", response=" + response +
        ", isDelete=" + isDelete +
        ", reason=" + reason +
        "}";
    }
}