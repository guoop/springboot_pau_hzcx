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


    //：否；
    public static Integer is_default_0 = 0;
    //：是）
    public static Integer is_default_1 = 1;
    //：否；
    public static Integer is_deleted_0 = 0;
    //：是）
    public static Integer is_deleted_1 = 1;

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

    public TblAddress setOwner( String owner){
        this.owner = owner;
        return this;
    }
    public String getName(){
        return name;
    }

    public TblAddress setName( String name){
        this.name = name;
        return this;
    }
    public String getTelephone(){
        return telephone;
    }

    public TblAddress setTelephone( String telephone){
        this.telephone = telephone;
        return this;
    }
    public String getProvince(){
        return province;
    }

    public TblAddress setProvince( String province){
        this.province = province;
        return this;
    }
    public String getDetail(){
        return detail;
    }

    public TblAddress setDetail( String detail){
        this.detail = detail;
        return this;
    }
    public Integer getIsDefault(){
        return isDefault;
    }

    public TblAddress setIsDefault( Integer isDefault){
        this.isDefault = isDefault;
        return this;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public TblAddress setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
        return this;
    }
    public Integer getIsDeleted(){
        return isDeleted;
    }

    public TblAddress setIsDeleted( Integer isDeleted){
        this.isDeleted = isDeleted;
        return this;
    }
    public Date getDeletedAt(){
        return deletedAt;
    }

    public TblAddress setDeletedAt( Date deletedAt){
        this.deletedAt = deletedAt;
        return this;
    }


    public Long getId(){
        return id;
    }

    public TblAddress setId(Long id){
        this.id = id;
        return this;
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