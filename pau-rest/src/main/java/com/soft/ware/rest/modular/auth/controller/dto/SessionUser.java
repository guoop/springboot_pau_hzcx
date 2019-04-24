package com.soft.ware.rest.modular.auth.controller.dto;

/**
 * session 用户信息
 */
public class SessionUser  {

    /**
     * 用戶Id
     */
    private String  Id;
    /**
     * 用戶名称  在买家端是nickName
     */
    private String name;
    /**
     * 登录手机号
     */
    private String phone;
    /**
     * 买家端登录的openId
     */
    private String openId;
    /**
     * appId，小程序客户端才有
     */
    private String appId;
    /**
     * 商戶唯一id
     */
    private String ownerId;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public SessionUser setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getOpenId(){
        return openId;
    }

    public SessionUser setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public SessionUser() {
    }

    /**
     * 构造appid
     * @param appId
     */
    public SessionUser setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    /**
     * 构造商户唯一id
     * @param ownerId
     */
    public SessionUser(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * 构造openId和OwnerId
     */
    public SessionUser(String ownerId,String openId){
        this.ownerId = ownerId;
        this.openId = openId;

    }

    /**
     * 构造用户昵称，登录手机号，用户openId,appId,ownerId
     * @param name
     * @param phone
     * @param openId
     * @param appId
     * @param ownerId
     */
    public SessionUser(String name, String phone, String openId, String appId, String ownerId) {
        this.name = name;
        this.phone = phone;
        this.openId = openId;
        this.appId = appId;
        this.ownerId = ownerId;
    }
}
