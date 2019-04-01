package com.soft.ware.rest.modular.auth.controller.dto;

public class SessionOwnerUser extends SessionOwner {

    /**
     * 用户名/手机号
     */
    private String username;

    public SessionOwnerUser(String owner) {
        super(owner);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}