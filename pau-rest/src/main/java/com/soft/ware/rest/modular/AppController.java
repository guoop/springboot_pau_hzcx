package com.soft.ware.rest.modular;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.*;
import com.soft.ware.rest.modular.auth.util.JwtTokenUtil;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import com.soft.ware.rest.modular.auth.validator.Validator;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import com.soft.ware.rest.modular.handover.model.THandoverRecord;
import com.soft.ware.rest.modular.handover.service.ITHandoverRecordService;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import com.soft.ware.rest.modular.order_app.service.ITOrderAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class AppController extends BaseController {


    @Autowired
    private ITCategoryService categoryService;

    @Autowired
    private ITGoodsService goodsService;

    @Autowired
    private ITHandoverRecordService handoverRecordService;

    @Autowired
    private ITOrderAppService orderAppService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ITOrderService orderService;



    /**
     * 接口："https://app.aiinp.com/user/category/list";
     * 参数：
     * owner（登录返回的owner），
     * Authorization（格式为：Bearer + 一个空格 + token）
     * @return
     */
    @RequestMapping(value = "category/list")
    public Object goods(SessionUser user){
        List<TCategory> list = categoryService.selectParentCategoryList(Kv.obj().set("ownerId", user.getOwnerId()));
        return render().set("data", list);
    }


    @RequestMapping(value = "goods")
    public Object userGoods(SessionUser user, Page page, GoodsPageParam param){
        return goodsService.findPage(user, page, param);
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
    @RequestMapping(value = "order",method = RequestMethod.POST)
    public Object addOrder(SessionUser user, AddOrderParam param){
        orderAppService.addOrder(user, param);
        return render();
    }





    /**
     * 极光im初始化
     * windows 需要
     */
    @RequestMapping("im/init")
    public Object getPayLoad(HttpServletResponse response){
        //todo yancc 是否删除
        response.setHeader("Access-Control-Allow-Origin", "*");
        return render().setAll(WXUtils.getPayLoad());
    }


    /**
     * 交接班,记录
     * @return
     */
    @RequestMapping(value = "handover",method = RequestMethod.POST)
    public Object handover(SessionUser user, HandoverParam param){
		THandoverRecord over = handoverRecordService.over(user, param);
		return render(over != null);
    }


    /**
     * 订单列表(线上)
     * @return
     */
    @RequestMapping(value = "orders",method = RequestMethod.GET)
    public Object orders(SessionUser user, Page page, OrderPageParam param){
        if ("confirm".equals(param.getStatus())) {
            // 新订单（待商家确认）
            param.setStatus("1");
        } else if ("confirmed".equals(param.getStatus())) {
            // 备货中（商家已确认）
            param.setStatus("10");
        } else if ("delivering".equals(param.getStatus())) {
            // 配送中（包含自提订单）
            param.setStatus("2");
        } else if ("done".equals(param.getStatus())) {
            // 已完成
            param.setStatus("3");
        } else if ("cancel".equals(param.getStatus())) {
            // 已取消
            param.setStatus("-1");
        } else {
            param.setStatus("");
        }
        List<Map<String, Object>> maps = orderService.findPage(user, page, param, TOrder.SOURCE_2, TOrder.SOURCE_0);
        return render().set("orders", maps);
    }

    /**
     * 查询订单详情（线上订单）
     * @param user
     * @param no
     * @return
     */
    @RequestMapping(value = "orders/{no}",method = RequestMethod.GET)
    public Object orders(SessionUser user,@PathVariable String no){
        Kv<String, Object> params = Kv.obj("ownerId", user.getOwnerId()).set("orderNo", no);
        Map<String, Object> order = orderService.findMap(params);
        return render().set("order", order);
    }

    /**
     * 更新订单状态
     * @return
     */
    @RequestMapping(value = "order/maintain",method = RequestMethod.POST)
    public Object updateStatus(SessionUser user, @Valid OrderUpdateStatusParam param, BindingResult error){
        Validator.valid(error);
        Map<String,Object> params = new HashMap<>();
        params.put("orderNo",param.getOrderNo());
        params.put("status",param.getStatus());
        if(orderService.orderSignStatu(user,params)){
            return new SuccessTip();
        }
        return new ErrorTip();
    }

    /**
     * 交换token
     * @param user
     */
    @RequestMapping(value = "token",method = RequestMethod.GET)
    public void updateToken(SessionUser user){
        render().set("token", jwtTokenUtil.generateToken(user.getPhone(), jwtTokenUtil.getRandomKey()));
    }

    /**
     * 订单退款
     */
    @RequestMapping("order/refund")
    public Object orderRefund(@RequestParam Map<String,Object> param, SessionUser sessionUser) throws WxPayException {
        param.put("owner_id",sessionUser.getOwnerId());
            if(orderService.orderRefund(param,sessionUser)){
                return new SuccessTip();
            }
        return new ErrorTip();
    }



}
