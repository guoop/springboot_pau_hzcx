package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
  * 收货地址
*/
@TableName("tbl_address")
public class TblAddress extends Model<TblAddress> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //属主（买家的openID）
    private String owner;
    //收货人姓名
    private String name;
    //收货人手机号码
    private String telephone;
    //收货人的省市区信息
    private String province;
    //收货人的详细地址
    private String detail;
    //是否默认地址（0：否；1：是）
    private Integer isDefault;
    //
    private Date createdAt;
    //是否删除（0：否；1：是）
    private Integer isDeleted;
    //
    private Date deletedAt;

    public String getOwner(){
        return owner;
    }

    public void setOwner( String owner){
        this.owner = owner;
    }
    public String getName(){
        return name;
    }

    public void setName( String name){
        this.name = name;
    }
    public String getTelephone(){
        return telephone;
    }

    public void setTelephone( String telephone){
        this.telephone = telephone;
    }
    public String getProvince(){
        return province;
    }

    public void setProvince( String province){
        this.province = province;
    }
    public String getDetail(){
        return detail;
    }

    public void setDetail( String detail){
        this.detail = detail;
    }
    public Integer getIsDefault(){
        return isDefault;
    }

    public void setIsDefault( Integer isDefault){
        this.isDefault = isDefault;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
    }
    public Integer getIsDeleted(){
        return isDeleted;
    }

    public void setIsDeleted( Integer isDeleted){
        this.isDeleted = isDeleted;
    }
    public Date getDeletedAt(){
        return deletedAt;
    }

    public void setDeletedAt( Date deletedAt){
        this.deletedAt = deletedAt;
    }


    public  Long getId(){
        return id;
    }

    public  void setId(Long id){
        this.id = id;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TblAddress{" +
        "id=" + id +
            ", owner=" + owner +
            ", name=" + name +
            ", telephone=" + telephone +
            ", province=" + province +
            ", detail=" + detail +
            ", isDefault=" + isDefault +
            ", createdAt=" + createdAt +
            ", isDeleted=" + isDeleted +
            ", deletedAt=" + deletedAt +
        "}";
    }

}