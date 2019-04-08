package com.soft.ware.rest.modular.order_app.model;
import com.alibaba.fastjson.annotation.JSONField;
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
@TableName("t_order_app")
public class TOrderApp extends Model<TOrderApp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //订单编号
    private String no;
    //订单来源（0：安卓收银机；1：Windows收银机）
    private Short source;
    //付款方式（1：现金支付；2：微信支付；3：支付宝支付；4；银联支付），多种支付方式以_分割
    @JSONField(name = "money_channel")
    private String moneyChannel;
    //订单金额，即：买家购买商品的总价（优惠前）
    private BigDecimal money;
    //买家支付金额（优惠后）
    @JSONField(name = "money_dpay")
    private BigDecimal moneyDpay;
    //实收金额（包含找零金额）
    @JSONField(name = "money_shishou")
    private BigDecimal moneyShishou;
    //找零金额
    @JSONField(name = "money_zhaol")
    private BigDecimal moneyZhaol;
    //支付明细(支付方式_支付金钱,支付方式_支付金钱)
    @JSONField(name = "channel_pay")
    private String channelPay;
    //支付时间（即：订单在APP中的创建时间）
    @JSONField(name = "pay_at", format = "YYYY-MM-DD HH:mm:ss")
    private Date payAt;
    //订单同步到云端的时间
    @JSONField(name = "created_at", format = "YYYY-MM-DD HH:mm:ss")
    private Date createdAt;
    //订单结算人（收银员标识）
    @JSONField(name = "settlement_by")
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

    public TOrderApp setId(Long id){
        this.id = id;
        return this;
    }


    public String getNo(){
        return no;
    }

    public TOrderApp setNo( String no){
        this.no = no;
        return this;
    }
    public Short getSource(){
        return source;
    }

    public TOrderApp setSource( Short source){
        this.source = source;
        return this;
    }
    public String getMoneyChannel(){
        return moneyChannel;
    }

    public TOrderApp setMoneyChannel( String moneyChannel){
        this.moneyChannel = moneyChannel;
        return this;
    }
    public BigDecimal getMoney(){
        return money;
    }

    public TOrderApp setMoney( BigDecimal money){
        this.money = money;
        return this;
    }
    public BigDecimal getMoneyDpay(){
        return moneyDpay;
    }

    public TOrderApp setMoneyDpay( BigDecimal moneyDpay){
        this.moneyDpay = moneyDpay;
        return this;
    }
    public BigDecimal getMoneyShishou(){
        return moneyShishou;
    }

    public TOrderApp setMoneyShishou( BigDecimal moneyShishou){
        this.moneyShishou = moneyShishou;
        return this;
    }
    public BigDecimal getMoneyZhaol(){
        return moneyZhaol;
    }

    public TOrderApp setMoneyZhaol( BigDecimal moneyZhaol){
        this.moneyZhaol = moneyZhaol;
        return this;
    }
    public String getChannelPay(){
        return channelPay;
    }

    public TOrderApp setChannelPay( String channelPay){
        this.channelPay = channelPay;
        return this;
    }
    public Date getPayAt(){
        return payAt;
    }

    public TOrderApp setPayAt( Date payAt){
        this.payAt = payAt;
        return this;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public TOrderApp setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
        return this;
    }
    public String getSettlementBy(){
        return settlementBy;
    }

    public TOrderApp setSettlementBy( String settlementBy){
        this.settlementBy = settlementBy;
        return this;
    }
    public String getOwner(){
        return owner;
    }

    public TOrderApp setOwner( String owner){
        this.owner = owner;
        return this;
    }
    public String getGoods(){
        return goods;
    }

    public TOrderApp setGoods( String goods){
        this.goods = goods;
        return this;
    }
    public Integer getStatus(){
        return status;
    }

    public TOrderApp setStatus( Integer status){
        this.status = status;
        return this;
    }
    public String getRemark(){
        return remark;
    }

    public TOrderApp setRemark( String remark){
        this.remark = remark;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TOrderApp{" +
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