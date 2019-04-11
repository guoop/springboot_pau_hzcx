package com.soft.ware.rest.modular.goods.controller.dto;

import com.google.common.collect.Lists;

import java.util.List;

public class CartParam {

    //下单有用，购物车没用
    private String formID;
    private String flag;
    private List<Goods> goods;

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public List<String> getIds(){
        List<String> ids = Lists.newArrayList();
        for (Goods g : goods) {
            ids.add(g.getId());
        }
        return ids;
    }

    public List<Integer> getNums(){
        List<Integer> nums = Lists.newArrayList();
        for (Goods g : goods) {
            nums.add(g.getNum());
        }
        return nums;
    }


    public List<String> getUnits(){
        List<String> units = Lists.newArrayList();
        for (Goods g : goods) {
            units.add(g.getUnit());
        }
        return units;
    }

    public static class Goods{

        private String id;
        private Integer num;
        private String unit;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
