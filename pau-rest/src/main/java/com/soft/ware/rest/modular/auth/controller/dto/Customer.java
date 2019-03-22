package com.soft.ware.rest.modular.auth.controller.dto;

public class Customer {

    private String openId;
    private String owner;


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
