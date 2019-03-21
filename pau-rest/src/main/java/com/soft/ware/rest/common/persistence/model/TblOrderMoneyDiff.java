package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
  * 订单差价
*/
@TableName("tbl_order_money_diff")
public class TblOrderMoneyDiff extends Model<TblOrderMoneyDiff> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //支付订单号
    private String no;
    //订单属主
    private String owner;
    //订单编号（关联到表tbl_order的no字段）
    private String orderNo;
    //小票金额
    private BigDecimal money;
    //差价（小票金额-订单金额，如果为正数则需要买家补足差价，如果为负数则需要商家退款差价）
    private BigDecimal moneyDiff;
    //小票图片地址
    private String pic;
    //添加时间
    private Date createdAt;
    //状态（0：未补差价；1：已补差价）
    private Boolean status;
    //支付成功时间
    private Date payAt;
    //支付信息
    private String payResponse;
    //退款人
    private String refundBy;
    //退款时间
    private Date refundAt;
    //退款状态（0：处理中；1：成功；2：失败）
    private Boolean refundStatus;
    //退款信息
    private String refundResponse;

    public String getNo(){
        return no;
    }

    public void setNo( String no){
        this.no = no;
    }
    public String getOwner(){
        return owner;
    }

    public void setOwner( String owner){
        this.owner = owner;
    }
    public String getOrderNo(){
        return orderNo;
    }

    public void setOrderNo( String orderNo){
        this.orderNo = orderNo;
    }
    public BigDecimal getMoney(){
        return money;
    }

    public void setMoney( BigDecimal money){
        this.money = money;
    }
    public BigDecimal getMoneyDiff(){
        return moneyDiff;
    }

    public void setMoneyDiff( BigDecimal moneyDiff){
        this.moneyDiff = moneyDiff;
    }
    public String getPic(){
        return pic;
    }

    public void setPic( String pic){
        this.pic = pic;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
    }
    public Boolean getStatus(){
        return status;
    }

    public void setStatus( Boolean status){
        this.status = status;
    }
    public Date getPayAt(){
        return payAt;
    }

    public void setPayAt( Date payAt){
        this.payAt = payAt;
    }
    public String getPayResponse(){
        return payResponse;
    }

    public void setPayResponse( String payResponse){
        this.payResponse = payResponse;
    }
    public String getRefundBy(){
        return refundBy;
    }

    public void setRefundBy( String refundBy){
        this.refundBy = refundBy;
    }
    public Date getRefundAt(){
        return refundAt;
    }

    public void setRefundAt( Date refundAt){
        this.refundAt = refundAt;
    }
    public Boolean getRefundStatus(){
        return refundStatus;
    }

    public void setRefundStatus( Boolean refundStatus){
        this.refundStatus = refundStatus;
    }
    public String getRefundResponse(){
        return refundResponse;
    }

    public void setRefundResponse( String refundResponse){
        this.refundResponse = refundResponse;
    }


    public  Long getId(){
        return id;
    }

    public  void setId(Long id){
        this.id = id;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TblOrderMoneyDiff{" +
        "id=" + id +
            ", no=" + no +
            ", owner=" + owner +
            ", orderNo=" + orderNo +
            ", money=" + money +
            ", moneyDiff=" + moneyDiff +
            ", pic=" + pic +
            ", createdAt=" + createdAt +
            ", status=" + status +
            ", payAt=" + payAt +
            ", payResponse=" + payResponse +
            ", refundBy=" + refundBy +
            ", refundAt=" + refundAt +
            ", refundStatus=" + refundStatus +
            ", refundResponse=" + refundResponse +
        "}";
    }

}