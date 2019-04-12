package com.soft.ware.rest.modular.goods.controller.dto;

import com.google.common.collect.Lists;
import com.soft.ware.rest.modular.order.model.TOrderChild;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class CartParam {

    //下单有用，购物车没用
    private String formID;
    @NotEmpty
    private List<TOrderChild> goods;

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public List<TOrderChild> getGoods() {
        return goods;
    }

    public void setGoods(List<TOrderChild> goods) {
        this.goods = goods;
    }

    public List<String> getIds(){
        List<String> ids = Lists.newArrayList();
        for (TOrderChild g : goods) {
            ids.add(g.getGoodsId());
        }
        return ids;
    }

    public List<Integer> getNums(){
        List<Integer> nums = Lists.newArrayList();
        for (TOrderChild g : goods) {
            nums.add(g.getGoodsNum());
        }
        return nums;
    }


    public List<String> getSpecs(){
        List<String> specs = Lists.newArrayList();
        for (TOrderChild g : goods) {
            specs.add(g.getGoodsSpecId());
        }
        return specs;
    }


    public List<String> getUnits(){
        List<String> units = Lists.newArrayList();
        for (TOrderChild g : goods) {
            units.add(g.getGoodsUnitId());
        }
        return units;
    }


}
