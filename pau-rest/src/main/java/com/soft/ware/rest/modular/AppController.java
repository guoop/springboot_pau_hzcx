package com.soft.ware.rest.modular;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.common.persistence.model.TblAppBase;
import com.soft.ware.rest.modular.app_version.model.TAppVersion;
import com.soft.ware.rest.modular.app_version.service.ITAppVersionService;
import com.soft.ware.rest.modular.auth.controller.dto.AddOrderParam;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.service.ITOrderService;
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
    private ITOrderService orderService;

    @Autowired
    private ITAppVersionService appVersionService;



    /**
     * 接口："https://app.aiinp.com/user/category/list";
     * 参数：
     * owner（登录返回的owner），
     * Authorization（格式为：Bearer + 一个空格 + token）
     * @return
     */
    @RequestMapping(value = "category/list")
    public Object goods(SessionUser user){
        List<TCategory> list = categoryService.findAllCategory(user);
        return render().set("data", list);
    }


    @RequestMapping(value = "/user/goods")
    public Object userGoods(SessionUser user, Page page, GoodsPageParam param){
        Kv.obj("user", user);
        //List<Map> list = goodsService.findMaps(user, page, param);
        List<Map> list = null;
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
    public Object addOrder(SessionUser user, AddOrderParam param){
        TOrder order = orderService.createOrder(user,param);
        return render();
    }


    @RequestMapping(value = "/version/check",method = RequestMethod.GET)
    public Object appVersion() throws Exception {
        TAppVersion v = appVersionService.findLast(TblAppBase.PLATFORM_CODE_APP_ANDROID);
        Kv<String, String> kv = Kv.by("download_url", v.getDownloadUrl()).set("description", v.getDescription()).set("version", v.getVersion());
        return render().set("force", v.getIsForce()).set("newVersion", kv);
    }



    /**
     * 极光im初始化
     */
    @RequestMapping("/im/init")
    public Object getPayLoad(HttpServletResponse response){
        //todo yancc 是否删除
        response.setHeader("Access-Control-Allow-Origin", "*");
        return render().setAll(WXUtils.getPayLoad());
    }


}
