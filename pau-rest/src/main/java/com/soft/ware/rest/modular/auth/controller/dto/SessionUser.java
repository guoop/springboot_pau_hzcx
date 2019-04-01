package com.soft.ware.rest.modular.auth.controller.dto;

/**
 * session 用户信息
 */
public class SessionUser extends SessionOwnerUser {

    /**
     * openId
     */
    private String openId;
    /**
     * appId，小程序客户端才有
     */
    private String appId;

    public SessionUser(String owner) {
        super(owner);
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

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
