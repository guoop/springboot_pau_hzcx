package com.soft.ware.rest.modular.auth.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.OrderUpdateStatusParam;
import com.soft.ware.rest.modular.auth.service.TblOrderService;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.validator.Validator;
import com.soft.ware.rest.modular.auth.wrapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController extends BaseController {


    @Autowired
    private TblOrderService orderService;


    /**
     *
     * 订单状态（-3：已删除；-2：过期失效；-1：手动取消；0：待付款（新建订单）；1：待商家确认（在线支付支付成功、货到付款下单成功）；
     *         2：配送中；3：已完成（在线支付配送完成；货到付款付款功能）；10：商家确认接单（主要用于配送前及时提醒买家商家已接单））
     *
     * 订单列表
     *
     * @param user 当前登录用户
     * @param page 分页信息
     * @param status  confirm：新订单 confirmed:备货 delivering:送/取货 done:已完成 cancel:已取消
     * @return
     */
    @RequestMapping(value = "/user/orders")
    public Object orderPage(TblOwnerStaff user, Page page, @RequestParam(required = false) String status){
        String s = null;
        if (status != null) {
            if ("confirm".equals(status)) {
                //新订单
                s = "1";
            } else if("confirmed".equals(status)) {
                //备货
            } else if ("delivering".equals(status)) {
                //送/取货
                s = "2";
            } else if ("done".equals(status)) {
                //已完成
                s = "3";
            } else if("cancel".equals(status)){
                //已取消
                s = "-1";
            }
        }
        List<Map> list = orderService.findPage(user, page, s);
        Map<String, Object> map = new HashMap<>();
        map.put("code", SUCCESS);
        map.put("orders",list);
        return super.warpObject(new OrderPageWrapper(map));
    }


    /**
     * 订单详情
     * @param no
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/order/{no}",method = RequestMethod.GET)
    public Object order(@PathVariable String no,TblOwnerStaff user){
        HashMap<Object, Object> map = new HashMap<>();
        Map<String, Object> order = orderService.findByNo(user, no);
        map.put("code", SUCCESS);
        map.put("order", order);
        return super.warpObject(new OrderWrapper(map));
    }


    /**
     * 更新订单状态
     * @return
     */
    @RequestMapping(value = "/user/order/maintain",method = RequestMethod.POST)
    public Object updateStatus(TblOwnerStaff user, OrderUpdateStatusParam param, BindingResult error){
        Validator.valid(error);
        boolean result = orderService.updateStatus(user,param.getNo(),param.getStatus());
        return result ? warpObject(new SuccessWrapper()) : warpObject(new FailWrapper());
    }

    /**
     * 订单退款
     * @return
     */
    @RequestMapping(value = "/user/order/refund",method = RequestMethod.POST)
    public Object orderBack(TblOwnerStaff user, OrderBackParam param){
        return null;//
    }



}