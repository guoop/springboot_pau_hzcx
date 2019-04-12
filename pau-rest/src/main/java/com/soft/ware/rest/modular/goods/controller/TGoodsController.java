package com.soft.ware.rest.modular.goods.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.CategorySortParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.goods.service.*;
import com.soft.ware.rest.modular.icon.model.TIcon;
import com.soft.ware.rest.modular.icon.service.TIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 分类图标服务
     */
    private TIconService iconService;
    /**
     * 获取分类列表
     * @param sessionUser  当前登录用户
     * @return
     */
    @RequestMapping("category/list")
    public Tip getCategoryList(@RequestParam Map<String,Object> param, SessionUser sessionUser){
        param.put("owner_id",sessionUser.getOwnerId());
       List<TCategory> list = itCategoryService.selectParentCategoryList(param);
       if(list.size() > 0){
           return new SuccessTip(list);
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
     * 新增或者编辑商品分类
     * @param category 分类
     * @param sessionUser 当前登录商户
     * @return
     */
    @RequestMapping("category/addOrUpdate")
    public Tip addOrUpdate(@RequestParam TCategory category,SessionUser sessionUser){
        boolean isSuccess = false;
            if(ToolUtil.isNotEmpty(category.getId())){
                isSuccess =  itCategoryService.updateById(category);
            }else{
                isSuccess = itCategoryService.insert(category);
            }
            if(isSuccess){
                return new SuccessTip();
            }
            return  new ErrorTip();
    }

    /**
     * 通过分类id 删除分类
     * @param id
     * @return
     */
    @RequestMapping("category/del")
    public Tip DelCategory(@RequestParam String id){
        if(itCategoryService.deleteById(id)){
            return new SuccessTip();
        }
        return new ErrorTip();
    }

    /**
     * 商品列表
     * @param param page当前页，categoryId 分类
     * @param sessionUser
     * @return
     */
    @RequestMapping("goods/list")
    public Tip getGoodsList(@RequestParam Map<String,Object> param, SessionUser sessionUser){
        param.put("owner_id",sessionUser.getOwnerId());
        param.put("page",Integer.valueOf(param.get("page").toString()));
        List<TGoods>  goodsList =  itGoodsService.selectTGoodsListByMap(param);
        System.out.println(goodsList.size());
        if(goodsList.size() > 0){
            return new SuccessTip(goodsList);
        }
        return  new ErrorTip();
    }
    @RequestMapping("goods/detail")
    public Tip getGoodsDetail(@RequestParam String id){
         TGoods goods = itGoodsService.selectById(id);
         if(goods != null){
             return new SuccessTip(goods);
         }
         return new ErrorTip();
    }

    /**
     * 获取系统内置商品分类图标
     * @param sessionUser
     * @return 图标列表
     */
    @RequestMapping("icon/list")
    public Tip getIcon(SessionUser sessionUser){
        List list = iconService.selectList(new EntityWrapper<TIcon>());
        if(list.size() > 0){
            return new SuccessTip(list);
        }
        return new ErrorTip();
    }

    /**
     * 分类排序
     * @param categorySortParam id 主键,status 状态,,weights权重
     * @return
     */
     @RequestMapping("category/sort")
     public Tip categorySort(@RequestParam CategorySortParam categorySortParam){
         if(itCategoryService.updateOrSort(categorySortParam)){
             return new SuccessTip();
         };
        return new ErrorTip();
     }

    /**
     * 添加或者更新商品
     * @param goods
     * @return
     */
    @RequestMapping("goods/addOrUpdate")
    public Tip goodsEdit(@RequestParam TGoods goods){
         boolean isSuccess = false;
         if (goods.getId() != null){
             isSuccess = itGoodsService.updateById(goods);
         }else{
             isSuccess = itGoodsService.insert(goods);
         }
         if(isSuccess){
             return  new SuccessTip();
         }
         return new ErrorTip();
    }

    /**
     * 扫描添加
     * @param goods
     * @return
     */
    public Tip addByScan(@RequestParam TGoods goods){
        if(itGoodsService.insert(goods)){
            return new SuccessTip();
        }
        return new ErrorTip();
    }


}
