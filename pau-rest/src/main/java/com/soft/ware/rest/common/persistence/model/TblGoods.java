package com.soft.ware.rest.common.persistence.model;

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
@TableName("tbl_goods")
public class TblGoods extends Model<TblGoods> {

    private static final long serialVersionUID = 1L;

    //：未上架；
    public static Integer status_0 = 0;
    //：销售中；
    public static Integer status_1 = 1;
    //：已下架）
    public static Integer status_2 = 2;
    //：未删除；
    public static Integer is_delete_0 = 0;
    //：已删除）
    public static Integer is_delete_1 = 1;
    //：扫码添加；
    public static Integer source_1 = 1;
    //：小程序手工添加；
    public static Integer source_2 = 2;
    //：后台手工添加；
    public static Integer source_3 = 3;
    //：导入）
    public static Integer source_4 = 4;
    //：否；
    public static Integer is_promotion_0 = 0;
    //：是）
    public static Integer is_promotion_1 = 1;

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


    public Long getId(){
        return id;
    }

    public TblGoods setId(Long id){
        this.id = id;
        return this;
    }


    public Long getCategoryId(){
        return categoryId;
    }

    public TblGoods setCategoryId( Long categoryId){
        this.categoryId = categoryId;
        return this;
    }
    public String getCategoryChild(){
        return categoryChild;
    }

    public TblGoods setCategoryChild( String categoryChild){
        this.categoryChild = categoryChild;
        return this;
    }
    public String getName(){
        return name;
    }

    public TblGoods setName( String name){
        this.name = name;
        return this;
    }
    public String getCode(){
        return code;
    }

    public TblGoods setCode( String code){
        this.code = code;
        return this;
    }
    public String getMeasurementUnit(){
        return measurementUnit;
    }

    public TblGoods setMeasurementUnit( String measurementUnit){
        this.measurementUnit = measurementUnit;
        return this;
    }
    public BigDecimal getPriceUnit(){
        return priceUnit;
    }

    public TblGoods setPriceUnit( BigDecimal priceUnit){
        this.priceUnit = priceUnit;
        return this;
    }
    public String getPics(){
        return pics;
    }

    public TblGoods setPics( String pics){
        this.pics = pics;
        return this;
    }
    public String getDescription(){
        return description;
    }

    public TblGoods setDescription( String description){
        this.description = description;
        return this;
    }
    public String getSpecs(){
        return specs;
    }

    public TblGoods setSpecs( String specs){
        this.specs = specs;
        return this;
    }
    public BigDecimal getBaseline(){
        return baseline;
    }

    public TblGoods setBaseline( BigDecimal baseline){
        this.baseline = baseline;
        return this;
    }
    public BigDecimal getStock(){
        return stock;
    }

    public TblGoods setStock( BigDecimal stock){
        this.stock = stock;
        return this;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public TblGoods setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
        return this;
    }
    public Long getCreatedBy(){
        return createdBy;
    }

    public TblGoods setCreatedBy( Long createdBy){
        this.createdBy = createdBy;
        return this;
    }
    public String getOwner(){
        return owner;
    }

    public TblGoods setOwner( String owner){
        this.owner = owner;
        return this;
    }
    public Integer getStatus(){
        return status;
    }

    public TblGoods setStatus( Integer status){
        this.status = status;
        return this;
    }
    public Integer getSells(){
        return sells;
    }

    public TblGoods setSells( Integer sells){
        this.sells = sells;
        return this;
    }
    public Integer getSortNum(){
        return sortNum;
    }

    public TblGoods setSortNum( Integer sortNum){
        this.sortNum = sortNum;
        return this;
    }
    public Integer getIsDelete(){
        return isDelete;
    }

    public TblGoods setIsDelete( Integer isDelete){
        this.isDelete = isDelete;
        return this;
    }
    public Integer getSource(){
        return source;
    }

    public TblGoods setSource( Integer source){
        this.source = source;
        return this;
    }
    public Date getTopTime(){
        return topTime;
    }

    public TblGoods setTopTime( Date topTime){
        this.topTime = topTime;
        return this;
    }
    public Integer getIsPromotion(){
        return isPromotion;
    }

    public TblGoods setIsPromotion( Integer isPromotion){
        this.isPromotion = isPromotion;
        return this;
    }
    public BigDecimal getPromotionPrice(){
        return promotionPrice;
    }

    public TblGoods setPromotionPrice( BigDecimal promotionPrice){
        this.promotionPrice = promotionPrice;
        return this;
    }
    public Date getPromotionEndtime(){
        return promotionEndtime;
    }

    public TblGoods setPromotionEndtime( Date promotionEndtime){
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