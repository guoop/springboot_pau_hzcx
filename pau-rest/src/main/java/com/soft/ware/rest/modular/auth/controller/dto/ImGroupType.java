package com.soft.ware.rest.modular.auth.controller.dto;

public enum  ImGroupType {

    STAFF("员工群");

    private String desc;

    ImGroupType(String desc) {
        this.desc = desc;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
