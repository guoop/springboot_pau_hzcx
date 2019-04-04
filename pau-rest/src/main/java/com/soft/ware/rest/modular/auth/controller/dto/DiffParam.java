package com.soft.ware.rest.modular.auth.controller.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 订单补差价接口参数
 */
public class DiffParam {

    //差价订单号
    @NotBlank
    private String diffNO;

    public String getDiffNO() {
        return diffNO;
    }

    public void setDiffNO(String diffNO) {
        this.diffNO = diffNO;
    }
}
