package com.soft.ware.rest.modular.address.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
/**
  * 业务地址表
*/
@TableName("t_address")
public class TAddress extends Model<TAddress> {

    private static final long serialVersionUID = 1L;


    //：否；
    public static Integer is_default_0 = 0;
    //：是）
    public static Integer is_default_1 = 1;

    //:不删除，
    public static Integer is_delete_0 = 0;
    //删除)
    public static Integer is_delete_1 = 1;




    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //收货人手机号
    private String phone;
    //商户唯一标识

    private String ownerId;
    //收货人姓名
    private String name;
    //地址信息（省市区以逗号隔开）
    private String province;
    //详细地址（精确到门牌号）
    private String detail;
    //是否是默认地址

    private Integer isDefault;
    //是否删除(0:不删除，1删除)

    private Integer isDelete;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //创建人
    private String creater;
    //删除时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deleteTime;


    public String getId(){
        return id;
    }

    public TAddress setId(String id){
        this.id = id;
        return this;
    }


    public String getPhone(){
        return phone;
    }

    public TAddress setPhone( String phone){
        this.phone = phone;
        return this;
    }
    public String getOwnerId(){
        return ownerId;
    }

    public TAddress setOwnerId( String ownerId){
        this.ownerId = ownerId;
        return this;
    }
    public String getName(){
        return name;
    }

    public TAddress setName( String name){
        this.name = name;
        return this;
    }
    public String getProvince(){
        return province;
    }

    public TAddress setProvince( String province){
        this.province = province;
        return this;
    }
    public String getDetail(){
        return detail;
    }

    public TAddress setDetail( String detail){
        this.detail = detail;
        return this;
    }
    public Integer getIsDefault(){
        return isDefault;
    }

    public TAddress setIsDefault( Integer isDefault){
        this.isDefault = isDefault;
        return this;
    }
    public Integer getIsDelete(){
        return isDelete;
    }

    public TAddress setIsDelete( Integer isDelete){
        this.isDelete = isDelete;
        return this;
    }
    public Date getCreateTime(){
        return createTime;
    }

    public TAddress setCreatedTime( Date createTime){
        this.createTime = createTime;
        return this;
    }
    public String getCreater(){
        return creater;
    }

    public TAddress setCreater( String creater){
        this.creater = creater;
        return this;
    }
    public Date getDeleteTime(){
        return deleteTime;
    }

    public TAddress setDeleteTime( Date deleteTime){
        this.deleteTime = deleteTime;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TAddress{" +
        "id=" + id +
            ", phone=" + phone +
            ", ownerId=" + ownerId +
            ", name=" + name +
            ", province=" + province +
            ", detail=" + detail +
            ", isDefault=" + isDefault +
            ", isDelete=" + isDelete +
            ", createTime=" + createTime +
            ", creater=" + creater +
            ", deleteTime=" + deleteTime +
        "}";
    }

}