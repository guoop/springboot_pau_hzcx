package com.soft.ware.rest.modular.auth.controller.dto;

public class SessionUser {

    private String id;
    private String username;
    private String owner;

    private String type;

    public static String type_customer = "type_customer";
    public static String type_staff = "type_staff";//
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
