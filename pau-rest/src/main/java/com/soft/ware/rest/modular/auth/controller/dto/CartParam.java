package com.soft.ware.rest.modular.auth.controller.dto;

import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车/下单参数
 */
public class CartParam {

    //下单有用，购物车没用
    private String formID;

    //商品ID$$商品规格$$商品数量, 商品ID$$商品规格$$商品数量
    @NotBlank(message = "商品信息不能为空")
    private String goods;

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
        resolver();
    }


    /**
     * 这几个参数不是前端传来的
     */
    private List<String> ids = Lists.newArrayList();
    private String[] units;
    private int[] nums;
    private List<BigDecimal> totals = Lists.newArrayList();

    public void resolver(){
        String[] goodsList = goods.split(",");
        String[] ss;
        units = new String[goodsList.length];
        nums = new int[goodsList.length];
        for (int i = 0; i < goodsList.length; i++) {
            ss = goodsList[i].split("\\$\\$");
            units[i] = ss[1];
            nums[i] = Integer.parseInt(ss[2]);
            ids.add(ss[0]);
        }
    }

    public List<String> getSids() {
        return new ArrayList<>();
    }

    public List<String> getIds() {
        return ids;
    }

    public String[] getUnits() {
        return units;
    }

    public int[] getNums() {
        return nums;
    }

    public List<BigDecimal> getTotals() {
        return totals;
    }

    public void setTotal(BigDecimal total,int i) {
        this.totals.add(i,total);
    }
}
