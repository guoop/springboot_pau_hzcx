package com.soft.ware.rest.modular.auth.controller.dto;

/**
 * session 用户信息
 */
public class SessionUser {

    /**
     * 用户id/openId
     */
    private String id;
    /**
     * 用户名/手机号
     */
    private String username;
    /**
     * 商家唯一标识
     */
    private String owner;

    /**
     * 用户类型
     */
    private String type;

    public static String type_customer = "type_customer";//买家端
    public static String type_staff = "type_app";//收银端
    public static String type_owner = "type_owner";//商家

    public SessionUser(String type,String owner) {
        this.type = type;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId(){
        return id;
    }

    public SessionUser setOpenId(String openId) {
        this.id = openId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
