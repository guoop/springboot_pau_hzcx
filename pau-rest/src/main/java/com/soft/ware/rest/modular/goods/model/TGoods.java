package com.soft.ware.rest.modular.goods.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品信息表
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@TableName("t_goods")
public class TGoods extends Model<TGoods> {

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

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 商品所属类别（关联表t_category的id字段）
     */
    @TableField("category_id")
    private String categoryId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品编码
     */
    private String code;
    /**
     * 计量单位
     */
    @TableField("unit_id")
    private String unitId;
    /**
     * 单价（对应到单个计量单位）
     */
    private BigDecimal price;
    /**
     * 商品图片（如果有多个图片则以英文逗号分隔）
     */
    private String pics;
    /**
     * 商品描述（目前只支持纯文字）
     */
    private String description;
    /**
     * 添加时间
     */
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @TableField("create_time")
    private Date createTime;
    /**
     * 添加人
     */
    private String creater;
    /**
     * 商品所属人
     */
    @TableField("owner_id")
    private String ownerId;
    /**
     * 状态（0：未上架；1：销售中；2：已下架）
     */
    private Integer status;
    /**
     * 销量
     */
    private Integer sells;
    /**
     * 显示顺序（数字越小越靠前显示）
     */
    private Integer sort;
    /**
     * 是否已删除（0：未删除；1：已删除）
     */
    @TableField("is_delete")
    private Integer isDelete;
    /**
     * 添加来源（1：扫码添加；2：小程序手工添加；3：后台手工添加；4：导入）
     */
    private Integer source;
    /**
     * 置顶时间（用于控制买家端首页商品的展示顺序）
     */
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @TableField("top_time")
    private Date topTime;
    /**
     * 是否促销商品（0：否；1：是）
     */
    @TableField("is_promotion")
    private Integer isPromotion;


    public String getId() {
        return id;
    }

    public TGoods setId(String id){
        this.id = id;return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public TGoods setCategoryId( String categoryId) {
        this.categoryId = categoryId;return this;
    }

    public String getName() {
        return name;
    }

    public TGoods setName( String name) {
        this.name = name;return this;
    }

    public String getCode() {
        return code;
    }

    public TGoods setCode( String code) {
        this.code = code;return this;
    }

    public String getUnitId() {
        return unitId;
    }

    public TGoods setUnitId( String unitId) {
        this.unitId = unitId;return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TGoods setPrice( BigDecimal price) {
        this.price = price;return this;
    }

    public String getPics() {
        return pics;
    }

    public TGoods setPics( String pics) {
        this.pics = pics;return this;
    }

    public String getDescription() {
        return description;
    }

    public TGoods setDescription( String description) {
        this.description = description;return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TGoods setCreateTime( Date createTime) {
        this.createTime = createTime;return this;
    }

    public String getCreater() {
        return creater;
    }

    public TGoods setCreater( String creater) {
        this.creater = creater;return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public TGoods setOwnerId( String ownerId) {
        this.ownerId = ownerId;return this;
    }

    public Integer getStatus() {
        return status;
    }

    public TGoods setStatus( Integer status) {
        this.status = status;return this;
    }

    public Integer getSells() {
        return sells;
    }

    public TGoods setSells( Integer sells) {
        this.sells = sells;return this;
    }

    public Integer getSort() {
        return sort;
    }

    public TGoods setSort( Integer sort) {
        this.sort = sort;return this;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public TGoods setIsDelete( Integer isDelete) {
        this.isDelete = isDelete;return this;
    }

    public Integer getSource() {
        return source;
    }

    public TGoods setSource( Integer source) {
        this.source = source;return this;
    }

    public Date getTopTime() {
        return topTime;
    }

    public TGoods setTopTime( Date topTime) {
        this.topTime = topTime;return this;
    }

    public Integer getIsPromotion() {
        return isPromotion;
    }

    public TGoods setIsPromotion( Integer isPromotion) {
        this.isPromotion = isPromotion;return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TGoods{" +
        "id=" + id +
        ", categoryId=" + categoryId +
        ", name=" + name +
        ", code=" + code +
        ", unitId=" + unitId +
        ", price=" + price +
        ", pics=" + pics +
        ", description=" + description +
        ", createTime=" + createTime +
        ", creater=" + creater +
        ", ownerId=" + ownerId +
        ", status=" + status +
        ", sells=" + sells +
        ", sort=" + sort +
        ", isDelete=" + isDelete +
        ", source=" + source +
        ", topTime=" + topTime +
        ", isPromotion=" + isPromotion +
        "}";
    }
}