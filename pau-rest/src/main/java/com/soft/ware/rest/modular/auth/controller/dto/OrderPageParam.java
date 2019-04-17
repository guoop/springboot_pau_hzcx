package com.soft.ware.rest.modular.auth.controller.dto;

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
     * 是否
     */
    private String flag;

    /**
     * 自定义sql,买家端用到了
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
