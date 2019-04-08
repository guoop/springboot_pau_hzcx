package com.soft.ware.rest.modular.pay_way.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
/**
  * 支付方式表
*/
@TableName("t_pay_way")
public class TPayWay extends Model<TPayWay> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //交接班记录id
    @JSONField(name = "handover_id")
    private String handoverId;
    //现金支付、聚合支付、银联支付（pos刷卡）、预存卡支付、其它支付
    private String type;
    //支付数量
    private Integer num;
    //是否删除
    @JSONField(name = "is_delete")
    private Integer isDelete;


    public String getId(){
        return id;
    }

    public TPayWay setId(String id){
        this.id = id;
        return this;
    }


    public String getHandoverId(){
        return handoverId;
    }

    public TPayWay setHandoverId( String handoverId){
        this.handoverId = handoverId;
        return this;
    }
    public String getType(){
        return type;
    }

    public TPayWay setType( String type){
        this.type = type;
        return this;
    }
    public Integer getNum(){
        return num;
    }

    public TPayWay setNum( Integer num){
        this.num = num;
        return this;
    }
    public Integer getIsDelete(){
        return isDelete;
    }

    public TPayWay setIsDelete( Integer isDelete){
        this.isDelete = isDelete;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TPayWay{" +
        "id=" + id +
            ", handoverId=" + handoverId +
            ", type=" + type +
            ", num=" + num +
            ", isDelete=" + isDelete +
        "}";
    }

}