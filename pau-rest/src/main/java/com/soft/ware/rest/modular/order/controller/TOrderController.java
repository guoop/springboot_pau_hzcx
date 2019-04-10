package com.soft.ware.rest.modular.order.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.ParamUtils;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import com.soft.ware.rest.modular.order_app.model.TOrderApp;
import com.soft.ware.rest.modular.order_app.service.TOrderAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/order/v1")
public class TOrderController extends BaseController {

    @Autowired
    private ITOrderService itOrderService;
    /**
     * 线下订单
     */
    @Autowired
    private TOrderAppService tOrderAppService;

    /**
     * 通过订单状态查询订单列表
     *
     * @param param (订单状态）,page（页码）
     * @return owner/v2/order 修改 owner/v1/orders/list
     */
    @RequestMapping("/orders/list")
    public Tip getOrderList(SessionUser user, @RequestParam Map<String, Object> param) {
        param.put("owner_id", user.getOwnerId());
        param.put("status",ParamUtils.getOrderStatus(param.get("status").toString()));
        param.put("page",Integer.valueOf(param.get("page").toString()));
        List<TOrder> list = itOrderService.selectOrderListByMap(param);
        if (list.size() > 0) {
            return new SuccessTip(list);
        }
        return new ErrorTip();
    }
    /**
     * 获取商家待发货订单总数
     * @param user
     * @return
     */
    @RequestMapping(value = "/orders/count",method = RequestMethod.GET)
    public Tip orderCount(SessionUser user){
        TOrder order = new TOrder();
        order.setOwnerId(user.getOwnerId());
        int i = itOrderService.selectCount(new EntityWrapper<>(order));
        return new SuccessTip(i);
    }

    /**
     * 获取线下订单列表
     * @param param page 页码
     * @param sessionUser 当前商户
     * @return
     */
    @RequestMapping(value = "app/orders")
    public Tip getAppOrdersList(@RequestParam Map<String,Object> param,SessionUser sessionUser){
        param.put("owner_id",sessionUser.getOwnerId());
        param.put("page",Integer.valueOf(param.get("page").toString()));
        List<TOrderApp> orderAppList = tOrderAppService.getAppOrderList(param);
        if(orderAppList.size() > 0){
            return new SuccessTip(orderAppList);
        }
        return new ErrorTip();




    }



}

