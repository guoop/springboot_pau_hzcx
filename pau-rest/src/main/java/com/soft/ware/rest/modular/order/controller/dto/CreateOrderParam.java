package com.soft.ware.rest.modular.order.controller.dto;

import com.google.common.collect.Lists;
import com.soft.ware.rest.modular.goods.controller.dto.CartParam;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

/**
 * 下单参数
 */
public class CreateOrderParam extends CartParam {

    @NotBlank
    private String addressId;

    //这个值不是前端传过来的
    private BigDecimal total;

    private List<BigDecimal> totals = Lists.newArrayList();

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public List<BigDecimal> getTotals() {
        return totals;
    }

    public void setTotal(BigDecimal total,int i) {
        this.totals.add(i,total);
    }
}
