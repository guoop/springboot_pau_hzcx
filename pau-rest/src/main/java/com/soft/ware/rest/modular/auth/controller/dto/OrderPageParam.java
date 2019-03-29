package com.soft.ware.rest.modular.auth.controller.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * 订单列表查询参数
 */
public class OrderPageParam {

    /**
     * 订单状态
     */
    private String status;

    /**
     * 订单来源
     */
    private Integer source;

    /**
     * 自定义sql,不能用前端传过来的值//禁止前端输入
     */
    private String sql;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getSql(){
        return sql;
    }

    public void setSql(String sql){
        this.sql = sql;
    }

    public String[] getStatusArray(){
        if (StringUtils.isNotBlank(status)) {
            return status.split(",");
        }else{
            return new String[]{Integer.MAX_VALUE+""};
        }
    }
}
