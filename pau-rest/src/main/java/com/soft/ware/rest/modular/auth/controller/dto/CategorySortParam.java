package com.soft.ware.rest.modular.auth.controller.dto;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 修改分类排序接口参数
 */
public class CategorySortParam {


    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Sort> getList(){
        try {
            List<Sort> list = JSON.parseArray(category, Sort.class);
            for (int i = list.size()-1; i > 0; i--) {
                list.get(i).setWeights(i);
            }
            return list;
        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    public static class Sort{

        private String id;
        private int status;
        private int weights;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getWeights() {
            return weights;
        }

        public void setWeights(int weights) {
            this.weights = weights;
        }
    }


}
