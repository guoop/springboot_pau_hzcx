package com.soft.ware.rest.modular.auth.controller.dto;

/**
 * 群组类型
 */
public enum  ImGroupType {

    //owner-separator-phone
    STAFF("员工群","0"),
    //owner-separator-mac
    WIN("收银机windows","0"),
    //owner-separator-mac
    ANDROID("收银机android", "0");

    private String desc;
    /**
     * 员工命名 分割符
     */
    private String separator;

    ImGroupType(String desc,String separator) {
        this.desc = desc;
        this.separator = separator;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getSeparator() {
        return separator;
    }
}
