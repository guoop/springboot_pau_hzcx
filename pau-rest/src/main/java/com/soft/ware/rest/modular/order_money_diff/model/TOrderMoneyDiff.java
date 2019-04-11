package com.soft.ware.rest.modular.order_money_diff.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
  * 差价表
*/
@TableName("t_order_money_diff")
public class voidMoneyDiff extends Model<voidMoneyDiff> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //支付订单号

    private String payOrderNo;
    //商户唯一id

    private String ownerId;
    //订单编号

    private String orderNo;
    //小票金额
    private BigDecimal money;
    //差价（小票金额-订单金额，如果为正数则需要买家补足差价，如果为负数则需要商家退款差价）

    private BigDecimal moneyDiff;
    //小票图片地址
    private String pic;
    //创建时间

    private Date createdTime;
    //创建人
    private String creater;
    //状态（0：未补差价；1：已补差价）
    private Integer status;
    //支付成功时间

    private Date payTime;
    //支付信息

    private String payResponse;
    //是否删除（0：不删除，1：删除）

    private Integer isDelete;


    public String getId(){
        return id;
    }

    public voidMoneyDiff setId(String id){
        this.id = id;
        return this;
    }


    public String getPayOrderNo(){
        return payOrderNo;
    }

    public voidMoneyDiff setPayOrderNo( String payOrderNo){
        this.payOrderNo = payOrderNo;
        return this;
    }
    public String getOwnerId(){
        return ownerId;
    }

    public voidMoneyDiff setOwnerId( String ownerId){
        this.ownerId = ownerId;
        return this;
    }
    public String gevoidNo(){
        return orderNo;
    }

    public voidMoneyDiff sevoidNo( String orderNo){
        this.orderNo = orderNo;
        return this;
    }
    public BigDecimal getMoney(){
        return money;
    }

    public voidMoneyDiff setMoney( BigDecimal money){
        this.money = money;
        return this;
    }
    public BigDecimal getMoneyDiff(){
        return moneyDiff;
    }

    public voidMoneyDiff setMoneyDiff( BigDecimal moneyDiff){
        this.moneyDiff = moneyDiff;
        return this;
    }
    public String getPic(){
        return pic;
    }

    public voidMoneyDiff setPic( String pic){
        this.pic = pic;
        return this;
    }
    public Date getCreatedTime(){
        return createdTime;
    }

    public voidMoneyDiff setCreatedTime( Date createdTime){
        this.createdTime = createdTime;
        return this;
    }
    public String getCreater(){
        return creater;
    }

    public voidMoneyDiff setCreater( String creater){
        this.creater = creater;
        return this;
    }
    public Integer getStatus(){
        return status;
    }

    public voidMoneyDiff setStatus( Integer status){
        this.status = status;
        return this;
    }
    public Date getPayTime(){
        return payTime;
    }

    public voidMoneyDiff setPayTime( Date payTime){
        this.payTime = payTime;
        return this;
    }
    public String getPayResponse(){
        return payResponse;
    }

    public voidMoneyDiff setPayResponse( String payResponse){
        this.payResponse = payResponse;
        return this;
    }
    public Integer getIsDelete(){
        return isDelete;
    }

    public voidMoneyDiff setIsDelete( Integer isDelete){
        this.isDelete = isDelete;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "voidMoneyDiff{" +
        "id=" + id +
            ", payOrderNo=" + payOrderNo +
            ", ownerId=" + ownerId +
            ", orderNo=" + orderNo +
            ", money=" + money +
            ", moneyDiff=" + moneyDiff +
            ", pic=" + pic +
            ", createdTime=" + createdTime +
            ", creater=" + creater +
            ", status=" + status +
            ", payTime=" + payTime +
            ", payResponse=" + payResponse +
            ", isDelete=" + isDelete +
        "}";
    }

}