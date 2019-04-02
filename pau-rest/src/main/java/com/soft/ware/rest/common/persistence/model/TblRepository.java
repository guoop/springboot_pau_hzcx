package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
  * 商品库
*/
@TableName("tbl_repository")
public class TblRepository extends Model<TblRepository> {

    private static final long serialVersionUID = 1L;

    //：否；
    public static Integer is_delete_0 = 0;
    //：是）
    public static Integer is_delete_1 = 1;

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
    private Integer isDelete;
    //记录添加时间
    private Date createdAt;


    public Long getId(){
        return id;
    }

    public TblRepository setId(Long id){
        this.id = id;
        return this;
    }


    public String getName(){
        return name;
    }

    public TblRepository setName( String name){
        this.name = name;
        return this;
    }
    public String getCode(){
        return code;
    }

    public TblRepository setCode( String code){
        this.code = code;
        return this;
    }
    public String getMeasurement(){
        return measurement;
    }

    public TblRepository setMeasurement( String measurement){
        this.measurement = measurement;
        return this;
    }
    public String getPics(){
        return pics;
    }

    public TblRepository setPics( String pics){
        this.pics = pics;
        return this;
    }
    public String getManufacturer(){
        return manufacturer;
    }

    public TblRepository setManufacturer( String manufacturer){
        this.manufacturer = manufacturer;
        return this;
    }
    public Integer getIsDelete(){
        return isDelete;
    }

    public TblRepository setIsDelete( Integer isDelete){
        this.isDelete = isDelete;
        return this;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public TblRepository setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
        return this;
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