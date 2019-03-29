package com.soft.ware.rest.modular.auth.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.warpper.FailWrapper;
import com.soft.ware.core.base.warpper.SuccessWrapper;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderUpdateStatusParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.TblOrderService;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.validator.Validator;
import com.soft.ware.rest.modular.auth.wrapper.OrderBackParam;
import com.soft.ware.rest.modular.auth.wrapper.OrderPageWrapper;
import com.soft.ware.rest.modular.auth.wrapper.OrderWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * @param param  confirm：新订单 confirmed:备货 delivering:送/取货 done:已完成 cancel:已取消
     * @return
     */
    @RequestMapping(value = "/user/orders")
    public Object orderPage(SessionUser user, Page page,  OrderPageParam param){
        if (param.getStatus()!=null) {
            if ("confirm".equals(param.getStatus())) {
                //新订单
                param.setStatus("1");
            } else if("confirmed".equals(param.getStatus())) {
                //备货
                param.setStatus("10");
            } else if ("delivering".equals(param.getStatus())) {
                //送/取货
                param.setStatus("2");
            } else if ("done".equals(param.getStatus())) {
                //已完成
                param.setStatus("3");
            } else if("cancel".equals(param.getStatus())){
                //已取消
                param.setStatus("-1");
            }
        }
        List<Map> list = orderService.findPage(user, page, param, TblOrder.SOURCE_1);
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
    public Object order(@PathVariable String no,SessionUser user){
        HashMap<Object, Object> map = new HashMap<>();
        TblOrder order = orderService.findByNo(user, no);
        map.put("code", SUCCESS);
        map.put("order", order);
        return super.warpObject(new OrderWrapper(map));
    }


    /**
     * 更新订单状态
     * @return
     */
    @RequestMapping(value = "/user/order/maintain",method = RequestMethod.POST)
    public Object updateStatus(SessionUser user, OrderUpdateStatusParam param, BindingResult error){
        Validator.valid(error);
        boolean result = orderService.updateStatus(user,param.getNo(),param.getStatus());
        return result ? warpObject(new SuccessWrapper()) : warpObject(new FailWrapper());
    }

    /**
     * 订单退款
     * @return
     */
    @RequestMapping(value = "/user/order/refund",method = RequestMethod.POST)
    public Object orderBack(SessionUser user, OrderBackParam param){
        return null;//
    }



}
