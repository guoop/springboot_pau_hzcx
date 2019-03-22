package com.soft.ware.rest.modular.wxsmall;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.rest.common.persistence.model.TblBanner;
import com.soft.ware.rest.common.persistence.model.TblCategory;
import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.modular.auth.controller.dto.Customer;
import com.soft.ware.rest.modular.auth.controller.dto.CustomerOrderParam;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.service.*;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.wrapper.SuccessWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WXSmallCustomerController  extends BaseController {


    @Autowired
    private TblBannerService bannerService;

    @Autowired
    private TblCategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TblGoodsService goodsService;

    @Autowired
    private TblOwnerService ownerService;

    @Autowired
    private TblOrderService orderService;


    @RequestMapping(value = "/customer/v1/banner/list")
    public Object banners(Customer customer){
        List<TblBanner> list = bannerService.findBannerByOwner(customer.getOwner());
        Map<String, Object> map = new HashMap<>();
        map.put("code", SUCCESS);
        return list;
    }



    @RequestMapping(value = "/customer/v1/category/list")
    public Object category(Customer customer){
        List<TblCategory> list =  categoryService.findAllCategory(customer);
        return list;
    }


    @RequestMapping(value = "/customer/v1/goods/list")
    public Object goodsPage(GoodsPageParam param, Customer customer, Page page){
        List<Map> list = goodsService.findPage(customer, page, param);
        return list;
    }

    @RequestMapping(value = "/customer/v1/goods/{id}")
    public Object goods(@PathVariable Long id){
       TblGoods goods = goodsService.findById(id);
        List<TblGoods> list = new ArrayList<>();
        list.add(goods);
       return list;
    }


    @RequestMapping(value = "/customer/v1/shop")
    public Object owner(Customer customer) throws Exception {
        TblOwner o = ownerService.find(customer);
        Map<String, Object> map = BeanMapUtils.toMap(o, true);
        return map;
    }


    @RequestMapping(value = "/customer/v1/cart")
    public Object owner(@RequestParam String goods){
        String[] ss = goods.split("&&");
        String id = ss[0];
        String uint = ss[1];
        String num = ss[2];

        return super.warpObject(new SuccessWrapper());
    }


    @RequestMapping(value = "/customer/v2/orders")
    public Object orders(Customer customer, CustomerOrderParam param){
        List<Map> list = orderService.findPage(customer,param);
        return list;
    }




}
