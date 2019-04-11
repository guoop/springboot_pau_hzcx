package com.soft.ware.rest.modular.goods.controller;

import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.goods.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Tip getCategoryList(SessionUser sessionUser){



        return new ErrorTip();
    }




}
