package com.soft.ware.rest.modular.auth.controller.dto;

public class SessionOwner {

    /**
     * 用户id/openId
     */
    private String id;

    /**
     * 商家唯一标识
     */
    private String owner;


    public SessionOwner(String owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }



}
