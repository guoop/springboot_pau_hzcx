package com.soft.ware.rest.modular.wx_secret.model;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
/**
  * 第三方微信秘钥表
*/
@TableName("s_wx_secret")
public class SWxSecret extends Model<SWxSecret> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //微信支付商户号

    private String shopNo;
    //微信支付密钥

    private String payKey;
    //商户唯一标识

    private String ownerId;


    public String getId(){
        return id;
    }

    public SWxSecret setId(String id){
        this.id = id;
        return this;
    }


    public String getShopNo(){
        return shopNo;
    }

    public SWxSecret setShopNo( String shopNo){
        this.shopNo = shopNo;
        return this;
    }
    public String getPayKey(){
        return payKey;
    }

    public SWxSecret setPayKey( String payKey){
        this.payKey = payKey;
        return this;
    }
    public String getOwnerId(){
        return ownerId;
    }

    public SWxSecret setOwnerId( String ownerId){
        this.ownerId = ownerId;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SWxSecret{" +
        "id=" + id +
            ", shopNo=" + shopNo +
            ", payKey=" + payKey +
            ", ownerId=" + ownerId +
        "}";
    }

}