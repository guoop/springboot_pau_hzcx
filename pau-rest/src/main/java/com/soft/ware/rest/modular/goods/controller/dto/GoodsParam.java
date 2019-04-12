package com.soft.ware.rest.modular.goods.controller.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
  * 商品信息表
*/
public class GoodsParam extends Model<GoodsParam> {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id")
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
    private String unitId;
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


    public Long getId(){
        return id;
    }

    public GoodsParam setId(Long id){
        this.id = id;
        return this;
    }


    public Long getCategoryId(){
        return categoryId;
    }

    public GoodsParam setCategoryId(Long categoryId){
        this.categoryId = categoryId;
        return this;
    }
    public String getCategoryChild(){
        return categoryChild;
    }

    public GoodsParam setCategoryChild(String categoryChild){
        this.categoryChild = categoryChild;
        return this;
    }
    public String getName(){
        return name;
    }

    public GoodsParam setName(String name){
        this.name = name;
        return this;
    }
    public String getCode(){
        return code;
    }

    public GoodsParam setCode(String code){
        this.code = code;
        return this;
    }
    public String getMeasurementUnit(){
        return unitId;
    }

    public GoodsParam setMeasurementUnit(String unitId){
        this.unitId = unitId;
        return this;
    }
    public BigDecimal getPriceUnit(){
        return priceUnit;
    }

    public GoodsParam setPriceUnit(BigDecimal priceUnit){
        this.priceUnit = priceUnit;
        return this;
    }
    public String getPics(){
        return pics;
    }

    public GoodsParam setPics(String pics){
        this.pics = pics;
        return this;
    }
    public String getDescription(){
        return description;
    }

    public GoodsParam setDescription(String description){
        this.description = description;
        return this;
    }
    public String getSpecs(){
        return specs;
    }

    public GoodsParam setSpecs(String specs){
        this.specs = specs;
        return this;
    }
    public BigDecimal getBaseline(){
        return baseline;
    }

    public GoodsParam setBaseline(BigDecimal baseline){
        this.baseline = baseline;
        return this;
    }
    public BigDecimal getStock(){
        return stock;
    }

    public GoodsParam setStock(BigDecimal stock){
        this.stock = stock;
        return this;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public GoodsParam setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
        return this;
    }
    public Long getCreatedBy(){
        return createdBy;
    }

    public GoodsParam setCreatedBy(Long createdBy){
        this.createdBy = createdBy;
        return this;
    }
    public String getOwner(){
        return owner;
    }

    public GoodsParam setOwner(String owner){
        this.owner = owner;
        return this;
    }
    public Integer getStatus(){
        return status;
    }

    public GoodsParam setStatus(Integer status){
        this.status = status;
        return this;
    }
    public Integer getSells(){
        return sells;
    }

    public GoodsParam setSells(Integer sells){
        this.sells = sells;
        return this;
    }
    public Integer getSortNum(){
        return sortNum;
    }

    public GoodsParam setSortNum(Integer sortNum){
        this.sortNum = sortNum;
        return this;
    }
    public Integer getIsDelete(){
        return isDelete;
    }

    public GoodsParam setIsDelete(Integer isDelete){
        this.isDelete = isDelete;
        return this;
    }
    public Integer getSource(){
        return source;
    }

    public GoodsParam setSource(Integer source){
        this.source = source;
        return this;
    }
    public Date getTopTime(){
        return topTime;
    }

    public GoodsParam setTopTime(Date topTime){
        this.topTime = topTime;
        return this;
    }
    public Integer getIsPromotion(){
        return isPromotion;
    }

    public GoodsParam setIsPromotion(Integer isPromotion){
        this.isPromotion = isPromotion;
        return this;
    }
    public BigDecimal getPromotionPrice(){
        return promotionPrice;
    }

    public GoodsParam setPromotionPrice(BigDecimal promotionPrice){
        this.promotionPrice = promotionPrice;
        return this;
    }
    public Date getPromotionEndtime(){
        return promotionEndtime;
    }

    public GoodsParam setPromotionEndtime(Date promotionEndtime){
        this.promotionEndtime = promotionEndtime;
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
            ", measurementUnit=" + unitId +
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