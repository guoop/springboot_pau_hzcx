package com.soft.ware.rest.modular.goods.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@TableName("t_category")
public class TCategory extends Model<TCategory> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类父id
     */
    private String pid;
    /**
     * 备注
     */
    private String description;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否删除
     */
    @TableField("is_detele")
    private Integer isDetele;
    /**
     * 商户唯一id
     */
    @TableField("owner_id")
    private String ownerId;
    /**
     * 分类状态
     */
    private Integer status;
    /**
     * 是否系统类目（0：否；1：是），系统类目不允许执行任何操作（比如香烟类目）
     */
    @TableField("is_system")
    private Integer isSystem;
    /**
     * 分类图标唯一id
     */
    @TableField("category_icon_id")
    private String categoryIconId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsDetele() {
        return isDetele;
    }

    public void setIsDetele(Integer isDetele) {
        this.isDetele = isDetele;
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

    public Integer getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }

    public String getCategoryIconId() {
        return categoryIconId;
    }

    public void setCategoryIconId(String categoryIconId) {
        this.categoryIconId = categoryIconId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TCategory{" +
        "id=" + id +
        ", name=" + name +
        ", pid=" + pid +
        ", description=" + description +
        ", sort=" + sort +
        ", isDetele=" + isDetele +
        ", ownerId=" + ownerId +
        ", status=" + status +
        ", isSystem=" + isSystem +
        ", categoryIconId=" + categoryIconId +
        "}";
    }
}
