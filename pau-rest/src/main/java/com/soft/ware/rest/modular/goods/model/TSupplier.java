package com.soft.ware.rest.modular.goods.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 供应商
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@TableName("t_supplier")
public class TSupplier extends Model<TSupplier> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;
    /**
     * 供应商电话
     */
    private String phone;
    /**
     * 供应商地址
     */
    private String addrress;
    /**
     * 详细地址
     */
    @TableField("detail_address")
    private String detailAddress;
    /**
     * 状态（0启用，1不启用）
     */
    private Integer status;
    /**
     * 商户id
     */
    @TableField("owner_id")
    private String ownerId;
    /**
     * 供应商名称
     */
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddrress() {
        return addrress;
    }

    public void setAddrress(String addrress) {
        this.addrress = addrress;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TSupplier{" +
        "id=" + id +
        ", phone=" + phone +
        ", addrress=" + addrress +
        ", detailAddress=" + detailAddress +
        ", status=" + status +
        ", ownerId=" + ownerId +
        ", name=" + name +
        "}";
    }
}
