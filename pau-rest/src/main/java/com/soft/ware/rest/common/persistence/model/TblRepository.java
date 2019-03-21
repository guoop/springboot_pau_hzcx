package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
  * 商品库
*/
@TableName("tbl_repository")
public class TblRepository extends Model<TblRepository> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //商品名称
    private String name;
    //商品编码
    private String code;
    //计量单位（比如：袋、个等）
    private String measurement;
    //图片（多张图片已英文逗号分隔）
    private String pics;
    //厂商
    private String manufacturer;
    //是否删除（0：否；1：是）
    private Boolean isDelete;
    //记录添加时间
    private Date createdAt;

    public String getName(){
        return name;
    }

    public void setName( String name){
        this.name = name;
    }
    public String getCode(){
        return code;
    }

    public void setCode( String code){
        this.code = code;
    }
    public String getMeasurement(){
        return measurement;
    }

    public void setMeasurement( String measurement){
        this.measurement = measurement;
    }
    public String getPics(){
        return pics;
    }

    public void setPics( String pics){
        this.pics = pics;
    }
    public String getManufacturer(){
        return manufacturer;
    }

    public void setManufacturer( String manufacturer){
        this.manufacturer = manufacturer;
    }
    public Boolean getIsDelete(){
        return isDelete;
    }

    public void setIsDelete( Boolean isDelete){
        this.isDelete = isDelete;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
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
        return "TblRepository{" +
        "id=" + id +
            ", name=" + name +
            ", code=" + code +
            ", measurement=" + measurement +
            ", pics=" + pics +
            ", manufacturer=" + manufacturer +
            ", isDelete=" + isDelete +
            ", createdAt=" + createdAt +
        "}";
    }

}