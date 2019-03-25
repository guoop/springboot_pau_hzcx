package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
/**
  * 聚合支付相关信息
*/
@TableName("tbl_owner_aggregate_payment")
public class TblOwnerAggregatePayment extends Model<TblOwnerAggregatePayment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //属主
    private String owner;
    //聚合支付服务商（1：钱台好近）
    private Integer source;
    //商户环境（0：测试；1：正式）
    private Integer sourceEnv;
    //
    private String appCode;
    //
    private String appKey;
    //
    private String mchId;
    //接口地址
    private String apiUrl;

    public String getOwner(){
        return owner;
    }

    public TblOwnerAggregatePayment setOwner( String owner){
        this.owner = owner;
        return this;
    }
    public Integer getSource(){
        return source;
    }

    public TblOwnerAggregatePayment setSource( Integer source){
        this.source = source;
        return this;
    }
    public Integer getSourceEnv(){
        return sourceEnv;
    }

    public TblOwnerAggregatePayment setSourceEnv( Integer sourceEnv){
        this.sourceEnv = sourceEnv;
        return this;
    }
    public String getAppCode(){
        return appCode;
    }

    public TblOwnerAggregatePayment setAppCode( String appCode){
        this.appCode = appCode;
        return this;
    }
    public String getAppKey(){
        return appKey;
    }

    public TblOwnerAggregatePayment setAppKey( String appKey){
        this.appKey = appKey;
        return this;
    }
    public String getMchId(){
        return mchId;
    }

    public TblOwnerAggregatePayment setMchId( String mchId){
        this.mchId = mchId;
        return this;
    }
    public String getApiUrl(){
        return apiUrl;
    }

    public TblOwnerAggregatePayment setApiUrl( String apiUrl){
        this.apiUrl = apiUrl;
        return this;
    }


    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TblOwnerAggregatePayment{" +
        "id=" + id +
            ", owner=" + owner +
            ", source=" + source +
            ", sourceEnv=" + sourceEnv +
            ", appCode=" + appCode +
            ", appKey=" + appKey +
            ", mchId=" + mchId +
            ", apiUrl=" + apiUrl +
        "}";
    }

}