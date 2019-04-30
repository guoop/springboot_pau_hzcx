package com.soft.ware.rest.modular.goods.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 规格表
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@TableName("t_specs")
public class TSpecs extends Model<TSpecs> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 商户唯一标识
     */
    @TableField("owner_id")
    private String ownerId;
    /**
     * 规格名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否删除（0：不删除， 1删除 ）
     */
    @TableField("is_delete")
    private Integer isDelete;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TSpecs{" +
        "id=" + id +
        ", ownerId=" + ownerId +
        ", name=" + name +
        ", sort=" + sort +
        ", isDelete=" + isDelete +
        "}";
    }
}
