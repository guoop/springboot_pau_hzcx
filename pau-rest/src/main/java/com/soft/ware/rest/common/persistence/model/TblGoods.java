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
  * 商品信息表
*/
@TableName("tbl_goods")
public class TblGoods extends Model<TblGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //商品所属类别（关联表tbl_category的id字段）
    private Long categoryId;
    //细分类目
    private String categoryChild;
    //商品名称
    private String name;
    //商品编码
    private String code;
    //计量单位
    private String measurementUnit;
    //单价（对应到单个计量单位）
    private BigDecimal priceUnit;
    //商品图片（如果有多个图片则以英文逗号分隔）
    private String pics;
    //商品描述（目前只支持纯文字）
    private String description;
    //商品规格，不同规格的商品价格是一致的（多个规格使用英文逗号分隔）
    private String specs;
    //当前库存标准线
    private BigDecimal baseline;
    //库存
    private BigDecimal stock;
    //添加时间
    private Date createdAt;
    //添加人
    private Long createdBy;
    //商品所属人
    private String owner;
    //状态（0：未上架；1：销售中；2：已下架）
    private Integer status;
    //销量
    private Integer sells;
    //显示顺序（数字越小越靠前显示）
    private Integer sortNum;
    //是否已删除（0：未删除；1：已删除）
    private Integer isDelete;
    //添加来源（1：扫码添加；2：小程序手工添加；3：后台手工添加；4：导入）
    private Integer source;
    //置顶时间（用于控制买家端首页商品的展示顺序）
    private Date topTime;
    //是否促销商品（0：否；1：是）
    private Integer isPromotion;
    //促销价格
    private BigDecimal promotionPrice;
    //促销结束时间
    private Date promotionEndtime;

    public Long getCategoryId(){
        return categoryId;
    }

    public void setCategoryId( Long categoryId){
        this.categoryId = categoryId;
    }
    public String getCategoryChild(){
        return categoryChild;
    }

    public void setCategoryChild( String categoryChild){
        this.categoryChild = categoryChild;
    }
    public String getName(){
        return name;
    }

    public void setName( String name){
        this.name = name;
    }
    public String getCode(){
        return code;
    }

    public void setCode( String code){
        this.code = code;
    }
    public String getMeasurementUnit(){
        return measurementUnit;
    }

    public void setMeasurementUnit( String measurementUnit){
        this.measurementUnit = measurementUnit;
    }
    public BigDecimal getPriceUnit(){
        return priceUnit;
    }

    public void setPriceUnit( BigDecimal priceUnit){
        this.priceUnit = priceUnit;
    }
    public String getPics(){
        return pics;
    }

    public void setPics( String pics){
        this.pics = pics;
    }
    public String getDescription(){
        return description;
    }

    public void setDescription( String description){
        this.description = description;
    }
    public String getSpecs(){
        return specs;
    }

    public void setSpecs( String specs){
        this.specs = specs;
    }
    public BigDecimal getBaseline(){
        return baseline;
    }

    public void setBaseline( BigDecimal baseline){
        this.baseline = baseline;
    }
    public BigDecimal getStock(){
        return stock;
    }

    public void setStock( BigDecimal stock){
        this.stock = stock;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
    }
    public Long getCreatedBy(){
        return createdBy;
    }

    public void setCreatedBy( Long createdBy){
        this.createdBy = createdBy;
    }
    public String getOwner(){
        return owner;
    }

    public void setOwner( String owner){
        this.owner = owner;
    }
    public Integer getStatus(){
        return status;
    }

    public void setStatus( Integer status){
        this.status = status;
    }
    public Integer getSells(){
        return sells;
    }

    public void setSells( Integer sells){
        this.sells = sells;
    }
    public Integer getSortNum(){
        return sortNum;
    }

    public void setSortNum( Integer sortNum){
        this.sortNum = sortNum;
    }
    public Integer getIsDelete(){
        return isDelete;
    }

    public void setIsDelete( Integer isDelete){
        this.isDelete = isDelete;
    }
    public Integer getSource(){
        return source;
    }

    public void setSource( Integer source){
        this.source = source;
    }
    public Date getTopTime(){
        return topTime;
    }

    public void setTopTime( Date topTime){
        this.topTime = topTime;
    }
    public Integer getIsPromotion(){
        return isPromotion;
    }

    public void setIsPromotion( Integer isPromotion){
        this.isPromotion = isPromotion;
    }
    public BigDecimal getPromotionPrice(){
        return promotionPrice;
    }

    public void setPromotionPrice( BigDecimal promotionPrice){
        this.promotionPrice = promotionPrice;
    }
    public Date getPromotionEndtime(){
        return promotionEndtime;
    }

    public void setPromotionEndtime( Date promotionEndtime){
        this.promotionEndtime = promotionEndtime;
    }


    public  Long getId(){
        return id;
    }

    public TblGoods setId(Long id){
        this.id = id;
        return this;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TblGoods{" +
        "id=" + id +
            ", categoryId=" + categoryId +
            ", categoryChild=" + categoryChild +
            ", name=" + name +
            ", code=" + code +
            ", measurementUnit=" + measurementUnit +
            ", priceUnit=" + priceUnit +
            ", pics=" + pics +
            ", description=" + description +
            ", specs=" + specs +
            ", baseline=" + baseline +
            ", stock=" + stock +
            ", createdAt=" + createdAt +
            ", createdBy=" + createdBy +
            ", owner=" + owner +
            ", status=" + status +
            ", sells=" + sells +
            ", sortNum=" + sortNum +
            ", isDelete=" + isDelete +
            ", source=" + source +
            ", topTime=" + topTime +
            ", isPromotion=" + isPromotion +
            ", promotionPrice=" + promotionPrice +
            ", promotionEndtime=" + promotionEndtime +
        "}";
    }

}