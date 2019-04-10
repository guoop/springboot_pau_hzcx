package com.soft.ware.rest.modular.handover.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 交接班记录表
 * </p>
 *
 * @author paulo123
 * @since 2019-04-10
 */
@TableName("t_handover_record")
public class THandoverRecord extends Model<THandoverRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;
    /**
     * 商户唯一标识
     */
    @TableField("owner_id")
    private String ownerId;
    /**
     * 商户员工唯一标识
     */
    @TableField("staff_id")
    private String staffId;
    @TableField("cash_register_id")
    private String cashRegisterId;
    /**
     * 同步时间
     */
    @TableField("sync_time")
    private Date syncTime;
    /**
     * 交接班开始时间
     */
    private Date startTime;

    /**
     * 交接班结束时间
     */
   private Date endTime;

    /**
     * 收银单数
     */
    @TableField("order_num")
    private Integer orderNum;
    /**
     * 退单数
     */
    @TableField("order_refund_num")
    private Integer orderRefundNum;
    /**
     * 反结账数
     */
    @TableField("order_return_num")
    private Integer orderReturnNum;
    /**
     * 订单金额
     */
    @TableField("order_money")
    private BigDecimal orderMoney;
    /**
     * 退单金额
     */
    @TableField("order_refund_money")
    private BigDecimal orderRefundMoney;
    /**
     * 反结账金额
     */
    @TableField("order_return_money")
    private BigDecimal orderReturnMoney;
    /**
     * 总金额
     */
    @TableField("all_money")
    private BigDecimal allMoney;
    /**
     * 找零
     */
    @TableField("odd_change_money")
    private BigDecimal oddChangeMoney;
    /**
     * 会员充值金额
     */
    @TableField("member_recharge")
    private BigDecimal memberRecharge;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人
     */
    private String creater;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getCashRegisterId() {
        return cashRegisterId;
    }

    public void setCashRegisterId(String cashRegisterId) {
        this.cashRegisterId = cashRegisterId;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderRefundNum() {
        return orderRefundNum;
    }

    public void setOrderRefundNum(Integer orderRefundNum) {
        this.orderRefundNum = orderRefundNum;
    }

    public Integer getOrderReturnNum() {
        return orderReturnNum;
    }

    public void setOrderReturnNum(Integer orderReturnNum) {
        this.orderReturnNum = orderReturnNum;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public BigDecimal getOrderRefundMoney() {
        return orderRefundMoney;
    }

    public void setOrderRefundMoney(BigDecimal orderRefundMoney) {
        this.orderRefundMoney = orderRefundMoney;
    }

    public BigDecimal getOrderReturnMoney() {
        return orderReturnMoney;
    }

    public void setOrderReturnMoney(BigDecimal orderReturnMoney) {
        this.orderReturnMoney = orderReturnMoney;
    }

    public BigDecimal getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(BigDecimal allMoney) {
        this.allMoney = allMoney;
    }

    public BigDecimal getOddChangeMoney() {
        return oddChangeMoney;
    }

    public void setOddChangeMoney(BigDecimal oddChangeMoney) {
        this.oddChangeMoney = oddChangeMoney;
    }

    public BigDecimal getMemberRecharge() {
        return memberRecharge;
    }

    public void setMemberRecharge(BigDecimal memberRecharge) {
        this.memberRecharge = memberRecharge;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "THandoverRecord{" +
        "id=" + id +
        ", ownerId=" + ownerId +
        ", staffId=" + staffId +
        ", cashRegisterId=" + cashRegisterId +
        ", syncTime=" + syncTime +
        ", orderNum=" + orderNum +
        ", orderRefundNum=" + orderRefundNum +
        ", orderReturnNum=" + orderReturnNum +
        ", orderMoney=" + orderMoney +
        ", orderRefundMoney=" + orderRefundMoney +
        ", orderReturnMoney=" + orderReturnMoney +
        ", allMoney=" + allMoney +
        ", oddChangeMoney=" + oddChangeMoney +
        ", memberRecharge=" + memberRecharge +
        ", createTime=" + createTime +
        ", creater=" + creater +
        "}";
    }
}
