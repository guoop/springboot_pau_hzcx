package com.soft.ware.rest.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
  * 商户信息
*/
@TableName("tbl_owner_temp")
public class TblOwnerTemp extends Model<TblOwnerTemp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //微信支付商户号
    private String shopId;
    //微信支付密钥
    private String shopSecret;
    //小程序ID
    private String appId;
    //小程序密钥
    private String appSecret;
    //小程序名称
    private String appName;
    //小程序二维码URL
    private String appQr;
    //商家在系统中的唯一标识
    private String owner;
    //官网登录账号
    private String acctName;
    //官网登录密码
    private String acctPwd;
    //企业名称
    private String companyName;
    //企业信用代码
    private String companyCode;
    //企业营业执照URL
    private String companyLicense;
    //商家姓名
    private String name;
    //商家电话
    private String phone;
    //邮箱地址
    private String mailbox;
    //微信商户平台API证书地址
    private String refundCert;
    //模板消息ID对应的JSON字符串
    private String tmpl;
    //记录添加时间
    private Date createdAt;
    //商户基础资料状态（1：未提交审核；2：审核中；3：已通过审核；4：审核被拒）
    private Integer status01;
    //小程序资料状态（1：未提交审核；2：审核中；3：已通过审核；4：审核被拒）
    private Integer status02;
    //商户基础资料审核人
    private String passedBy01;
    //商户基础资料审核通过时间
    private Date passedAt01;
    //小程序资料审核人
    private String passedBy02;
    //小程序资料审核通过时间
    private Date passedAt02;
    //商户基础资料拒绝人
    private String refusedBy01;
    //商户基础资料拒绝时间
    private Date refusedAt01;
    //商户基础资料拒绝原因
    private String refusedReason01;
    //小程序资料拒绝人
    private String refusedBy02;
    //小程序资料拒绝时间
    private Date refusedAt02;
    //小程序资料拒绝原因
    private String refusedReason02;
    //是否已完成数据配置（将记录转移到tbl_owner表中，并将相关数据刷新到Redis中）
    private String ownerIsValid;

    public String getShopId(){
        return shopId;
    }

    public TblOwnerTemp setShopId( String shopId){
        this.shopId = shopId;
        return this;
    }
    public String getShopSecret(){
        return shopSecret;
    }

    public TblOwnerTemp setShopSecret( String shopSecret){
        this.shopSecret = shopSecret;
        return this;
    }
    public String getAppId(){
        return appId;
    }

    public TblOwnerTemp setAppId( String appId){
        this.appId = appId;
        return this;
    }
    public String getAppSecret(){
        return appSecret;
    }

    public TblOwnerTemp setAppSecret( String appSecret){
        this.appSecret = appSecret;
        return this;
    }
    public String getAppName(){
        return appName;
    }

    public TblOwnerTemp setAppName( String appName){
        this.appName = appName;
        return this;
    }
    public String getAppQr(){
        return appQr;
    }

    public TblOwnerTemp setAppQr( String appQr){
        this.appQr = appQr;
        return this;
    }
    public String getOwner(){
        return owner;
    }

    public TblOwnerTemp setOwner( String owner){
        this.owner = owner;
        return this;
    }
    public String getAcctName(){
        return acctName;
    }

    public TblOwnerTemp setAcctName( String acctName){
        this.acctName = acctName;
        return this;
    }
    public String getAcctPwd(){
        return acctPwd;
    }

    public TblOwnerTemp setAcctPwd( String acctPwd){
        this.acctPwd = acctPwd;
        return this;
    }
    public String getCompanyName(){
        return companyName;
    }

    public TblOwnerTemp setCompanyName( String companyName){
        this.companyName = companyName;
        return this;
    }
    public String getCompanyCode(){
        return companyCode;
    }

    public TblOwnerTemp setCompanyCode( String companyCode){
        this.companyCode = companyCode;
        return this;
    }
    public String getCompanyLicense(){
        return companyLicense;
    }

    public TblOwnerTemp setCompanyLicense( String companyLicense){
        this.companyLicense = companyLicense;
        return this;
    }
    public String getName(){
        return name;
    }

    public TblOwnerTemp setName( String name){
        this.name = name;
        return this;
    }
    public String getPhone(){
        return phone;
    }

    public TblOwnerTemp setPhone( String phone){
        this.phone = phone;
        return this;
    }
    public String getMailbox(){
        return mailbox;
    }

    public TblOwnerTemp setMailbox( String mailbox){
        this.mailbox = mailbox;
        return this;
    }
    public String getRefundCert(){
        return refundCert;
    }

    public TblOwnerTemp setRefundCert( String refundCert){
        this.refundCert = refundCert;
        return this;
    }
    public String getTmpl(){
        return tmpl;
    }

    public TblOwnerTemp setTmpl( String tmpl){
        this.tmpl = tmpl;
        return this;
    }
    public Date getCreatedAt(){
        return createdAt;
    }

    public TblOwnerTemp setCreatedAt( Date createdAt){
        this.createdAt = createdAt;
        return this;
    }
    public Integer getStatus01(){
        return status01;
    }

    public TblOwnerTemp setStatus01( Integer status01){
        this.status01 = status01;
        return this;
    }
    public Integer getStatus02(){
        return status02;
    }

    public TblOwnerTemp setStatus02( Integer status02){
        this.status02 = status02;
        return this;
    }
    public String getPassedBy01(){
        return passedBy01;
    }

    public TblOwnerTemp setPassedBy01( String passedBy01){
        this.passedBy01 = passedBy01;
        return this;
    }
    public Date getPassedAt01(){
        return passedAt01;
    }

    public TblOwnerTemp setPassedAt01( Date passedAt01){
        this.passedAt01 = passedAt01;
        return this;
    }
    public String getPassedBy02(){
        return passedBy02;
    }

    public TblOwnerTemp setPassedBy02( String passedBy02){
        this.passedBy02 = passedBy02;
        return this;
    }
    public Date getPassedAt02(){
        return passedAt02;
    }

    public TblOwnerTemp setPassedAt02( Date passedAt02){
        this.passedAt02 = passedAt02;
        return this;
    }
    public String getRefusedBy01(){
        return refusedBy01;
    }

    public TblOwnerTemp setRefusedBy01( String refusedBy01){
        this.refusedBy01 = refusedBy01;
        return this;
    }
    public Date getRefusedAt01(){
        return refusedAt01;
    }

    public TblOwnerTemp setRefusedAt01( Date refusedAt01){
        this.refusedAt01 = refusedAt01;
        return this;
    }
    public String getRefusedReason01(){
        return refusedReason01;
    }

    public TblOwnerTemp setRefusedReason01( String refusedReason01){
        this.refusedReason01 = refusedReason01;
        return this;
    }
    public String getRefusedBy02(){
        return refusedBy02;
    }

    public TblOwnerTemp setRefusedBy02( String refusedBy02){
        this.refusedBy02 = refusedBy02;
        return this;
    }
    public Date getRefusedAt02(){
        return refusedAt02;
    }

    public TblOwnerTemp setRefusedAt02( Date refusedAt02){
        this.refusedAt02 = refusedAt02;
        return this;
    }
    public String getRefusedReason02(){
        return refusedReason02;
    }

    public TblOwnerTemp setRefusedReason02( String refusedReason02){
        this.refusedReason02 = refusedReason02;
        return this;
    }
    public String getOwnerIsValid(){
        return ownerIsValid;
    }

    public TblOwnerTemp setOwnerIsValid( String ownerIsValid){
        this.ownerIsValid = ownerIsValid;
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
        return "TblOwnerTemp{" +
        "id=" + id +
            ", shopId=" + shopId +
            ", shopSecret=" + shopSecret +
            ", appId=" + appId +
            ", appSecret=" + appSecret +
            ", appName=" + appName +
            ", appQr=" + appQr +
            ", owner=" + owner +
            ", acctName=" + acctName +
            ", acctPwd=" + acctPwd +
            ", companyName=" + companyName +
            ", companyCode=" + companyCode +
            ", companyLicense=" + companyLicense +
            ", name=" + name +
            ", phone=" + phone +
            ", mailbox=" + mailbox +
            ", refundCert=" + refundCert +
            ", tmpl=" + tmpl +
            ", createdAt=" + createdAt +
            ", status01=" + status01 +
            ", status02=" + status02 +
            ", passedBy01=" + passedBy01 +
            ", passedAt01=" + passedAt01 +
            ", passedBy02=" + passedBy02 +
            ", passedAt02=" + passedAt02 +
            ", refusedBy01=" + refusedBy01 +
            ", refusedAt01=" + refusedAt01 +
            ", refusedReason01=" + refusedReason01 +
            ", refusedBy02=" + refusedBy02 +
            ", refusedAt02=" + refusedAt02 +
            ", refusedReason02=" + refusedReason02 +
            ", ownerIsValid=" + ownerIsValid +
        "}";
    }

}