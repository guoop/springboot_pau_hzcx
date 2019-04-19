package com.soft.ware.rest.modular;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.*;
import com.soft.ware.rest.modular.auth.util.JwtTokenUtil;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import com.soft.ware.rest.modular.handover.model.THandoverRecord;
import com.soft.ware.rest.modular.handover.service.ITHandoverRecordService;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order_app.service.ITOrderAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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



    /**
     * 接口："https://app.aiinp.com/user/category/list";
     * 参数：
     * owner（登录返回的owner），
     * Authorization（格式为：Bearer + 一个空格 + token）
     * @return
     */
    @RequestMapping(value = "category/list")
    public Object goods(SessionUser user){
        List<TCategory> list = categoryService.selectParentCategoryList(Kv.obj().set("owner_id", user.getOwnerId()));
        return render().set("data", list);
    }


    @RequestMapping(value = "goods")
    public Object userGoods(SessionUser user, Page page, GoodsPageParam param){
        List<Map> list = goodsService.findPage(user, page, param);
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
    @RequestMapping(value = "order",method = RequestMethod.POST)
    public Object addOrder(SessionUser user, AddOrderParam param){
        orderAppService.addOrder(user,param);
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
     * 订单列表
     * @return
     */
    @RequestMapping(value = "orders",method = RequestMethod.GET)
    public Object orders(SessionUser user, Page page, OrderPageParam param){
        Kv<String, Object> map = Kv.obj("page", page).set("ownerId", user.getOwnerId());
        List<Map<String, Object>> maps = orderAppService.findMapPage(user, page, param, TOrder.SOURCE_1);
        return render();
    }


    /**
     * 交换token
     * @param user
     */
    @RequestMapping(value = "token",method = RequestMethod.GET)
    public void updateToken(SessionUser user){
        render().set("token", jwtTokenUtil.generateToken(user.getPhone(), jwtTokenUtil.getRandomKey()));
    }



}
