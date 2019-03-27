package com.soft.ware.rest.modular.auth.controller.dto;

import java.util.List;

/**
 * 极光im群组模板类
 */
public class ImGroup {

    private Integer gid;
    private String owner_username;
    private String name;
    private int max_member_count;
    private int flag;
    private List<String> members_username;

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getOwner_username() {
        return owner_username;
    }

    public void setOwner_username(String owner_username) {
        this.owner_username = owner_username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMax_member_count() {
        return max_member_count;
    }

    public void setMax_member_count(int max_member_count) {
        this.max_member_count = max_member_count;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<String> getMembers_username() {
        return members_username;
    }

    public void setMembers_username(List<String> members_username) {
        this.members_username = members_username;
    }
}
