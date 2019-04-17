package com.soft.ware.rest.modular.order.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 退款记录表
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@TableName("t_refund")
public class TRefund extends Model<TRefund> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;
    /**
     * 订单编号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 退款原因
     */
    private String reason;
    /**
     * 退款状态（0：处理中；1：成功；2：失败）
     */
    private Integer status;
    /**
     * 订单金额
     */
    @TableField("order_money")
    private String orderMoney;
    /**
     * 第三方返回结果
     */
    private String response;
    /**
     * 退款时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 退款人
     */
    private String creater;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String gevoidNo() {
        return orderNo;
    }

    public void sevoidNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String gevoidMoney() {
        return orderMoney;
    }

    public void sevoidMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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
        return "TRefund{" +
        "id=" + id +
        ", orderNo=" + orderNo +
        ", reason=" + reason +
        ", status=" + status +
        ", orderMoney=" + orderMoney +
        ", response=" + response +
        ", createTime=" + createTime +
        ", creater=" + creater +
        "}";
    }
}
