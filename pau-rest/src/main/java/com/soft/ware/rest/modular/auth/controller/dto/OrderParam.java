package com.soft.ware.rest.modular.auth.controller.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * 订单列表查询参数
 */
public class OrderParam {

    /**
     * 订单状态
     */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getStatusArray(){
        if (StringUtils.isNotBlank(status)) {
            return status.split(",");
        }else{
            return new String[]{Integer.MAX_VALUE+""};
        }
    }
}
