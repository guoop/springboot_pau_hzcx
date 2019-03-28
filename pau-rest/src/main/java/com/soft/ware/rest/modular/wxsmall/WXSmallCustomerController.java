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
import com.soft.ware.core.base.warpper.ListWrapper;
import com.soft.ware.core.base.warpper.MapWrapper;
import com.soft.ware.core.base.warpper.SuccessWrapper;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.rest.common.persistence.model.*;
import com.soft.ware.rest.modular.auth.controller.dto.*;
import com.soft.ware.rest.modular.auth.service.*;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.auth.wrapper.CarWrapper;
import com.soft.ware.rest.modular.auth.wrapper.OrderWrapper;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class WXSmallCustomerController  extends BaseController {

    private Logger log = LoggerFactory.getLogger(WXSmallCustomerController.class);

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
    public Object goodsPage(GoodsPageParam param,SessionUser user, Page page) throws WxErrorException {
        List<Map> list = goodsService.findPage(user, page, param);
        return list;
    }

    /**
     * 商品详情
     * @param id
     * @return
     */
    
    @RequestMapping(value = "/customer/v1/goods/{id}")
    public Object goods(@PathVariable Long id) throws Exception {
       TblGoods goods = goodsService.findById(id);
        List<TblGoods> list = new ArrayList<>();
        list.add(goods);
        List<Map<String, Object>> maps = BeanMapUtils.toMap(true, list);
        return warpObject(new ListWrapper(maps));
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
        TblOwner owner = ownerService.find(user.getOwner());
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


        Map<Object, Object> map = new HashMap<>();
        if ("all".equals(flag)) {
            map.put("goods", maps);
        }
        map.put("goodsMoney", goodsMoney.setScale(2, round));
        map.put("goodsCount", count);
        map.put("reachDeliveryMoney", reachDeliveryMoney);
        map.put("actualFee", actualFee.setScale(2, round));
        map.put("actualMoney", goodsMoney.add(actualFee).setScale(2, round));
        return super.warpObject(new CarWrapper(map));
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
        return warpObject(new OrderWrapper(list));
    }



    /**
     * 订单详情
     * @param user
     * @param no
     * @return
     */
    @RequestMapping(value = "/customer/v2/orders/{no}",method = RequestMethod.GET)
    public Object orders(SessionUser user,@PathVariable String no) throws Exception {
        TblOrder order = orderService.findByNo(user, no);
        List<Map> list = new ArrayList<>();
        Map<String, Object> map = BeanMapUtils.toMap(order, true);
        list.add(map);
        return warpObject(new OrderWrapper(list));
    }

    /**
     * 描述：订单支付成功后，完成后续的订单逻辑，比如收集PrepayID，
     * 用于后续发送模板消息、向店家发送短信通知、发送极光消息推送等。
     * @param param
     * @return
     */
    @RequestMapping(value = "/customer/v1/orders/{no}",method = RequestMethod.POST)
    public Object orders(SessionUser user,@PathVariable String no,@RequestBody OrderUpdateParam param) throws WxErrorException {
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
        //todo yancc im 极光
        //imSign.notifyMpForOrder(req._target_);
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
        boolean update = orderService.update(order, new EntityWrapper<>(new TblOrder().setId(order.getId()).setOwner(user.getOwner()).setStatus(order.getStatus())));
        return warpObject(render(update));
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
            MapWrapper wrapper = new MapWrapper();
            wrapper.put("code", "false");
            wrapper.put("msg", "请完善预留手机号");
            return super.warpObject(wrapper);
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
        WxPayUnifiedOrderRequest req = WxPayUnifiedOrderRequest
                .newBuilder()
                .body(body)
                .attach(attach)
                .notifyUrl(notify_url)
                .openid(user.getOpenId())
                .outTradeNo(out_trade_no)
                .spbillCreateIp(spbill_create_ip)
                .tradeType(WxPayConstants.TradeType.JSAPI)
                .totalFee(total_fee).build();
        Map<String, Object> map = new HashMap<>();
        try {
            WxPayMpOrderResult res = hzcxWxService.getWxPayService(owner).createOrder(req);
            map.put("msg", "操作成功");
            map.put("status", "100");
            map.put("out_trade_no", out_trade_no);
            // 小程序 客户端支付需要 nonceStr,timestamp,package,paySign  这四个参数
            map.put("nonceStr",res.getNonceStr());
            map.put("timestamp", res.getTimeStamp());
            map.put("package", res.getPackageValue());
            map.put("paySign", res.getPaySign());
        } catch (WxPayException e) {
            e.printStackTrace();
            map.put("msg",e.getReturnMsg());
            map.put("code", "102");
        }
        return warpObject(new MapWrapper(map));
    }

    /**
     * 小程序下单
     * @param param
     * @return
     */
    @RequestMapping(value = {"/customer/v1/order"},method = RequestMethod.POST)
    public Object order(SessionUser user,@RequestBody CartParam param) {
        TblOrder order = orderService.createMiniAppOrder(user, param);
        MapWrapper map = new MapWrapper();
        map.put("orderNO", order.getNo());
        return warpObject(map);
    }


    /**
     * 订单生成后，允许买家更改订单的收货地址
     * @param param
     */
    @RequestMapping(value = "/customer/v1/order/address",method = RequestMethod.POST)
    public Object orderAddressUpdate(SessionUser user,@RequestBody OrderAddressUpdateParam param){
        TblOrder order = orderService.findByNo(user, param.getOrderNO());
        TblAddress address = addressService.findById(user, param.getAddressID());
        order.setConsigneeMobile(address.getTelephone());
        order.setConsigneeName(address.getName());
        order.setConsigneeAddress(address.getProvince() + "  " + address.getDetail());
        boolean update = orderService.update(order, new EntityWrapper<>(new TblOrder().setId(order.getId()).setOwner(user.getOwner()).setStatus(TblOrder.STATUS_0)));
        return warpObject(render(update));
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
        return warpObject(render(b));
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
        return warpObject(render(b));
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
        return warpObject(render(b));
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
            return warpObject(render(b));
        }else{
            TblAddress old = addressService.findById(user, address.getId());
            old.setName(address.getName());
            old.setProvince(address.getProvince());
            old.setDetail(address.getDetail());
            old.setTelephone(address.getTelephone());
            old.setIsDefault(address.getIsDefault());
            boolean b = addressService.updateAddress(user, address);
            return warpObject(render(b));
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
        question.setOwner(user.getOwner());
        question.setCreatedAt(new Date());
        boolean b = questionService.add(question);
        return warpObject(render(b));
    }


    /**
     *
     * @param user
     * @param orderNO
     * @return
     * @throws WxPayException
     */
    @RequestMapping(value = "/order/check")
    public Object orderFind(SessionUser user,String orderNO) throws WxPayException {
        TblOwner owner = ownerService.find(user);
        WxPayService service = hzcxWxService.getWxPayService(owner);
        //todo yancc 处理订单逻辑
        WxPayOrderQueryResult result = service.queryOrder(WxPayOrderQueryRequest.newBuilder().outTradeNo(orderNO).build());
        return warpObject(new SuccessWrapper(result));
    }


}
