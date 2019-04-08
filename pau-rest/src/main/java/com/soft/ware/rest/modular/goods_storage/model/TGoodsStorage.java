package com.soft.ware.rest.modular.goods_storage.model;
import com.alibaba.fastjson.annotation.JSONField;
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
@TableName("t_goods_storage")
public class TGoodsStorage extends Model<TGoodsStorage> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //商户唯一id
    @JSONField(name = "owner_id")
    private String ownerId;
    //商品唯一id
    @JSONField(name = "goods_id")
    private String goodsId;
    //单个商品价格
    @JSONField(name = "single_price")
    private BigDecimal singlePrice;
    //进货前库存
    @JSONField(name = "before_storage")
    private BigDecimal beforeStorage;
    //进货后库存
    @JSONField(name = "after_storage")
    private BigDecimal afterStorage;
    //进货金额
    @JSONField(name = "in_money")
    private BigDecimal inMoney;
    //库存标准线
    private BigDecimal baseline;
    //进货前的库存标准线
    @JSONField(name = "before_baseline")
    private BigDecimal beforeBaseline;
    //进货时间
    @JSONField(name = "in_time", format = "YYYY-MM-DD HH:mm:ss")
    private Date inTime;
    //进货数量
    @JSONField(name = "in_amount")
    private BigDecimal inAmount;
    //创建时间
    @JSONField(name = "create_time", format = "YYYY-MM-DD HH:mm:ss")
    private Date createTime;
    //创建人
    private String creater;


    public String getId(){
        return id;
    }

    public TGoodsStorage setId(String id){
        this.id = id;
        return this;
    }


    public String getOwnerId(){
        return ownerId;
    }

    public TGoodsStorage setOwnerId( String ownerId){
        this.ownerId = ownerId;
        return this;
    }
    public String getGoodsId(){
        return goodsId;
    }

    public TGoodsStorage setGoodsId( String goodsId){
        this.goodsId = goodsId;
        return this;
    }
    public BigDecimal getSinglePrice(){
        return singlePrice;
    }

    public TGoodsStorage setSinglePrice( BigDecimal singlePrice){
        this.singlePrice = singlePrice;
        return this;
    }
    public BigDecimal getBeforeStorage(){
        return beforeStorage;
    }

    public TGoodsStorage setBeforeStorage( BigDecimal beforeStorage){
        this.beforeStorage = beforeStorage;
        return this;
    }
    public BigDecimal getAfterStorage(){
        return afterStorage;
    }

    public TGoodsStorage setAfterStorage( BigDecimal afterStorage){
        this.afterStorage = afterStorage;
        return this;
    }
    public BigDecimal getInMoney(){
        return inMoney;
    }

    public TGoodsStorage setInMoney( BigDecimal inMoney){
        this.inMoney = inMoney;
        return this;
    }
    public BigDecimal getBaseline(){
        return baseline;
    }

    public TGoodsStorage setBaseline( BigDecimal baseline){
        this.baseline = baseline;
        return this;
    }
    public BigDecimal getBeforeBaseline(){
        return beforeBaseline;
    }

    public TGoodsStorage setBeforeBaseline( BigDecimal beforeBaseline){
        this.beforeBaseline = beforeBaseline;
        return this;
    }
    public Date getInTime(){
        return inTime;
    }

    public TGoodsStorage setInTime( Date inTime){
        this.inTime = inTime;
        return this;
    }
    public BigDecimal getInAmount(){
        return inAmount;
    }

    public TGoodsStorage setInAmount( BigDecimal inAmount){
        this.inAmount = inAmount;
        return this;
    }
    public Date getCreateTime(){
        return createTime;
    }

    public TGoodsStorage setCreateTime( Date createTime){
        this.createTime = createTime;
        return this;
    }
    public String getCreater(){
        return creater;
    }

    public TGoodsStorage setCreater( String creater){
        this.creater = creater;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TGoodsStorage{" +
        "id=" + id +
            ", ownerId=" + ownerId +
            ", goodsId=" + goodsId +
            ", singlePrice=" + singlePrice +
            ", beforeStorage=" + beforeStorage +
            ", afterStorage=" + afterStorage +
            ", inMoney=" + inMoney +
            ", baseline=" + baseline +
            ", beforeBaseline=" + beforeBaseline +
            ", inTime=" + inTime +
            ", inAmount=" + inAmount +
            ", createTime=" + createTime +
            ", creater=" + creater +
        "}";
    }

}