package com.soft.ware.rest.modular.auth.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.rest.common.persistence.model.TblAppBase;
import com.soft.ware.rest.common.persistence.model.TblAppVersion;
import com.soft.ware.rest.common.persistence.model.TblCategory;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.modular.auth.controller.dto.AddOrderParam;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.TblAppVersionService;
import com.soft.ware.rest.modular.auth.service.TblCategoryService;
import com.soft.ware.rest.modular.auth.service.TblGoodsService;
import com.soft.ware.rest.modular.auth.service.TblOrderService;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.wrapper.AppVersionWrapper;
import com.soft.ware.rest.modular.auth.wrapper.CategoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AppController extends BaseController {

    @Autowired
    private TblCategoryService categoryService;

    @Autowired
    private TblGoodsService goodsService;

    @Autowired
    private TblOrderService tblOrderService;

    @Autowired
    private TblAppVersionService appVersionService;


    /**
     * 接口："https://app.aiinp.com/user/category/list";
     * 参数：
     * owner（登录返回的owner），
     * Authorization（格式为：Bearer + 一个空格 + token）
     * @return
     */
    @RequestMapping(value = "/user/category/list")
    public Object goods(SessionUser user){
        List<TblCategory> list = categoryService.findAllCategory(user);
        Map<String, Object> map = new HashMap<>();
        map.put("code",SUCCESS);
        map.put("data", list);
        return super.warpObject(new CategoryWrapper(map));

    }


    @RequestMapping(value = "/user/goods")
    public Object userGoods(SessionUser user, Page page, GoodsPageParam param){
        List<Map> list = goodsService.findPage(user, page, param);
        page.setRecords(list);
        //todo  yancc 前端还不支持分页
        return list;
    }


    /**
     *
     * 4、上传订单(POST)
     * 接口：https://app.aiinp.com/user/order
     * 参数：
     *
     * owner，
     * no（订单编号），
     * money_channel（支付方式：1现金、2微信、3支付宝、4银联，多种支付方式以_分割），
     * money（订单金额，单位元，精确到分），
     * source（订单来源：0：android收银机、1：windows收银机）
     * money_dpay(订单优惠后金额)
     * money_shishou(实收)
     * money_zhaol(找零)
     * channel_pay(支付明细  格式(支付方式_支付金钱，支付方式_支付金钱))
     * pay_at(支付时间)
     * status(订单状态  0 为正常  1 ：退单  2 ：反结账)
     * settlement_by（结算人标识（收银app登录账号）），
     * goods（订单包含的商品清单）,
     * Authorization（格式为：Bearer + 一个空格 + token）
     * goods拼接方式：1801__https://mp-owner-1251363375.cos.ap-guangzhou.myqcloud.com/1536655293256.jpg__馒头（每人限5袋）__-1__1__1.50__1.50
     * @return
     */
    @RequestMapping(value = "/user/order",method = RequestMethod.POST)
    public Object addOrder(SessionUser user,AddOrderParam param){
        TblOrder order = tblOrderService.createOrder(user,param);
        return render();
    }


    @RequestMapping(value = "/version/check",method = RequestMethod.GET)
    public Object appVersion(){
        TblAppVersion v = appVersionService.findLast(TblAppBase.PLATFORM_CODE_APP_ANDROID);
        Map<String, Object> map = new HashMap<>();
        map.put("code", SUCCESS);
        map.put("force", v.getIsForce());
        Map<String, Object> newVersion = new HashMap<String,Object>();
        newVersion.put("download_url", v.getDownloadUrl());
        newVersion.put("description", v.getDescription());
        newVersion.put("version", v.getVersion());
        map.put("newVersion", newVersion);
        return super.warpObject(new AppVersionWrapper(map));
    }




}
