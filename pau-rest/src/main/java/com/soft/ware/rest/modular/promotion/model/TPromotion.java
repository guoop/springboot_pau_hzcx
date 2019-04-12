package com.soft.ware.rest.modular.promotion.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * <p>
 * 促销表
 * </p>
 *
 * @author yancc
 * @since 2019-04-12 14:33:21
 */
@TableName("t_promotion")
public class TPromotion extends Model<TPromotion> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 商品唯一标识
     */
    @TableField("goods_id")
    private String goodsId;
    /**
     * 促销金额
     */
    private BigDecimal money;
    /**
     * 开始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * c创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人
     */
    private String creater;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 更新人
     */
    private String updater;
    /**
     * 0未删除 1 已删除
     */
    @TableField("is_delete")
    private Integer isDelete;


    public String getId() {
        return id;
    }

    public TPromotion setId(String id){
        this.id = id;return this;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public TPromotion setGoodsId( String goodsId) {
        this.goodsId = goodsId;return this;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public TPromotion setMoney( BigDecimal money) {
        this.money = money;return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public TPromotion setStartTime( Date startTime) {
        this.startTime = startTime;return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public TPromotion setEndTime( Date endTime) {
        this.endTime = endTime;return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TPromotion setCreateTime( Date createTime) {
        this.createTime = createTime;return this;
    }

    public String getCreater() {
        return creater;
    }

    public TPromotion setCreater( String creater) {
        this.creater = creater;return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public TPromotion setUpdateTime( Date updateTime) {
        this.updateTime = updateTime;return this;
    }

    public String getUpdater() {
        return updater;
    }

    public TPromotion setUpdater( String updater) {
        this.updater = updater;return this;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public TPromotion setIsDelete( Integer isDelete) {
        this.isDelete = isDelete;return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TPromotion{" +
        "id=" + id +
        ", goodsId=" + goodsId +
        ", money=" + money +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", createTime=" + createTime +
        ", creater=" + creater +
        ", updateTime=" + updateTime +
        ", updater=" + updater +
        ", isDelete=" + isDelete +
        "}";
    }
}