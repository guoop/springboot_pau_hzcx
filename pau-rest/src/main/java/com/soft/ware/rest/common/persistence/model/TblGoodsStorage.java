package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
  * 商品库存调整记录
*/
@TableName("tbl_goods_storage")
public class TblGoodsStorage extends Model<TblGoodsStorage> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //商品属主
    private String owner;
    //商品ID
    private Long goodsId;
    //单件进价
    private BigDecimal singlePrice;
    //进货数量
    private BigDecimal inAmount;
    //进货前库存
    private BigDecimal beforeStorage;
    //进货后库存
    private BigDecimal afterStorage;
    //进货金额
    private BigDecimal inMoney;
    //库存标准线
    private BigDecimal baseline;
    //进货前的库存标准线
    private BigDecimal beforeBaseline;
    //进货时间
    private Date inAt;
    //记录添加时间
    private Date createdAt;

    public String getOwner(){
        return owner;
    }

    public void setOwner( String owner){
        this.owner = owner;
    }
    public Long getGoodsId(){
        return goodsId;
    }

    public void setGoodsId( Long goodsId){
        this.goodsId = goodsId;
    }
    public BigDecimal getSinglePrice(){
        return singlePrice;
    }

    public void setSinglePrice( BigDecimal singlePrice){
        this.singlePrice = singlePrice;
    }
    public BigDecimal getInAmount(){
        return inAmount;
    }

    public void setInAmount( BigDecimal inAmount){
        this.inAmount = inAmount;
    }
    public BigDecimal getBeforeStorage(){
        return beforeStorage;
    }

    public void setBeforeStorage( BigDecimal beforeStorage){
        this.beforeStorage = beforeStorage;
    }
    public BigDecimal getAfterStorage(){
        return afterStorage;
    }

    public void setAfterStorage( BigDecimal afterStorage){
        this.afterStorage = afterStorage;
    }
    public BigDecimal getInMoney(){
        return inMoney;
    }

    public void setInMoney( BigDecimal inMoney){
        this.inMoney = inMoney;
    }
    public BigDecimal getBaseline(){
        return baseline;
    }

    public void setBaseline( BigDecimal baseline){
        this.baseline = baseline;
    }
    public BigDecimal getBeforeBaseline(){
        return beforeBaseline;
    }

    public void setBeforeBaseline( BigDecimal beforeBaseline){
        this.beforeBaseline = beforeBaseline;
    }
    public Date getInAt(){
        return inAt;
    }

    public void setInAt( Date inAt){
        this.inAt = inAt;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
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
        return "TblGoodsStorage{" +
        "id=" + id +
            ", owner=" + owner +
            ", goodsId=" + goodsId +
            ", singlePrice=" + singlePrice +
            ", inAmount=" + inAmount +
            ", beforeStorage=" + beforeStorage +
            ", afterStorage=" + afterStorage +
            ", inMoney=" + inMoney +
            ", baseline=" + baseline +
            ", beforeBaseline=" + beforeBaseline +
            ", inAt=" + inAt +
            ", createdAt=" + createdAt +
        "}";
    }

}