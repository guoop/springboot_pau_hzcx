package com.soft.ware.rest.modular;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.common.collect.Lists;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ResultView;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.modular.address.model.TAddress;
import com.soft.ware.rest.modular.address.service.ITAddressService;
import com.soft.ware.rest.modular.auth.controller.dto.*;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.auth.validator.Validator;
import com.soft.ware.rest.modular.banner.model.TBanner;
import com.soft.ware.rest.modular.banner.service.TBannerService;
import com.soft.ware.rest.modular.goods.controller.dto.CartParam;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import com.soft.ware.rest.modular.im.service.ImService;
import com.soft.ware.rest.modular.order.controller.dto.CreateOrderParam;
import com.soft.ware.rest.modular.order.controller.dto.PayAfterOrderParam;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_config.model.TOwnerConfig;
import com.soft.ware.rest.modular.owner_config.service.ITOwnerConfigService;
import com.soft.ware.rest.modular.question.model.TQuestion;
import com.soft.ware.rest.modular.question.service.ITQuestionService;
import com.soft.ware.rest.modular.sms.service.SmsService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(value = "买家版接口")
@RestController
@RequestMapping(value = "/customer/v1")
public class CustomerController extends BaseController {


    @Autowired
    private TBannerService bannerService;

    @Autowired
    private ITCategoryService categoryService;

    @Autowired
    private ITGoodsService goodsService;

    @Autowired
    private ITOwnerService ownerService;

    @Autowired
    private ISWxAppService appService;

    @Autowired
    private ITOrderService orderService;

    @Autowired
    private ITAddressService addressService;

    @Autowired
    private ITQuestionService questionService;

    @Autowired
    private ITOwnerConfigService ownerConfigService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private HzcxWxService hzcxWxService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private ImService imService;



