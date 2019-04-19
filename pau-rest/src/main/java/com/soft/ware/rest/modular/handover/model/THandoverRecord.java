package com.soft.ware.rest.modular.handover.model;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * <p>
 * 交接班记录表
 * </p>
 *
 * @author yancc
 * @since 2019-04-19 09:42:15
 */
@TableName("t_handover_record")
public class THandoverRecord extends Model<THandoverRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 商户唯一标识
     */
    private String ownerId;
    /**
     * 商户员工唯一标识
     */
    private String staffId;
    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 同步时间
     */
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
    private Integer orderNum;
    /**
     * 退单数
     */
    private Integer orderRefundNum;
    /**
     * 反结账数
     */
    private Integer orderReturnNum;
    /**
     * 订单金额
     */
    private BigDecimal orderMoney;
    /**
     * 退单金额
     */
    private BigDecimal orderRefundMoney;
    /**
     * 反结账金额
     */
    private BigDecimal orderReturnMoney;
    /**
     * 总金额
     */
    private BigDecimal allMoney;
    /**
     * 找零
     */
    private BigDecimal oddChangeMoney;
    /**
     * 会员充值金额
     */
    private BigDecimal memberRecharge;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String creater;


    public String getId() {
        return id;
    }

    public THandoverRecord setId(String id){
        this.id = id;return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public THandoverRecord setOwnerId( String ownerId) {
        this.ownerId = ownerId;return this;
    }

    public String getStaffId() {
        return staffId;
    }

    public THandoverRecord setStaffId( String staffId) {
        this.staffId = staffId;return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public THandoverRecord setSyncTime( Date syncTime) {
        this.syncTime = syncTime;return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public THandoverRecord setStartTime( Date startTime) {
        this.startTime = startTime;return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public THandoverRecord setEndTime( Date endTime) {
        this.endTime = endTime;return this;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public THandoverRecord setOrderNum( Integer orderNum) {
        this.orderNum = orderNum;return this;
    }

    public Integer getOrderRefundNum() {
        return orderRefundNum;
    }

    public THandoverRecord setOrderRefundNum( Integer orderRefundNum) {
        this.orderRefundNum = orderRefundNum;return this;
    }

    public Integer getOrderReturnNum() {
        return orderReturnNum;
    }

    public THandoverRecord setOrderReturnNum( Integer orderReturnNum) {
        this.orderReturnNum = orderReturnNum;return this;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public THandoverRecord setOrderMoney( BigDecimal orderMoney) {
        this.orderMoney = orderMoney;return this;
    }

    public BigDecimal getOrderRefundMoney() {
        return orderRefundMoney;
    }

    public THandoverRecord setOrderRefundMoney( BigDecimal orderRefundMoney) {
        this.orderRefundMoney = orderRefundMoney;return this;
    }

    public BigDecimal getOrderReturnMoney() {
        return orderReturnMoney;
    }

    public THandoverRecord setOrderReturnMoney( BigDecimal orderReturnMoney) {
        this.orderReturnMoney = orderReturnMoney;return this;
    }

    public BigDecimal getAllMoney() {
        return allMoney;
    }

    public THandoverRecord setAllMoney( BigDecimal allMoney) {
        this.allMoney = allMoney;return this;
    }

    public BigDecimal getOddChangeMoney() {
        return oddChangeMoney;
    }

    public THandoverRecord setOddChangeMoney( BigDecimal oddChangeMoney) {
        this.oddChangeMoney = oddChangeMoney;return this;
    }

    public BigDecimal getMemberRecharge() {
        return memberRecharge;
    }

    public THandoverRecord setMemberRecharge( BigDecimal memberRecharge) {
        this.memberRecharge = memberRecharge;return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public THandoverRecord setCreateTime( Date createTime) {
        this.createTime = createTime;return this;
    }

    public String getCreater() {
        return creater;
    }

    public THandoverRecord setCreater( String creater) {
        this.creater = creater;return this;
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
                ", deviceId=" + deviceId +
                ", syncTime=" + syncTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
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