package com.soft.ware.rest.modular.order.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;
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

    //：处理中；
    public static Integer status_0 = 0;
    //：成功；
    public static Integer status_1 = 1;
    //：失败）
    public static Integer status_2 = 2;

    /**
     * 主键id
     */
    @Id
    @TableId(value = "id" ,type = IdType.INPUT)
    private String id;

    /**
     * 退款单号
     */
    private String no;

    /**
     * 订单编号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 退款原因
     */
    @TableField("reason")
    private String reason;
    /**
     * 退款状态（0：处理中；1：成功；2：失败）
     */
    private Integer status;
    /**
     * 订单金额
     */
    @TableField("order_money")
    private BigDecimal orderMoney;
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

    public String getNo() {
        return no;
    }

    public TRefund setNo(String no) {
        this.no = no;
        return this;
    }


    public String getOrderNo() {
        return orderNo;
    }

    public TRefund setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
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

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
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
