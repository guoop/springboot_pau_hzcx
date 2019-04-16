package com.soft.ware.rest.modular.order.controller.dto;

import com.google.common.collect.Lists;
import com.soft.ware.rest.modular.goods.controller.dto.CartParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * 下单参数
 */
public class CreateOrderParam extends CartParam {


    //这个值不是前端传过来的
    private List<BigDecimal> totals = Lists.newArrayList();

    public BigDecimal getTotal(int i) {
        return totals.get(i);
    }

    public List<BigDecimal> getTotals() {
        return totals;
    }

    public void setTotal(BigDecimal total,int i) {
        this.totals.add(i,total);
    }
}
