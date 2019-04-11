package com.soft.ware.rest.modular.goods.controller;

import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/owner/v1")
public class TGoodsController {
    /**
     * 商品分类服务
     */
    @Autowired
    private ITCategoryService itCategoryService;
    /**
     * 商品服务
     */
    @Autowired
    private ITGoodsService itGoodsService;
    /**
     * 库存服务
     */
    @Autowired
    private ITRepositoryService itRepositoryService;

    /**
     * 规格服务
     */
    private ITSpecsService itSpecsService;

    /**
     * 单位服务
     */
    private ITUnitService itUnitService;

    /**
     * 供应商服务
     */
    private ITSupplierService itSupplierService;

    /**
     * 获取分类列表
     * @param sessionUser  当前登录用户
     * @return
     */
    @RequestMapping("category/list")
    public Tip getCategoryList(@RequestParam Map<String,Object> param, SessionUser sessionUser){
        param.put("owner_id",sessionUser.getOwnerId());
        List<TCategory> categoryParentList = itCategoryService.selectParentCategoryList(param);
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> result = new HashMap<>();
       if(categoryParentList.size()>0){
           for(int i = 0;i<categoryParentList.size();i++){
               result.put("parent",categoryParentList.get(i));
               List<TCategory> categoryChildrenList = new ArrayList<>();
               param.put("pid",categoryParentList.get(i).getId());
               List<TCategory> categoryChildList = itCategoryService.selectChildrenCategoryList(param);
               if(categoryChildList.size() > 0){
                   result.put("children",categoryChildList);
               }
               resultList.add(result);
           }
          if(resultList.size() > 0){
              return  new SuccessTip(resultList);
          }
       }
        return new ErrorTip();
    }

    /**
     * 根据分类id获取分类详情
     * @param id
     * @return
     */
    @RequestMapping(value = "category/detail")
    public Tip getCategoryDetail(@RequestParam String id){
        TCategory category = itCategoryService.selectById(id);
        if(ToolUtil.isNotEmpty(category)){
            return new SuccessTip(category);
        }
        return new ErrorTip();
    }

    /**
     * 商品列表
     * @param param page当前页，category 分类
     * @param sessionUser
     * @return
     */
    @RequestMapping("goods/list")
    public Tip getGoodsList(@RequestParam Map<String,Object> param, SessionUser sessionUser){


        return  new ErrorTip();
    }



}
