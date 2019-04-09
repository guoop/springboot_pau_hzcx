package com.soft.ware.rest.modular.wxsmall;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.base.warpper.ListWrapper;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ResultView;
import com.soft.ware.rest.common.persistence.model.*;
import com.soft.ware.rest.modular.auth.controller.dto.*;
import com.soft.ware.rest.modular.auth.service.*;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.auth.validator.Validator;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class WXSmallCustomerController  extends BaseController {

    @Autowired
    private TblBannerService bannerService;

    @Autowired
    private TblCategoryService categoryService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

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

    @Autowired
    private HzcxWxService hzcxWxService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private TblOwnerTempService tempService;

    @Autowired
    private ImService imService;

    @Autowired
    private WXContants wxContants;

    @Autowired
    private TblOrderMoneyDiffService orderMoneyDiffService;

    /**
     * banner
     * @param user
     * @return
     */
    //@RequestMapping(value = "/customer/v1/banner/list")
    public Object banners(SessionUser user){
        List<TblBanner> list = bannerService.findBannerByOwner(user.getOwnerId());
        Map<String, Object> map = new HashMap<>();
        map.put("code", SUCCESS);
        return list;
    }


    /**
     * 商品分类列表
     * @param user
     * @return
     */
    //@RequestMapping(value = "/customer/v1/category/list")
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
    //@RequestMapping(value = "/customer/v1/goods/list",method = RequestMethod.GET)
    public Object goodsPage(GoodsPageParam param,SessionUser user, Page page) throws WxErrorException {
        List<Map> list = goodsService.findPage(user, page, param);
        return list;
    }

    /**
     * 商品详情
     * @param id
     * @return
     */
    //@RequestMapping(value = "/customer/v1/goods/{id}")
    public Object goods(@PathVariable Long id) throws Exception {
       TblGoods goods = goodsService.findById(id);
        List<TblGoods> list = new ArrayList<>();
        list.add(goods);
        List<Map<String, Object>> maps = BeanMapUtils.toMap(true, list);
        return warpObject(new ListWrapper(maps));
    }

    /**
     * 商品详情
     * @param id
     * @return
     */
    //@RequestMapping(value = "/customer/v1/goods/{id}",method = RequestMethod.GET,params = {"flag=goodsNO"})
    public Object goodsByCode(SessionUser owner,@PathVariable String id) throws Exception {
        List<TblGoods> goods = goodsService.findByCode(owner,id);
        List<Map<String, Object>> maps = BeanMapUtils.toMap(true, goods);
        return warpObject(new ListWrapper(maps));
    }

    /**
     * 商户信息查询
     * @param user
     * @return
     * @throws Exception
     */
    //@RequestMapping(value = "/customer/v1/shop")
    public Object owner(SessionUser user) throws Exception {
        TblOwner o = ownerService.find(user);
        Map<String, Object> map = BeanMapUtils.toMap(o, true);
        return map;
    }


    /**
     * 查看购物车
     * @param user
     * @param flag
     * @param param
     * @return
     */
    @RequestMapping(value = "/customer/v1/cart")
    public Object owner(SessionUser user, @RequestParam(required = false,defaultValue = "all") String flag, CartParam param){
        int round = BigDecimal.ROUND_HALF_UP;

        String[] units = param.getUnits();
        int[] nums = param.getNums();

        List<TblGoods> all = goodsService.findAll(user, param.getSids());
        TblOwner owner = ownerService.find(user.getOwnerId());
        long current = System.currentTimeMillis();
        TblGoods g;
        BigDecimal total = BigDecimal.ZERO;
        int count = 0;
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        Map<String,Object> m;
        for (int i = 0; i < all.size(); i++) {
            // 计算单个商品的总价（总价 = 购买数量 * 商品单价）
            // let goodsMoney = parseInt(cartGoodsItems[2]) * parseFloat(result.price_unit);
            g = all.get(i);
            BigDecimal goodsMoney = BigDecimal.valueOf(nums[i]).multiply(g.getPriceUnit());
            // 如果是促销商品，则：总价 = 购买数量 * 促销价
            if (g.getIsPromotion().equals(TblGoods.is_promotion_1) && g.getPromotionEndtime() != null &&  g.getPromotionEndtime().getTime() > current) {
                // goodsMoney = parseInt(cartGoodsItems[2]) * parseFloat(result.promotion_price);
                goodsMoney = BigDecimal.valueOf(nums[i]).multiply(g.getPromotionPrice());
            }
            // 商品总价只计算在售商品
            if (g.getStatus().equals(1)) {
                // total += goodsMoney;
                total = total.add(goodsMoney);
                count += nums[i];
            }
            boolean is_promotion = TblGoods.is_promotion_1.equals(g.getIsPromotion());
            m = new HashMap<>();
            if ("all".equals(flag)) {
                m.put("id", g.getId());
                m.put("name", g.getName());
                m.put("pics", g.getPics() != null ? g.getPics().split(",")[0] : "");
                m.put("measurement_unit", g.getMeasurementUnit());
                m.put("price_unit", g.getPriceUnit().setScale(2, round));
                m.put("specifications", units[i]);
                m.put("count", nums[i]);
                m.put("total", goodsMoney.setScale(2,round));
                m.put("status", g.getStatus());
                m.put("is_promotion",g.getIsPromotion());
                if (is_promotion) {
                    if (g.getPromotionEndtime()!=null && g.getPromotionEndtime().getTime() < current) {
                        //促销过期
                        m.put("promotion_price", 0);
                        m.put("promotion_in_progress", 0);
                    }else{
                        //正常促销
                        m.put("promotion_price", g.getPromotionPrice().setScale(2, round));
                        m.put("promotion_in_progress", 1);
                    }
                }else{
                    //没有促销
                    m.put("promotion_price", 0);
                    m.put("promotion_in_progress",0);
                }
                maps.add(m);
            }
        }

        BigDecimal goodsMoney = total;         // 商品总价（不包含配送费）
        BigDecimal actualFee = BigDecimal.ZERO;// 运费
        boolean reachDeliveryMoney = false;        // 是否达到商家设置的起配金额
        if (total.compareTo(owner.getDeliveryMoney()) < 0) {
            // 配送费取delivery_less_money
            actualFee = BigDecimal.valueOf(owner.getDeliveryLessMoney());
            reachDeliveryMoney = false;
        } else {
            // 配送费取delivery_great_money
            actualFee = BigDecimal.valueOf(owner.getDeliveryGreatMoney());
            reachDeliveryMoney = true;
        }


        ResultView map = render();
        if ("all".equals(flag)) {
            map.put("goods", maps);
        }
        map.put("goodsMoney", goodsMoney.setScale(2, round));
        map.put("goodsCount", count);
        map.put("reachDeliveryMoney", reachDeliveryMoney);
        map.put("actualFee", actualFee.setScale(2, round));
        map.put("actualMoney", goodsMoney.add(actualFee).setScale(2, round));
        return map;
    }


    /**
     * 订单列表查询
     * @param user
     * @param page
     * @param param
     * @return
     */
    @RequestMapping(value = "/customer/v2/orders")
    public Object orders(SessionUser user,Page page, OrderPageParam param){
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
            param.setStatus(Integer.MAX_VALUE+"");
        }
        List<Map> list = orderService.findPage(user, page, param, TblOrder.SOURCE_0, TblOrder.SOURCE_2);
        return warpObject(new ListWrapper(list));
    }



    /**
     * 订单详情
     * @param user
     * @param no
     * @return
     */
    @RequestMapping(value = "/customer/v2/orders/{no}",method = RequestMethod.GET)
    public Object orders(SessionUser user,@PathVariable String no) {
        List<Map> list = orderService.findOrderMapByNo(user, no);
        return warpObject(new ListWrapper(list));
    }

    /**
     * 描述：订单支付成功后，完成后续的订单逻辑，比如收集PrepayID，
     * 用于后续发送模板消息、向店家发送短信通知、发送极光消息推送等。
     * @param param
     * @return
     */
    @RequestMapping(value = "/customer/v1/orders/{no}",method = RequestMethod.POST)
    public Tip orders(SessionUser user,@PathVariable String no,@RequestBody OrderUpdateParam param) throws WxErrorException {
        // 如果是在线支付，则向买家发送【订单支付成功】模板消息
        TblOrder order = orderService.findByNo(user, no);
        long current = System.currentTimeMillis();
        String pack = param.getPack();
        if (param.getMoneyChannel() == TblOrder.SOURCE_0) {
            String tempKey = "ms:ppi:" + param.getOrderNO();
            redisTemplate.opsForValue().set(tempKey,pack,604800,TimeUnit.SECONDS);
            logger.debug("买家支付订单时保存PrepayID {} = {}",tempKey,pack);
            // 微信支付时发送模板消息
            String pay = tempService.getTplId(user, "pay");
            TblOwner owner = ownerService.find(user);
            WxMaService service = hzcxWxService.getWxMaService(owner);
            List<WxMaTemplateData> msList = new ArrayList<>();
            // 订单编号
            msList.add(new WxMaTemplateData("keyword1", param.getOrderNO()));
            // 下单时间
            msList.add(new WxMaTemplateData("keyword2", DateUtil.format(param.getCreated_at(),"YYYY-MM-DD HH:mm:ss")));
            // 订单金额
            msList.add(new WxMaTemplateData("keyword3", param.getPayMoney() + "元"));
            // 商品名称
            msList.add(new WxMaTemplateData("keyword4", param.getGoodsName()));
            // 收货地址
            msList.add(new WxMaTemplateData("keyword5", param.getAddress()));
            // 备注信息
            msList.add(new WxMaTemplateData("keyword6", "如有疑问，请进入小程序联系商家"));

            // 微信支付时发送模板消息
            service.getMsgService().sendTemplateMsg(WxMaTemplateMessage.builder()
                    // 接收者（用户）的 openid
                    .toUser(user.getOpenId())
                    // 所需下发的模板消息的id
                    .templateId(pay)
                    // 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
                    .page("/pages/mine/index")
                    // 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
                    .formId(pack)
                    // 模板内容，不填则下发空模板
                    .data(msList)
                    .formId(param.getFormID())
                    .build());

        }
        // 发送短信通知
        String phone = (String) redisTemplate.opsForHash().get("owner:" + user.getAppId(), "order_phone");
        if (StringUtils.isNotBlank(phone)) {
           smsService.sendNotify(phone, WXContants.TENCENT_TEMPLATE_ID4, param.getOrderNO());
        }
        // IM通知店铺
        imService.sendNewOrderNotify(user, order);
        String tempKey = "ms:fit:" + param.getOrderNO();
        redisTemplate.opsForValue().set(tempKey,param.getFormID(), 604800,TimeUnit.SECONDS);
        logger.info("买家支付订单时保存FormID {} = {}", tempKey, param.getFormID());
        // 标识订单来源
        if (Integer.valueOf(TblOrder.SOURCE_2).equals(param.getSource())) {
            //计数器加1
            redisTemplate.opsForValue().increment("counter:" + user.getAppId(), 1);
            Object s = redisTemplate.opsForValue().get("counter:" + user.getAppId());
            order.setPickupNo(Long.valueOf(s.toString()));
            order.setPickupTime(new Date(current));
            order.setFreight(BigDecimal.ZERO);
            order.setPayMoney(order.getMoney());

        } else {
            order.setPickupTime(null);
            order.setPickupNo(null);
        }
        order.setSource(param.getSource());
        order.setMoneyChannel(param.getMoneyChannel());
        order.setRemark(param.getRemark());
        if (TblOrder.MONEY_CHANNEL_1.equals(order.getMoneyChannel())) {
            order.setStatus(TblOrder.MONEY_CHANNEL_1);
        }
        if (StringUtils.isNotBlank(param.getTelephone())) {
            order.setConsigneeMobile(param.getTelephone());
        }
        boolean update = orderService.update(order, new EntityWrapper<>(new TblOrder().setId(order.getId()).setOwner(user.getOwnerId()).setStatus(order.getStatus())));
        return render(update);
    }

    /**
     * 微信支付
     * @param user
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value = "/customer/v1/wxpay/unifiedorder")
    public Object unifiedorder(SessionUser user,@RequestBody UnifiedorderParam param, HttpServletRequest request) {
        if (Integer.valueOf(2).equals(param.getSource()) && StringUtils.isBlank(param.getTelephone())) {
            return render(false, "请完善预留手机号");
        }
        String no = param.getOrderNO();
        Integer source  = param.getSource();
        TblOrder order = orderService.findByNo(user,no);
        TblOwner owner = ownerService.find(user);
        boolean source2 = Integer.valueOf(TblOrder.SOURCE_2).equals(source);
        // 获取客户端ip
        String spbill_create_ip = request.getRemoteHost().replace("::ffff:", "");

        // 商品描述
        String body = "购买商品";
        // 支付成功的回调地址  可访问 不带参数
        String notify_url = wxContants.getCustomerPayHost() + wxContants.getCustomerPay();
        String attach = "无";
        if (source2) {
            notify_url = wxContants.getCustomerPayHost() + wxContants.getCustomerPayPickup();
            attach = no;
        }
        // 商户订单号
        String out_trade_no = order.getNo();
        if (source2) {
            out_trade_no = param.getTelephone() + "" + order.getCreatedAt().getTime();
        }
        // 订单价格 单位是 分
        int total_fee = order.getMoney().multiply(BigDecimal.valueOf(100)).add(order.getFreight().multiply(BigDecimal.valueOf(100))).intValue();
        if (source2) {
            total_fee = order.getMoney().multiply(BigDecimal.valueOf(100)).intValue();
        }
        WxPayUnifiedOrderRequest req = buildPayReq(user, out_trade_no, total_fee, notify_url, body, attach, spbill_create_ip);
        return buildPayView(owner, req);
    }

    /**
     * 小程序下单
     * @param param
     * @return
     */
    @RequestMapping(value = {"/customer/v1/order"},method = RequestMethod.POST)
    public Object order(SessionUser user,@RequestBody CartParam param) {
        TblOrder order = orderService.createMiniAppOrder(user, param);
        return render().set("orderNO", order.getNo());
    }


    /**
     * 订单生成后，允许买家更改订单的收货地址
     * @param param
     */
    @RequestMapping(value = "/customer/v1/order/address",method = RequestMethod.POST)
    public Tip orderAddressUpdate(SessionUser user,@RequestBody OrderAddressUpdateParam param){
        TblOrder order = orderService.findByNo(user, param.getOrderNO());
        TblAddress address = addressService.findById(user, param.getAddressID());
        order.setConsigneeMobile(address.getTelephone());
        order.setConsigneeName(address.getName());
        order.setConsigneeAddress(address.getProvince() + "  " + address.getDetail());
        boolean update = orderService.update(order, new EntityWrapper<>(new TblOrder().setId(order.getId()).setOwner(user.getOwnerId()).setStatus(TblOrder.STATUS_0)));
        return render(update);
    }

    /**
     * 买家删除订单
     * @param user
     * @param param
     * @return
     */
    @RequestMapping(value = "/customer/v1/order/delete",method = RequestMethod.POST)
    public Object deleteOrder(SessionUser user,@RequestBody OrderDeleteParam param){
        boolean b = orderService.customerDelete(user, param);
        return render(b);
    }

    /**
     * 买家取消订单
     * @param user
     * @param param
     * @return
     */
    @RequestMapping(value = "/customer/v1/order/cancel",method = RequestMethod.POST)
    public Object cancelOrder(SessionUser user,@RequestBody OrderDeleteParam param){
        boolean b = orderService.customerCancel(user, param);
        return render(b);
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
     * 删除收货地址
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "/customer/v1/address/del",method = RequestMethod.POST)
    public Object addressDel(SessionUser user,@RequestBody Id id){
        TblAddress address = addressService.findById(user, Integer.valueOf(id.getId()));
        boolean b = addressService.deleteById(address.getId());
        return render(b);
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
            address.setOwner(user.getOpenId());
            address.setCreatedAt(new Date());
            boolean b = addressService.addAddress(user, address);
            return render(b);
        }else{
            TblAddress old = addressService.findById(user, address.getId());
            old.setName(address.getName());
            old.setProvince(address.getProvince());
            old.setDetail(address.getDetail());
            old.setTelephone(address.getTelephone());
            old.setIsDefault(address.getIsDefault());
            boolean b = addressService.updateAddress(user, address);
            return render(b);
        }
    }


    /**
     * 意见反馈
     * @param user
     * @param question
     */
    @RequestMapping(value = "/customer/v1/question",method = RequestMethod.POST)
    public Object question(SessionUser user,@RequestBody TblQuestion question){
        question.setOpenId(user.getOpenId());
        question.setOwner(user.getOwnerId());
        question.setCreatedAt(new Date());
        boolean b = questionService.add(question);
        return render(b);
    }


    /**
     * 前端查询某个订单的状态
     * @param user
     * @param orderNO
     * @return
     * @throws WxPayException
     */
    @RequestMapping(value = "/customer/v1/order/status",method = RequestMethod.GET)
    public Object orderFind(SessionUser user,String orderNO) throws WxPayException {
        TblOwner owner = ownerService.find(user);
        WxPayService service = hzcxWxService.getWxPayService(owner);
        WxPayOrderQueryResult result = service.queryOrder(WxPayOrderQueryRequest.newBuilder().outTradeNo(orderNO).build());
        try{
            TblOrder o = orderService.findByNo(user, orderNO);
            if (TblOrder.STATUS_0.equals(o.getStatus())) {
                //orderService.update(result, user);
                //todo yancc
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("前端查询订单，被动更新失败：订单号=" + orderNO);
        }
        return render(true, "订单支付成功！").set("total_fee", result.getTotalFee());
    }

    /**
     * 补差价的二次支付下单
     * 描述：一个订单，当商家补充了小票金额后，当小票金额大于订单已支付金额时，需要买家补齐差价。
     * @param user 用户
     * @param param 参数
     * @return 该接口逻辑和微信下单接口逻辑一样，返回结果也一样
     */
    @RequestMapping(value = "/customer/v2/diff/wxpay/unifiedorder",method = RequestMethod.POST)
    public Object diff(SessionUser user, @RequestBody @Valid DiffParam param, HttpServletRequest request, BindingResult result) {
        Validator.valid(result);
        String no = param.getDiffNO();
        TblOrderMoneyDiff diff = orderMoneyDiffService.findByNo(user, no);
        TblOwner owner = ownerService.find(user);
        String remark = "无";
        // 获取客户端ip
        String spbill_create_ip = request.getRemoteHost().replace("::ffff:", "");
        // 商品描述
        String body = "购买商品";
        // 支付成功的回调地址  可访问 不带参数
        String notify_url = wxContants.getCustomerPayHost() + wxContants.getCustomerPayDiff();
        // 订单价格 单位是 分
        Integer total_fee = diff.getMoneyDiff().multiply(BigDecimal.valueOf(100)).intValue();
        WxPayUnifiedOrderRequest req = buildPayReq(user, no, total_fee, notify_url, body, remark, spbill_create_ip);
        return  buildPayView(owner, req);

    }

    public WxPayUnifiedOrderRequest buildPayReq(SessionUser user, String no, Integer total_fee, String notifyUrl, String body, String attach, String ip) {
        return WxPayUnifiedOrderRequest
                .newBuilder()
                .body(body)
                .attach(attach)
                .notifyUrl(notifyUrl)
                .openid(user.getOpenId())
                .outTradeNo(no)
                .spbillCreateIp(ip)
                .tradeType(WxPayConstants.TradeType.JSAPI)
                .totalFee(total_fee).build();
    }


    public Tip buildPayView(TblOwner owner,WxPayUnifiedOrderRequest req){
        Kv<String,Object> tip;
        try {
            WxPayMpOrderResult res = hzcxWxService.getWxPayService(owner).createOrder(req);
            tip = render();
            tip.set("out_trade_no", req.getOutTradeNo());
            // 小程序 客户端支付需要 nonceStr,timestamp,package,paySign  这四个参数
            tip.set("nonceStr", res.getNonceStr());
            tip.set("timestamp", res.getTimeStamp());
            tip.set("package", res.getPackageValue());
            tip.set("paySign", res.getPaySign());
        } catch (WxPayException e) {
            e.printStackTrace();
            return render(false, e.getReturnMsg()).set("status", "102");
        }
        return tip;
    }
    
}
