package com.soft.ware.rest.modular.order.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.ParamUtils;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.model.TOrderChild;
import com.soft.ware.rest.modular.order.service.ITOrderChildService;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import com.soft.ware.rest.modular.order_app.model.TOrderApp;
import com.soft.ware.rest.modular.order_app.service.TOrderAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/owner/v1")
public class TOrderController extends BaseController {

    @Autowired
    private ITOrderService tOrderService;
    /**
     * 子订单服务
     */
    @Autowired
    private ITOrderChildService itOrderChildService;
    /**
     * 线下订单
     */
    @Autowired
    private TOrderAppService tOrderAppService;

    /**
     * 通过订单状态查询订单列表
     * @param param (订单状态）,page（页码）
     * @return owner/v2/order 修改 owner/v1/orders/list
     */
    @RequestMapping(value = "/orders/list",method = RequestMethod.GET)
    @Transient
    public Tip getOrderList( @RequestParam Map<String, Object> param,SessionUser sessionUser) {
        param.put("owner_id", sessionUser.getOwnerId());
        param.put("status",ParamUtils.getOrderStatus(param.get("status").toString()));
        param.put("page",Integer.valueOf(param.get("page").toString()));
        //List<TOrder> list = tOrderService.selectOrderListByMap(param);
        List<HashMap<String,Object>> listMap = tOrderService.selectOrdersListByMap(param);
        if (listMap.size() > 0) {
            return new SuccessTip(listMap);
        }
        return new ErrorTip();
    }

    /**
     * 订单详情
     * @param orderNo
     * @return
     */
    @RequestMapping("order/detail")
    public Tip orderDetail(String orderNo){
        TOrder tOrder = tOrderService.selectOrderDetailById(orderNo);
        if(ToolUtil.isNotEmpty(tOrder)){
            return new SuccessTip(tOrder);
        }
        return new ErrorTip();
    }

    /**
     * @param param orderNo 订单编号.orderId ,page 当前页
     * @param sessionUser
     * @return
     */
    @RequestMapping("orders/child-order-list")
    public Tip getChildList(@RequestParam Map<String,Object> param ,SessionUser sessionUser){
        param.put("owner_id",sessionUser.getOwnerId());
        List<TOrderChild> list = itOrderChildService.selectOrderChildListByMap(param);
        if(list.size() > 0){
            return new SuccessTip(list);
        }
        return new ErrorTip();
    }
    /**
     * 获取商家待发货订单总数
     * @param user
     * @return
     */
    @RequestMapping(value = "/orders/count")
    public Tip orderCount(SessionUser user){
        int orderCount = tOrderService.selectOrderCount(user.getOwnerId());
        if(orderCount == 0 || orderCount > 0){
            return new SuccessTip(orderCount);
        }
       return new ErrorTip();
    }

    /**
     * 获取线下订单列表
     * @param param page 页码
     * @param sessionUser 当前商户
     * @return
     */
    @RequestMapping(value = "orders/app-list")
    public Tip getAppOrdersList(@RequestParam Map<String,Object> param,SessionUser sessionUser){
        param.put("owner_id",sessionUser.getOwnerId());
        param.put("page",Integer.valueOf(param.get("page").toString()));
        List<TOrderApp> orderAppList = tOrderAppService.getAppOrderList(param);
        if(orderAppList.size() > 0){
            return new SuccessTip(orderAppList);
        }
        return new ErrorTip();
    }

    /**
     * 标记订单状态
     * @param param orderNo订单编号 ，status订单状态， reason原因
     * @param sessionUser 当前登录用户
     * @return 通用提示
     * @// TODO: 2019/4/11  paulo 暂时往后放
     */
    @RequestMapping(value = "orders/sign-status")
    public Tip signStatus(@RequestParam Map<String,Object> param,SessionUser sessionUser){
         param.put("status",ParamUtils.getOrderStatus(param.get("status").toString()));
         param.get("orderNo").toString();
         param.put("owner_id",sessionUser.getOwnerId());

        return  new ErrorTip();

    }

    /**
     * 订单退款
     * @param param orderNo 订单编号
     * @param sessionUser  当前登录用户
     * @// TODO: 2019/4/11 paulo 暂时往后放
     */
    @RequestMapping(value = "orders/refund")
    public Tip ordersRefund(@RequestParam Map<String,Object> param,SessionUser sessionUser){
        param.put("status",ParamUtils.getOrderStatus(param.get("status").toString()));
        param.get("orderNo").toString();
        param.put("owner_id",sessionUser.getOwnerId());
        return new ErrorTip();
    }

    /**
     * 订单补差价退款
     * @param param orderNo 订单编号
     * @param sessionUser  当前登录用户
     * @// TODO: 2019/4/11 paulo 暂时往后放
     */
    @RequestMapping(value = "orders/money-diff-refund")
    public Tip ordersMoneyDiffRefund(@RequestParam Map<String,Object> param,SessionUser sessionUser){
        param.put("status",ParamUtils.getOrderStatus(param.get("status").toString()));
        param.get("orderNo").toString();
        param.put("owner_id",sessionUser.getOwnerId());

        return new ErrorTip();
    }




}

