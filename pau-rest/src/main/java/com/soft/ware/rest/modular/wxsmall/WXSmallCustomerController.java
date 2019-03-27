package com.soft.ware.rest.modular.wxsmall;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.warpper.ListWrapper;
import com.soft.ware.core.base.warpper.MapWrapper;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.rest.common.persistence.model.*;
import com.soft.ware.rest.modular.auth.controller.dto.*;
import com.soft.ware.rest.modular.auth.service.*;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.util.RegexUtils;
import com.soft.ware.rest.modular.auth.wrapper.CarWrapper;
import com.soft.ware.rest.modular.auth.wrapper.OrderWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

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
    //todo yancc 删掉
    //@Value("${payUrlPrefix}")
    private String payUrlPrefix;

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
    public Object goodsPage(GoodsPageParam param,SessionUser user, Page page){
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
            param.setStatus("-2, -1, 0, 1, 2, 3, 10");
        } else if ("pay".equals(param.getStatus())) {
            // 待付款
            param.setStatus("0");
        } else if ("receive".equals(param.getStatus())) {
            // 待收货
            param.setStatus("1, 2, 10");
        } else if ("done".equals(param.getStatus())) {
            // 已完成
            param.setStatus("3");
        }else if ("cancel".equals(param.getStatus())) {
            // 已取消
            param.setStatus("-1, -2");
        }
        List<Map> list = orderService.findPage(user,page,param,TblOrder.SOURCE_0);
        return warpObject(new OrderWrapper(list));
    }



    /**
     * 订单详情
     * @param user
     * @param no
     * @return
     */
    @RequestMapping(value = "/customer/v2/orders/{no}")
    public Object orders(SessionUser user,@PathVariable String no) throws Exception {
        TblOrder order = orderService.findByNo(user, no);
        List<Map> list = new ArrayList<>();
        Map<String, Object> map = BeanMapUtils.toMap(order, true);
        list.add(map);
        return warpObject(new OrderWrapper(list));
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
        TblOrder order = orderService.findByNo(user, param.getOrderNO());
        TblOwner owner = ownerService.find(user);
        String remark = "无";

        // 获取客户端ip
        String spbill_create_ip = request.getRemoteHost().replace("::ffff:", "");

        // 商品描述
        String body = "购买商品";
        // 支付成功的回调地址  可访问 不带参数
        //String urlPrefix = payUrlPrefix == null ? "https://wx.aiinp.com/third-part-callback/we-chat-pay/dev" : payUrlPrefix;
        String urlPrefix = payUrlPrefix == null ? "https://wx.javaccy.giize.com" : payUrlPrefix;
        String notify_url = urlPrefix + "/customer-pay";
        if (param.getSource().equals(TblOrder.SOURCE_2)) {
            notify_url = (urlPrefix + "/customer-pay/pickup");
        }
        // 随机字符串
        //String nonce_str = wxSign.getNonceStr();
        //todo yancc 改用微信sdk自带的
        // 商户订单号
        String out_trade_no = order.getNo();
        if (param.getSource().equals(TblOrder.SOURCE_2)) {
            // out_trade_no = result.order.consignee_mobile.toString() + '' + new Date(result.order.created_at).getTime().toString()
            out_trade_no = param.getTelephone() + "" + order.getCreatedAt().getTime();
        }
        // 订单价格 单位是 分
        // let total_fee = parseInt(result.order.money * 100 + result.order.freight * 100);
        int total_fee = order.getMoney().multiply(BigDecimal.valueOf(100)).add(order.getFreight().multiply(BigDecimal.valueOf(100))).intValue();
        if (param.getSource().equals(TblOrder.SOURCE_2)) {
            total_fee = order.getMoney().multiply(BigDecimal.valueOf(100)).intValue();
        }
        WxPayUnifiedOrderRequest req = WxPayUnifiedOrderRequest
                .newBuilder()
                .body(body)
                .attach(remark)
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
     * 下单
     * @param formID
     * @param param
     * @return
     */
    @RequestMapping(value = {"/customer/v1/order"},method = RequestMethod.POST)
    public Object order(SessionUser user,@RequestParam(defaultValue = "") String formID,@RequestBody CartParam param) {
        int round = BigDecimal.ROUND_HALF_UP;
        Map<String,Object> map = new HashMap<>();
        List<TblGoods> list = goodsService.findAll(user, param.getSids());
        TblOwner owner = ownerService.find(user.getOwner());
        List<TblAddress> addressList = addressService.findAll(user);
        long current = System.currentTimeMillis();
        //k 商品id， param 下标
        Map<Long, Integer> m = new LinkedHashMap<>();
        for (int i = 0; i < param.getIds().length; i++) {
            for (TblGoods g : list) {
                if (g.getId().equals(param.getIds()[i])) {
                    m.put(g.getId(), i);
                    g.setPics(g.getPics() == null ? "" : g.getPics());
                    g.setPics(g.getPics().split(",")[0]);
                }
            }
        }
        int i;
        BigDecimal total = BigDecimal.ZERO;
        for (TblGoods g : list) {
            i = m.get(g.getId());
            // 只计算在售商品
            if (TblGoods.status_1.equals(g.getStatus())) {
                // let goodsPrice = parseFloat(result.price_unit);             // 商品单价
                // let goodsMoney = parseInt(cartGoodsItems[2]) * goodsPrice;  // 商品总价 = 商品单价 * 购买数量
                BigDecimal goodsPrice = g.getPriceUnit();                                // 商品单价
                BigDecimal goodsMoney = BigDecimal.valueOf(param.getNums()[i]).multiply(goodsPrice);   // 商品总价 = 商品单价 * 购买数量
                // 判断是否促销商品
                if (g.getIsPromotion().equals(TblGoods.is_promotion_1) && g.getPromotionEndtime() != null &&  g.getPromotionEndtime().getTime() > current) {
                    // goodsPrice = parseFloat(result.promotion_price);        // 商品促销单价
                    // goodsMoney = parseInt(cartGoodsItems[2]) * goodsPrice;  // 商品总价 = 商品促销单价 * 购买数量
                    goodsPrice = g.getPromotionPrice();                           // 商品促销单价
                    goodsMoney = BigDecimal.valueOf(param.getNums()[i]).multiply(goodsPrice);   // 商品总价 = 商品促销单价 * 购买数量
                }
                //设置总价格，下面的保存订单逻辑要用到
                param.setTotal(goodsMoney, i);
                total = total.add(goodsMoney);
            }
        }


        BigDecimal goodsMoney = total;   // 商品总价（不包含配送费）
        BigDecimal actualFee = BigDecimal.ZERO;  // 运费
        if (goodsMoney.compareTo(owner.getDeliveryMoney()) < 0) {
            // 配送费取delivery_less_money
            actualFee = BigDecimal.valueOf(owner.getDeliveryLessMoney());
        } else {
            // 配送费取delivery_great_money
            actualFee = BigDecimal.valueOf(owner.getDeliveryGreatMoney());
        }
        // 保存订单信息
        List<String> goodsStr = new ArrayList<>();
        for (TblGoods temp : list) {
            i = m.get(temp.getId());
            if (temp.getCode()!=null && temp.getCode().length() == 5) {
                String a = RegexUtils.find(temp.getMeasurementUnit(), Pattern.compile("\\d+"), "1").get(0);
                String b = RegexUtils.find(temp.getMeasurementUnit(), Pattern.compile("[^\\d]"), "").get(0);
                BigDecimal c = BigDecimal.valueOf(Float.valueOf(a)).multiply(BigDecimal.valueOf(param.getNums()[i]));
                //todo yancc pics  和 getSpecs 等 需要预处理
                goodsStr.add(temp.getId() + "__" + temp.getPics() + "__" + temp.getName() + "__" + param.getUnits()[i] + "__" + (c.setScale(0,round) + b) + "__" + temp.getPriceUnit() + '/' + temp.getMeasurementUnit() + "__" + param.getTotals().get(i));
            } else {
                goodsStr.add(temp.getId() + "__" + temp.getPics() + "__" + temp.getName() + "__" + param.getUnits()[i] + "__" + param.getNums()[i] + "__" + temp.getPriceUnit() + "__" + param.getTotals().get(i));
            }
        }
		final String orderNO =  IdGenerator.getId();
        TblOrder o = new TblOrder();
        o.setNo(orderNO);
        o.setMoneyChannel(TblOrder.MONEY_CHANNEL_0);
        o.setMoney(goodsMoney.setScale(2, round));
        o.setFreight(actualFee.setScale(2, round));
        o.setPayMoney(goodsMoney.add(actualFee).setScale(2, round));
        o.setCreatedAt(new Date(current));
        o.setCreatedBy(user.getOpenId());
        o.setOwner(user.getOwner());
        o.setGoods(StringUtils.join(goodsStr, ","));
        o.setStatus(TblOrder.STATUS_0);
        if (!addressList.isEmpty()) {
            TblAddress address = addressList.get(0);
            o.setConsigneeName(address.getName());
            o.setConsigneeMobile(address.getTelephone());
            o.setConsigneeAddress(address.getProvince() + "    " + address.getDetail());
        }
        boolean insert = orderService.insert(o);
        String tempKey = "ms:fio:" + orderNO;
        //todo yancc 处理formID
        ValueOperations<String, String> val = redisTemplate.opsForValue();
        val.set(tempKey, "EX", 604800);
        log.debug("买家下单时保存FormID {tempKey} = {req.body.formID}", tempKey, formID);
        map.put("orderNO", orderNO);
        return map;
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



}
