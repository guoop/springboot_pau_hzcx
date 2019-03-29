package com.soft.ware.rest.modular.auth.controller.dto;

/**
 *
 * 商品更新
 *
 */
public class GoodsUpdateParam extends Id {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
