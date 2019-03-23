package com.soft.ware.rest.modular.wxsmall;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.rest.common.persistence.model.*;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderDeleteParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.*;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.wrapper.SuccessWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private TblAddressService addressService;

    @Autowired
    private TblQuestionService questionService;

    /**
     * banner
     * @param user
     * @return
     */
    @RequestMapping(value = "/customer/v1/banner/list")
    public Object banners(SessionUser user){
        List<TblBanner> list = bannerService.findBannerByOwner(user.getOwner());
        Map<String, Object> map = new HashMap<>();
        map.put("code", SUCCESS);
        return list;
    }


    /**
     * 商品分类列表
     * @param user
     * @return
     */
    @RequestMapping(value = "/customer/v1/category/list")
    public Object category(SessionUser user){
        List<TblCategory> list =  categoryService.findAllCategory(user);
        return list;
    }


    /**
     * 商品列表
     * @param param
     * @param user
     * @param page
     * @return
     */
    @RequestMapping(value = "/customer/v1/goods/list")
    public Object goodsPage(GoodsPageParam param,SessionUser user, Page page){
        List<Map> list = goodsService.findPage(user, page, param);
        return list;
    }

    /**
     * 商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/customer/v1/goods/{id}")
    public Object goods(@PathVariable Long id){
       TblGoods goods = goodsService.findById(id);
        List<TblGoods> list = new ArrayList<>();
        list.add(goods);
       return list;
    }


    /**
     * 商户信息查询
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/customer/v1/shop")
    public Object owner(SessionUser user) throws Exception {
        TblOwner o = ownerService.find(user);
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


    /**
     * 订单列表查询
     * @param user
     * @param page
     * @param param
     * @return
     */
    @RequestMapping(value = "/customer/v2/orders")
    public Object orders(SessionUser user,Page page, OrderParam param){
        // 所有订单
        if ("all".equals(param.getStatus())) {
            param.setStatus("-2, -1, 0, 1, 2, 3, 10");
        } else if ("pay".equals(param.getStatus())) {
            // 待付款
            param.setStatus("0");
        } else if ("receive".equals(param.getStatus())) {
            // 待收货
            param.setStatus("1, 2, 10");
        } else if ("done".equals(param.getStatus())) {
            // 已完成
            param.setStatus("3");
        }else if ("cancel".equals(param.getStatus())) {
            // 已取消
            param.setStatus("-1, -2");
        }
        List<Map> list = orderService.findPage(user,page,param,TblOrder.SOURCE_0);
        return list;
    }

    /**
     * 买家删除订单
     * @param user
     * @param param
     * @return
     */
    @RequestMapping(value = "/customer/v1/order/delete",method = RequestMethod.POST)
    public Object deleteOrder(SessionUser user,@RequestBody OrderDeleteParam param){
        orderService.customerDelete(user,param);
        return warpObject(new SuccessWrapper());
    }

    /**
     * 买家取消订单
     * @param user
     * @param param
     * @return
     */
    @RequestMapping(value = "/customer/v1/order/cancel",method = RequestMethod.POST)
    public Object cancelOrder(SessionUser user,@RequestBody OrderDeleteParam param){
        orderService.customerCancel(user,param);
        return warpObject(new SuccessWrapper());
    }

    /**
     * 收货地址列表
     * @param user
     * @return
     */
    @RequestMapping(value = "/customer/v1/address")
    public Object address(SessionUser user) throws Exception {
        List<TblAddress> all = addressService.findAll(user);
        List<Map<String, Object>> maps = BeanMapUtils.toMap(true, all);
        return maps;
    }

    /**
     * 收货地址详情
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "/customer/v1/address/{id}",method = RequestMethod.GET)
    public Object address(SessionUser user,@PathVariable int id){
        TblAddress address = addressService.findById(user, id);
        return address;
    }


    /**
     * 添加/编辑 用户地址
     * @param user
     * @param address
     * @return
     */
    @RequestMapping(value = "/customer/v1/address/man",method = RequestMethod.POST)
    public Object address(SessionUser user,@RequestBody TblAddress address){
        if (address.getId() == null) {
            address.setOwner(user.getId());
            address.setCreatedAt(new Date());
            addressService.addAddress(user,address);
        }else{
            TblAddress old = addressService.findById(user, address.getId());
            old.setName(address.getName());
            old.setProvince(address.getProvince());
            old.setDetail(address.getDetail());
            old.setTelephone(address.getTelephone());
            old.setIsDefault(address.getIsDefault());
            addressService.updateAddress(user,address);
        }
        return warpObject(new SuccessWrapper());
    }


    /**
     * 意见反馈
     * @param user
     * @param question
     */
    @RequestMapping(value = "/customer/v1/question",method = RequestMethod.POST)
    public Object question(SessionUser user,@RequestBody TblQuestion question){
        question.setOpenId(user.getId());
        question.setOwner(user.getOwner());
        question.setCreatedAt(new Date());
        questionService.add(question);
        return warpObject(new SuccessWrapper());
    }

}
