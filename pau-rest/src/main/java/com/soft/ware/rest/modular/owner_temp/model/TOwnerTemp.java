package com.soft.ware.rest.modular.owner_temp.model;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>
 * 商户信息采集表
 * </p>
 *
 * @author yancc
 * @since 2019-04-18 16:53:22
 */
@TableName("t_owner_temp")
public class TOwnerTemp extends Model<TOwnerTemp> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 微信支付商户号
     */
    private String shopId;
    /**
     * 微信支付密钥
     */
    private String shopSecret;
    /**
     * 小程序ID
     */
    private String appId;
    /**
     * app_secret
     */
    private String appSecret;
    /**
     * 小程序名称
     */
    private String appName;
    /**
     * 小程序二维码URL
     */
    private String appQr;
    /**
     * 商家在系统中的唯一标识
     */
    private String ownerId;
    /**
     * 官网登录账号
     */
    private String acctName;
    /**
     * 官网登录密码
     */
    private String acctPwd;
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 企业营业执照URL
     */
    private String companyLicense;
    /**
     * 企业信用代码
     */
    private String companyCode;
    /**
     * 商家姓名
     */
    private String name;
    /**
     * 商家电话
     */
    private String phone;
    /**
     * 邮箱地址
     */
    private String mailbox;
    /**
     * 微信商户平台API证书地址
     */
    private String wxApiCard;
    /**
     * 模板消息ID对应的JSON字符串
     */
    private String msgTmplate;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String creater;
    /**
     * 商户资料状态（1：未提交审核；2：审核中；3：已通过审核；4：审核被拒）
     */
    private Integer ownerDataStatus;
    /**
     * 小程序资料状态（1：未提交审核；2：审核中；3：已通过审核；4：审核被拒）
     */
    private Integer wxappDataStatus;
    /**
     * 商户资料审核人
     */
    private String ownerDataAuditor;
    /**
     *  商户资料审核通过时间
     */
    private Date ownerDataTime;
    /**
     * 小程序审核人
     */
    private String wxappDataAuditor;
    /**
     * 小程序审核时间
     */
    private Date wxappDataTime;
    /**
     * 商户资料拒绝人
     */
    private String ownerRefuser;
    /**
     * 
     */
    private Date ownerRefuserTime;
    /**
     * 
     */
    private String ownerRefuserReason;
    /**
     * 
     */
    private String wxappRefuser;
    /**
     * 小程序拒绝时间
     */
    private Date wxappRefuserTime;
    /**
     * 小程序拒绝原因
     */
    private String wxappRefuserReason;
    /**
     * 是否已完成配置
     */
    private String ownerIsValid;
    /**
     * 是否审核通过
     */
    private Integer isVerify;


    public String getId() {
        return id;
    }

    public TOwnerTemp setId(String id){
        this.id = id;return this;
    }

    public String getShopId() {
        return shopId;
    }

    public TOwnerTemp setShopId( String shopId) {
        this.shopId = shopId;return this;
    }

    public String getShopSecret() {
        return shopSecret;
    }

    public TOwnerTemp setShopSecret( String shopSecret) {
        this.shopSecret = shopSecret;return this;
    }

    public String getAppId() {
        return appId;
    }

    public TOwnerTemp setAppId( String appId) {
        this.appId = appId;return this;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public TOwnerTemp setAppSecret( String appSecret) {
        this.appSecret = appSecret;return this;
    }

    public String getAppName() {
        return appName;
    }

    public TOwnerTemp setAppName( String appName) {
        this.appName = appName;return this;
    }

    public String getAppQr() {
        return appQr;
    }

    public TOwnerTemp setAppQr( String appQr) {
        this.appQr = appQr;return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public TOwnerTemp setOwnerId( String ownerId) {
        this.ownerId = ownerId;return this;
    }

    public String getAcctName() {
        return acctName;
    }

    public TOwnerTemp setAcctName( String acctName) {
        this.acctName = acctName;return this;
    }

    public String getAcctPwd() {
        return acctPwd;
    }

    public TOwnerTemp setAcctPwd( String acctPwd) {
        this.acctPwd = acctPwd;return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public TOwnerTemp setCompanyName( String companyName) {
        this.companyName = companyName;return this;
    }

    public String getCompanyLicense() {
        return companyLicense;
    }

    public TOwnerTemp setCompanyLicense( String companyLicense) {
        this.companyLicense = companyLicense;return this;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public TOwnerTemp setCompanyCode( String companyCode) {
        this.companyCode = companyCode;return this;
    }

    public String getName() {
        return name;
    }

    public TOwnerTemp setName( String name) {
        this.name = name;return this;
    }

    public String getPhone() {
        return phone;
    }

    public TOwnerTemp setPhone( String phone) {
        this.phone = phone;return this;
    }

    public String getMailbox() {
        return mailbox;
    }

    public TOwnerTemp setMailbox( String mailbox) {
        this.mailbox = mailbox;return this;
    }

    public String getWxApiCard() {
        return wxApiCard;
    }

    public TOwnerTemp setWxApiCard( String wxApiCard) {
        this.wxApiCard = wxApiCard;return this;
    }

    public String getMsgTmplate() {
        return msgTmplate;
    }

    public TOwnerTemp setMsgTmplate( String msgTmplate) {
        this.msgTmplate = msgTmplate;return this;
    }

    public Date getCreatedTime() {
        return createTime;
    }

    public TOwnerTemp setCreatedTime( Date createdTime) {
        this.createTime = createdTime;return this;
    }

    public String getCreater() {
        return creater;
    }

    public TOwnerTemp setCreater( String creater) {
        this.creater = creater;return this;
    }

    public Integer getOwnerDataStatus() {
        return ownerDataStatus;
    }

    public TOwnerTemp setOwnerDataStatus( Integer ownerDataStatus) {
        this.ownerDataStatus = ownerDataStatus;return this;
    }

    public Integer getWxappDataStatus() {
        return wxappDataStatus;
    }

    public TOwnerTemp setWxappDataStatus( Integer wxappDataStatus) {
        this.wxappDataStatus = wxappDataStatus;return this;
    }

    public String getOwnerDataAuditor() {
        return ownerDataAuditor;
    }

    public TOwnerTemp setOwnerDataAuditor( String ownerDataAuditor) {
        this.ownerDataAuditor = ownerDataAuditor;return this;
    }

    public Date getOwnerDataTime() {
        return ownerDataTime;
    }

    public TOwnerTemp setOwnerDataTime( Date ownerDataTime) {
        this.ownerDataTime = ownerDataTime;return this;
    }

    public String getWxappDataAuditor() {
        return wxappDataAuditor;
    }

    public TOwnerTemp setWxappDataAuditor( String wxappDataAuditor) {
        this.wxappDataAuditor = wxappDataAuditor;return this;
    }

    public Date getWxappDataTime() {
        return wxappDataTime;
    }

    public TOwnerTemp setWxappDataTime( Date wxappDataTime) {
        this.wxappDataTime = wxappDataTime;return this;
    }

    public String getOwnerRefuser() {
        return ownerRefuser;
    }

    public TOwnerTemp setOwnerRefuser( String ownerRefuser) {
        this.ownerRefuser = ownerRefuser;return this;
    }

    public Date getOwnerRefuserTime() {
        return ownerRefuserTime;
    }

    public TOwnerTemp setOwnerRefuserTime( Date ownerRefuserTime) {
        this.ownerRefuserTime = ownerRefuserTime;return this;
    }

    public String getOwnerRefuserReason() {
        return ownerRefuserReason;
    }

    public TOwnerTemp setOwnerRefuserReason( String ownerRefuserReason) {
        this.ownerRefuserReason = ownerRefuserReason;return this;
    }

    public String getWxappRefuser() {
        return wxappRefuser;
    }

    public TOwnerTemp setWxappRefuser( String wxappRefuser) {
        this.wxappRefuser = wxappRefuser;return this;
    }

    public Date getWxappRefuserTime() {
        return wxappRefuserTime;
    }

    public TOwnerTemp setWxappRefuserTime( Date wxappRefuserTime) {
        this.wxappRefuserTime = wxappRefuserTime;return this;
    }

    public String getWxappRefuserReason() {
        return wxappRefuserReason;
    }

    public TOwnerTemp setWxappRefuserReason( String wxappRefuserReason) {
        this.wxappRefuserReason = wxappRefuserReason;return this;
    }

    public String getOwnerIsValid() {
        return ownerIsValid;
    }

    public TOwnerTemp setOwnerIsValid( String ownerIsValid) {
        this.ownerIsValid = ownerIsValid;return this;
    }

    public Integer getIsVerify() {
        return isVerify;
    }

    public TOwnerTemp setIsVerify( Integer isVerify) {
        this.isVerify = isVerify;return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TOwnerTemp{" +
        "id=" + id +
        ", shopId=" + shopId +
        ", shopSecret=" + shopSecret +
        ", appId=" + appId +
        ", appSecret=" + appSecret +
        ", appName=" + appName +
        ", appQr=" + appQr +
        ", ownerId=" + ownerId +
        ", acctName=" + acctName +
        ", acctPwd=" + acctPwd +
        ", companyName=" + companyName +
        ", companyLicense=" + companyLicense +
        ", companyCode=" + companyCode +
        ", name=" + name +
        ", phone=" + phone +
        ", mailbox=" + mailbox +
        ", wxApiCard=" + wxApiCard +
        ", msgTmplate=" + msgTmplate +
        ", createdTime=" + createTime +
        ", creater=" + creater +
        ", ownerDataStatus=" + ownerDataStatus +
        ", wxappDataStatus=" + wxappDataStatus +
        ", ownerDataAuditor=" + ownerDataAuditor +
        ", ownerDataTime=" + ownerDataTime +
        ", wxappDataAuditor=" + wxappDataAuditor +
        ", wxappDataTime=" + wxappDataTime +
        ", ownerRefuser=" + ownerRefuser +
        ", ownerRefuserTime=" + ownerRefuserTime +
        ", ownerRefuserReason=" + ownerRefuserReason +
        ", wxappRefuser=" + wxappRefuser +
        ", wxappRefuserTime=" + wxappRefuserTime +
        ", wxappRefuserReason=" + wxappRefuserReason +
        ", ownerIsValid=" + ownerIsValid +
        ", isVerify=" + isVerify +
        "}";
    }
}