    /**
     * 首页横幅
     * banner
     * @param user
     * @return
     */
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "owner", value = "user", dataType = "com.soft.ware.rest.modular.auth.controller.dto.SessionUser")})
    @ApiOperation(value = "首页横幅")
    @RequestMapping(value = "banner/list", method = RequestMethod.GET)
    public Tip banners(SessionUser user) {
        List<TBanner> list = bannerService.findBannerByOwner(user);
        return render().set("list", list);
    }


    /**
     * 商品分类列表
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "category/list", method = RequestMethod.GET)
    public Tip category(SessionUser user) {
        List<Map<String, Object>> list = categoryService.findMaps(Kv.by("pid", null).set("ownerId", user.getOwnerId()));
        return render().set("list", list);
    }


    /**
     * 商品列表
     *
     * @param param
     * @param user
     * @param page
     * @return
     */
    @RequestMapping(value = "goods/list", method = RequestMethod.GET)
    public Tip goodsPage(GoodsPageParam param, SessionUser user, Page page) {
        List<Map<String, Object>> list = goodsService.findPage(user, page, param);
        return render().set("list", list);
    }


    /**
     * 商品详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "goods/{id}", method = RequestMethod.GET)
    public Object goods(@PathVariable String id) {
        List<Map<String, Object>> list = goodsService.findMaps(Kv.by("id", id));
        return render().setOne("goods", list);
    }


    /**
     * 商品详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "goods/{id}", method = RequestMethod.GET, params = {"flag=goodsNO"})
    public Object goodsByCode(@PathVariable String id,String flag) {
        List<Map<String, Object>> list = goodsService.findMaps(Kv.obj("ownerId", id).set("code", id));
        return render().setOne("goods", list);
    }

    /**
     * 商户信息查询
     *
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "shop")
    public Object owner(SessionUser user) {
        Map<String, Object> owner = ownerService.findMap(Kv.by("id", user.getOwnerId()));
        Map<String, Object> app = appService.findMap(Kv.by("ownerId", user.getOwnerId()));
        Map<String, Object> config = ownerConfigService.findMap(Kv.by("ownerId", user.getOwnerId()));
        return render().set("owner", owner).merge("owner", app).merge("owner", config).del("owner", "app_secret");
    }


    /**
     * 订单列表查询
     *
     * @param user
     * @param page
     * @param param
     * @return
     */
    @RequestMapping(value = "orders", method = RequestMethod.GET)
    public Object orders(SessionUser user, Page page, OrderPageParam param) {
        // 所有订单
        if ("all".equals(param.getStatus())) {
            param.setStatus("-2,-1,0,1,2,3,10");
        } else if ("pay".equals(param.getStatus())) {
            // 待付款
            param.setStatus("0");
        } else if ("receive".equals(param.getStatus())) {
            // 待收货
            param.setStatus("1,2,10");
        } else if ("done".equals(param.getStatus())) {
            // 已完成
            param.setStatus("3");
        } else if ("cancel".equals(param.getStatus())) {
            // 已取消
            param.setStatus("-1,-2");
        } else {
            param.setStatus(Integer.MAX_VALUE + "");
        }
        if ("count".equals(param.getFlag())) {
            long count = orderService.findPageCount(user, param, TOrder.SOURCE_0, TOrder.SOURCE_2);
            return render().set("total", count);
        } else {
            List<Map<String, Object>> maps = orderService.findPage(user, page, param,TOrder.SOURCE_0, TOrder.SOURCE_2);
            return render(maps).set("total", page.getTotal());
        }
    }


    /**
     * 订单详情
     *
     * @param user
     * @param no
     * @return
     */
    @RequestMapping(value = "orders/{no}", method = RequestMethod.GET)
    public Tip orders(SessionUser user, @PathVariable String no) {
        Map<String, Object> map = orderService.findMap(Kv.obj("orderNo", no).set("openId", user.getOpenId()));
        if(TOrder.SOURCE_0.toString().equals(map.get("source").toString())){
            Kv<String, Object> address = Kv.toKv(addressService.findMap(Kv.obj("id", map.get("addressId")).set("ownerId", user.getOwnerId())));
            map.remove("addressId");
            map.put("address", address);
        }
        return render().set("order", map);
    }


    /**
     * 收货地址列表
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "address", method = RequestMethod.GET)
    public Object address(SessionUser user) {
        List<Map<String, Object>> list = addressService.findMaps(Kv.obj("ownerId", user.getOwnerId()).set("creater", user.getOpenId()).set("isDelete", TAddress.is_delete_0).set("orderBy", " is_default desc, create_time desc "));
        return render(list);
    }

    /**
     * 收货地址详情
     *
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "address/{id}", method = RequestMethod.GET)
    public Tip address(SessionUser user, @PathVariable String id) {
        Map<String, Object> address = addressService.findMap(Kv.obj().set("id", id).set("creater", user.getOpenId()).set("ownerId", user.getOwnerId()));
        return render().set("address", address);
    }

    /**
     * 删除收货地址
     *
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "address/del", method = RequestMethod.POST)
    public Object addressDel(SessionUser user, @RequestBody Id id, BindingResult result) throws Exception {
        Validator.valid(result);
        Map<String, Object> address = addressService.findMap(Kv.obj().set("id", id.getId()).set("creater", user.getOpenId()).set("ownerId", user.getOwnerId()));
        TAddress a = BeanMapUtils.toObject(address, TAddress.class, true);
        addressService.deleteById(user, a);
        return render();
    }


    /**
     * 添加/编辑 用户地址
     *
     * @param user
     * @param address
     * @return
     */
    @RequestMapping(value = "address/man", method = RequestMethod.POST)
    public Object address(SessionUser user, @RequestBody TAddress address) throws Exception {
        if (address.getId() == null) {
            address.setOwnerId(user.getOwnerId());
            address.setCreater(user.getOpenId());
            address.setCreatedTime(new Date());
            boolean b = addressService.addAddress(user, address);
            return render(b);
        } else {
            Map<String, Object> addr = addressService.findMap(Kv.obj().set("id", address.getId()).set("creater", user.getOpenId()).set("ownerId", user.getOwnerId()));
            TAddress old = BeanMapUtils.toObject(addr, TAddress.class, true);
            old.setName(address.getName());
            old.setProvince(address.getProvince());
            old.setDetail(address.getDetail());
            old.setPhone(address.getPhone());
            old.setIsDefault(address.getIsDefault());
            boolean b = addressService.updateAddress(user, address);
            return render(b);
        }
    }

    /**
     * 订单生成后，允许买家更改订单的收货地址
     *
     * @param param
     */
    @RequestMapping(value = "order/address", method = RequestMethod.POST)
    public Tip orderAddressUpdate(SessionUser user, @RequestBody Map<String, Object> param) throws Exception {
        Kv<String, Object> kv = Kv.toKv(param);
        String orderNo = kv.requiredStr("orderNO");
        String addressId = kv.requiredStr("addressID");
        kv.remove("addressId");
        TAddress a = addressService.findById(user,addressId);
        TOrder order = BeanMapUtils.toObject(orderService.findMap(Kv.obj("orderNo", orderNo).set("ownerId", user.getOwnerId()).set("creater", user.getOpenId())), TOrder.class);
        order.setAddressId(addressId);
        order.setPhone(a.getPhone());
        boolean update = orderService.updateByVersion(order);
        return render(update);
    }


    /**
     * 意见反馈
     *
     * @param user
     * @param question
     */
    @RequestMapping(value = "question", method = RequestMethod.POST)
    public Tip question(SessionUser user, @RequestBody TQuestion question) {
        boolean b = questionService.add(user, question);
        return render(b);
    }


    /**
     * 查看购物车
     *
     * @param user
     * @param flag  all/money
     * @param param
     * @return
     */
    @RequestMapping(value = "cart", method = RequestMethod.POST)
    public Tip owner(SessionUser user, @RequestParam(required = false, defaultValue = "all") String flag, @Valid @RequestBody CartParam param, BindingResult result) throws Exception {
        Validator.valid(result);
        int round = BigDecimal.ROUND_HALF_UP;
        List<Integer> nums = param.getNums();
        List<String> units = param.getUnits();
        List<String> specs = param.getSpecs();//todo yancc unit id 需要查询转换
        String ids = "'" + StringUtils.join(param.getIds(), "','") + "'";
        //查询订单所有商品
        List<Kv<String, Object>> goods = Kv.toKvs(goodsService.findMaps(Kv.obj().set("ownerId", user.getOwnerId()).set("ids", ids)));
        List<Kv<String, Object>> all = Lists.newArrayList();
        //多种规格拆分为多种商品
        List<Kv<String, Object>> kvSpec;
        for (Kv<String, Object> g : goods) {
            List<Kv<String, Object>> sps = g.getList("specs", Lists.newArrayList());
            kvSpec = Lists.newArrayList();
            g.set("specs", kvSpec);
            if (sps.isEmpty()) {
                kvSpec.add(Kv.obj());
                all.add(g);
            } else {
                for (Kv<String, Object> sp : sps) {
                    if (specs.contains(sp.getStr("id"))) {
                        kvSpec.add(sp);
                        all.add(g);
                    }
                }
            }
        }
        TOwnerConfig config = BeanMapUtils.toObject(ownerConfigService.findMap(Kv.obj().set("ownerId", user.getOwnerId())), TOwnerConfig.class);
        if (all.size() != nums.size() || all.size() != param.getIds().size()) {
            return render(false, "订单错误！");
        }
        //商品信息
        Kv<String, Object> g;
        //商品总价（不包含配送费）
        BigDecimal total = BigDecimal.ZERO;
        //商品总数
        int count = 0;
        List<Kv<String, Object>> maps = Lists.newArrayList();
        Kv<String, Object> m;
        boolean isPromotion;//是否促销
        BigDecimal price;//商品单价
        BigDecimal promotionPrice = BigDecimal.ZERO;
        BigDecimal num;//商品数量
        BigDecimal goodsMoney;//商品总价（商品单价*商品数量）
        Kv<String, Object> spec;
        for (int i = 0; i < all.size(); i++) {
            // 计算单个商品的总价（总价 = 购买数量 * 商品单价）
            g = all.get(i);
            final String specId = param.getSpecs().get(i);
            spec = g.getRequiredList("specs").stream().filter(s -> s.containsKey("id") && specId.equals(s.get("id"))).findFirst().orElse(Kv.obj());
            isPromotion = g.getBoolean("isPromotion");
            price = g.getBigDecimal("price").setScale(2, round);
            num = BigDecimal.valueOf(nums.get(i));
            goodsMoney = num.multiply(price);
            // 如果是促销商品，则：总价 = 购买数量 * 促销价
            if (isPromotion) {
                promotionPrice = g.getBigDecimal("promotionMoney").setScale(2, round);
                goodsMoney = num.multiply(promotionPrice);
            }
            // 商品总价只计算在售商品
            if (TGoods.status_1.equals(g.getInt("status"))) {
                total = total.add(goodsMoney);
                count += num.intValue();
            }
            m = Kv.obj();
            if ("all".equals(flag)) {
                m.put("id", g.get("id"));
                m.put("name", g.get("name"));
                m.put("pics", g.get("pics"));
                m.put("measurementUnit", g.get("measurementUnit"));
                m.put("unitId", g.get("unitId"));
                m.put("price", price);
                m.put("specs", spec);
                m.put("count", num.intValue());
                m.put("total", goodsMoney.setScale(2, round));
                m.put("status", g.get("status"));
                m.put("isPromotion", isPromotion);
                if (isPromotion) {
                    //正常促销
                    m.put("promotionMoney", promotionPrice);
                } else {
                    //没有促销
                    m.put("promotionMoney", 0);
                }
                maps.add(m);
            }
        }
        // 运费
        BigDecimal actualFee;
        // 是否达到商家设置的起配金额
        boolean reachDeliveryMoney;
        if (total.compareTo(config.getDeliveryMoney()) < 0) {
            // 配送费取delivery_less_money
            actualFee = config.getDeliveryLessMoney();
            reachDeliveryMoney = false;
        } else {
            // 配送费取delivery_great_money
            actualFee = config.getDeliveryGreatMoney();
            reachDeliveryMoney = true;
        }
        ResultView map = render();
        if ("all".equals(flag)) {
            map.put("goods", maps);
        }
        map.put("goodsMoney", total.setScale(2, round));
        map.put("goodsCount", count);
        map.put("reachDeliveryMoney", reachDeliveryMoney);
        map.put("actualFee", actualFee.setScale(2, round));
        map.put("actualMoney", total.add(actualFee).setScale(2, round));
        return map;
    }


    /**
     * 描述：订单支付成功后，完成后续的订单逻辑，比如收集PrepayID，
     * 用于后续发送模板消息、向店家发送短信通知、发送极光消息推送等。
     * //todo yancc 修改或删除这个方法
     * @param param
     * @return
     */
    @RequestMapping(value = "orders/{no}", method = RequestMethod.POST)
    public Tip orders(SessionUser user, @PathVariable String no, @RequestBody PayAfterOrderParam param) throws Exception {
        // 如果是在线支付，则向买家发送【订单支付成功】模板消息
        TOrder order = BeanMapUtils.toObject(orderService.findMap(Kv.obj().set("orderNo", no).set("creater", user.getOpenId())), TOrder.class);
        // 发送短信通知
        String phone = (String) redisTemplate.opsForHash().get("owner:" + user.getAppId(), "orderPhone");
        if (StringUtils.isNotBlank(phone)) {
            smsService.sendNotify(phone, WXContants.TENCENT_TEMPLATE_ID4, param.getOrderNO());
        }
        //通知不在这里发送了，转移到回调里面了

        // IM通知店铺
        imService.sendNewOrderNotify(user, order);
        String tempKey = "ms:fit:" + param.getOrderNO();
        redisTemplate.opsForValue().set(tempKey, param.getFormID(), 604800, TimeUnit.SECONDS);
        logger.info("买家支付订单时保存FormID {} = {}", tempKey, param.getFormID());
        boolean update = orderService.updateByVersion(order);
        return render(update);
    }


    /**
     * 微信支付
     *
     * @param user
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "wxpay/unifiedorder", method = RequestMethod.POST)
    public Object unifiedorder(SessionUser user, @RequestBody Map<String, Object> map, HttpServletRequest request) throws Exception {
        Kv<String, Object> kv = Kv.toKv(map);
        String no = kv.requiredStr("orderNO");
        Integer source = kv.getInt("source");
        String remark = kv.getStr("remark");
        String phone = kv.getStr("phone");
        String spbill_create_ip = request.getRemoteHost().replace("::ffff:", "");
        try {
            WxPayMpOrderResult result = orderService.unifiedorder(user, no, source, spbill_create_ip, phone, remark);
            return buildPayView(result);
        } catch (WxPayException e) {
            return render(false, e.getReturnMsg()).set("status", "102");
        }

    }


    /**
     * 小程序下单
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Object order(SessionUser user, @RequestBody @Valid CreateOrderParam param,BindingResult result) {
        try {
            Validator.valid(result);
            TOrder order = orderService.createMiniAppOrder(user, param);
            return render().set("orderNO", order.getOrderNo());
        } catch (Exception e) {
            e.printStackTrace();
            return render(false, e.getMessage());
        }
    }


    public Tip buildPayView(WxPayMpOrderResult res) {
        Kv<String, Object> tip;
        tip = render();
        //tip.set("outTradeNo", req.getOutTradeNo());
        // 小程序 客户端支付需要 nonceStr,timestamp,package,paySign  这四个参数
        tip.set("nonceStr", res.getNonceStr());
        tip.set("timestamp", res.getTimeStamp());
        tip.set("package", res.getPackageValue());
        tip.set("paySign", res.getPaySign());
        tip.set("status", "100");
        return tip;
    }

    /**
     * 买家删除订单
     *
     * @param user
     * @param param
     * @return
     */
    @RequestMapping(value = "order/delete", method = RequestMethod.POST)
    public Object deleteOrder(SessionUser user, @Valid @RequestBody OrderDeleteParam param, BindingResult result) {
        Validator.valid(result);
        boolean b = orderService.customerDelete(user, param);
        return render(b);
    }

    /**
     * 买家取消订单
     *
     * @param user
     * @param param
     * @return
     */
    @RequestMapping(value = "order/cancel", method = RequestMethod.POST)
    public Object cancelOrder(SessionUser user, @Valid @RequestBody OrderDeleteParam param, BindingResult result) {
        Validator.valid(result);
        boolean b = orderService.customerCancel(user, param);
        return render(b);
    }


    /**
     * 前端查询某个订单的状态
     *
     * @param user
     * @param orderNO
     * @return
     * @throws WxPayException
     */
    @RequestMapping(value = "order/status", method = RequestMethod.GET)
    public Object orderFind(SessionUser user, String orderNO) throws Exception {
        SWxApp app = appService.find(user);
        WxPayService service = hzcxWxService.getWxPayService(app);
        WxPayOrderQueryResult result = service.queryOrder(WxPayOrderQueryRequest.newBuilder().outTradeNo(orderNO).build());
        try {
            Map<String, Object> map = orderService.findMap(Kv.obj("creater", user.getOpenId()).set("orderNo", orderNO));
            if (TblOrder.STATUS_0.equals(map.get("status"))) {
                //orderService.update(result, user);
                //todo yancc
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("前端查询订单，被动更新失败：订单号=" + orderNO);
        }
        return render(true, "订单支付成功！").set("total_fee", result.getTotalFee());
    }


    /**
     * 补差价的二次支付下单
     * 描述：一个订单，当商家补充了小票金额后，当小票金额大于订单已支付金额时，需要买家补齐差价。
     *
     * @param user  用户
     * @param param 参数
     * @return 该接口逻辑和微信下单接口逻辑一样，返回结果也一样
     */
    @RequestMapping(value = "diff/wxpay/unifiedorder", method = RequestMethod.POST)
    public Object diff(SessionUser user, @RequestBody @Valid DiffParam param, HttpServletRequest request, BindingResult result) throws Exception {
        Validator.valid(result);
        try {
            // 获取客户端ip
            String spbill_create_ip = request.getRemoteHost().replace("::ffff:", "");
            WxPayMpOrderResult res = orderService.unifiedorderDiff(user, param, spbill_create_ip);
            return buildPayView(res);
        } catch (WxPayException e) {
            return render(false, e.getReturnMsg()).set("status", "102");
        } catch (Exception e){
            return render(false, e.getMessage()).set("status", "102");
        }

    }


}
