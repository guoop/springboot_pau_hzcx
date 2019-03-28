package com.soft.ware.rest.modular.auth.controller.dto;

import org.hibernate.validator.constraints.NotBlank;

public class Id {

    @NotBlank
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
