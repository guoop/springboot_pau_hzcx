package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
  * 收银机订单信息
*/
@TableName("tbl_order_app")
public class TblOrderApp extends Model<TblOrderApp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //订单编号
    private String no;
    //订单来源（0：安卓收银机；1：Windows收银机）
    private Boolean source;
    //付款方式（1：现金支付；2：微信支付；3：支付宝支付；4；银联支付），多种支付方式以_分割
    private String moneyChannel;
    //订单金额，即：买家购买商品的总价（优惠前）
    private BigDecimal money;
    //买家支付金额（优惠后）
    private BigDecimal moneyDpay;
    //实收金额（包含找零金额）
    private BigDecimal moneyShishou;
    //找零金额
    private BigDecimal moneyZhaol;
    //支付明细(支付方式_支付金钱,支付方式_支付金钱)
    private String channelPay;
    //支付时间（即：订单在APP中的创建时间）
    private Date payAt;
    //订单同步到云端的时间
    private Date createdAt;
    //订单结算人（收银员标识）
    private String settlementBy;
    //所属人
    private String owner;
    //订单包含的商品信息（商品之间已英文逗号分隔，单个货物的的格式为：id__图片地址__名称__规格__数量__单价__总价）
    private String goods;
    //订单状态（0：为正常；1：退单；2：反结账）
    private Integer status;
    //备注信息
    private String remark;


    public Long getId(){
        return id;
    }

    public TblOrderApp setId(Long id){
        this.id = id;
        return this;
    }


    public String getNo(){
        return no;
    }

    public TblOrderApp setNo( String no){
        this.no = no;
        return this;
    }
    public Boolean getSource(){
        return source;
    }

    public TblOrderApp setSource( Boolean source){
        this.source = source;
        return this;
    }
    public String getMoneyChannel(){
        return moneyChannel;
    }

    public TblOrderApp setMoneyChannel( String moneyChannel){
        this.moneyChannel = moneyChannel;
        return this;
    }
    public BigDecimal getMoney(){
        return money;
    }

    public TblOrderApp setMoney( BigDecimal money){
        this.money = money;
        return this;
    }
    public BigDecimal getMoneyDpay(){
        return moneyDpay;
    }

    public TblOrderApp setMoneyDpay( BigDecimal moneyDpay){
        this.moneyDpay = moneyDpay;
        return this;
    }
    public BigDecimal getMoneyShishou(){
        return moneyShishou;
    }

    public TblOrderApp setMoneyShishou( BigDecimal moneyShishou){
        this.moneyShishou = moneyShishou;
        return this;
    }
    public BigDecimal getMoneyZhaol(){
        return moneyZhaol;
    }

    public TblOrderApp setMoneyZhaol( BigDecimal moneyZhaol){
        this.moneyZhaol = moneyZhaol;
        return this;
    }
    public String getChannelPay(){
        return channelPay;
    }

    public TblOrderApp setChannelPay( String channelPay){
        this.channelPay = channelPay;
        return this;
    }
    public Date getPayAt(){
        return payAt;
    }

    public TblOrderApp setPayAt( Date payAt){
        this.payAt = payAt;
        return this;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public TblOrderApp setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
        return this;
    }
    public String getSettlementBy(){
        return settlementBy;
    }

    public TblOrderApp setSettlementBy( String settlementBy){
        this.settlementBy = settlementBy;
        return this;
    }
    public String getOwner(){
        return owner;
    }

    public TblOrderApp setOwner( String owner){
        this.owner = owner;
        return this;
    }
    public String getGoods(){
        return goods;
    }

    public TblOrderApp setGoods( String goods){
        this.goods = goods;
        return this;
    }
    public Integer getStatus(){
        return status;
    }

    public TblOrderApp setStatus( Integer status){
        this.status = status;
        return this;
    }
    public String getRemark(){
        return remark;
    }

    public TblOrderApp setRemark( String remark){
        this.remark = remark;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TblOrderApp{" +
        "id=" + id +
            ", no=" + no +
            ", source=" + source +
            ", moneyChannel=" + moneyChannel +
            ", money=" + money +
            ", moneyDpay=" + moneyDpay +
            ", moneyShishou=" + moneyShishou +
            ", moneyZhaol=" + moneyZhaol +
            ", channelPay=" + channelPay +
            ", payAt=" + payAt +
            ", createdAt=" + createdAt +
            ", settlementBy=" + settlementBy +
            ", owner=" + owner +
            ", goods=" + goods +
            ", status=" + status +
            ", remark=" + remark +
        "}";
    }

}