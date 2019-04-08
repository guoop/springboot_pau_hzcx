package com.soft.ware.rest.modular.goods.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

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

    /**
     * 主键
     */
    private String id;
    /**
     * 商品所属类别（关联表tbl_category的id字段）
     */
    @TableField("category_id")
    private Long categoryId;
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
    @TableField("created_time")
    private Date createdTime;
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
    @TableField("top_time")
    private Date topTime;
    /**
     * 是否促销商品（0：否；1：是）
     */
    @TableField("is_promotion")
    private Integer isPromotion;
    /**
     * 商品库表
     */
    @TableField("storage_id")
    private String storageId;
    /**
     * 规格表唯一id
     */
    @TableField("spec_id")
    private String specId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSells() {
        return sells;
    }

    public void setSells(Integer sells) {
        this.sells = sells;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Date getTopTime() {
        return topTime;
    }

    public void setTopTime(Date topTime) {
        this.topTime = topTime;
    }

    public Integer getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(Integer isPromotion) {
        this.isPromotion = isPromotion;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
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
        ", createdTime=" + createdTime +
        ", creater=" + creater +
        ", ownerId=" + ownerId +
        ", status=" + status +
        ", sells=" + sells +
        ", sort=" + sort +
        ", isDelete=" + isDelete +
        ", source=" + source +
        ", topTime=" + topTime +
        ", isPromotion=" + isPromotion +
        ", storageId=" + storageId +
        ", specId=" + specId +
        "}";
    }
}
