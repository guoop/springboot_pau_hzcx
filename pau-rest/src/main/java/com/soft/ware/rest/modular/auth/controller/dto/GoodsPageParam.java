package com.soft.ware.rest.modular.auth.controller.dto;

/**
 * 商品列表参数
 */
public class GoodsPageParam {


    //根据商品名搜索
    public String name;
    //根据分类搜索
    public String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
