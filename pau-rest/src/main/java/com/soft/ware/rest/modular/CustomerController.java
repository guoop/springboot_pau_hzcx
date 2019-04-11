package com.soft.ware.rest.modular;

import com.google.common.collect.Lists;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.base.warpper.ListWrapper;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ResultView;
import com.soft.ware.rest.modular.address.model.TAddress;
import com.soft.ware.rest.modular.address.service.ITAddressService;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.Id;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.service.ImService;
import com.soft.ware.rest.modular.auth.service.SmsService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.validator.Validator;
import com.soft.ware.rest.modular.banner.model.TBanner;
import com.soft.ware.rest.modular.banner.service.TBannerService;
import com.soft.ware.rest.modular.goods.controller.dto.CartParam;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_config.model.TOwnerConfig;
import com.soft.ware.rest.modular.owner_config.service.ITOwnerConfigService;
import com.soft.ware.rest.modular.owner_temp.service.ITOwnerTempService;
import com.soft.ware.rest.modular.question.model.TQuestion;
import com.soft.ware.rest.modular.question.service.ITQuestionService;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ITOwnerTempService ownerTempService;

    @Autowired
    private HzcxWxService hzcxWxService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private ImService imService;

    //@Autowired
    //private ISWxAppService appService;

    /**
     * 首页横幅
     * banner
     * @param owner
     * @return
     */
    @RequestMapping(value = "banner/list",method = RequestMethod.GET)
    public Tip banners(SessionUser owner){
        List<TBanner> list = bannerService.findBannerByOwner(owner);
        return render().set("list", list);
    }



    /**
     * 商品分类列表
     * @param user
     * @return
     */
    @RequestMapping(value = "category/list",method = RequestMethod.GET)
    public Tip category(SessionUser user){
        List<Map<String, Object>> list = categoryService.findMaps(Kv.by("pid", null).set("ownerId", user.getOwnerId()));
        return render().set("list", list);
    }


    /**
     * 商品列表
     * @param param
     * @param user
     * @param page
     * @return
     */
    @RequestMapping(value = "goods/list",method = RequestMethod.GET)
    public Tip goodsPage(GoodsPageParam param, SessionUser user, Page page) throws WxErrorException {
        List<Map> list = goodsService.findPage(user, page, param);
        return render().set("list", list);
    }


    /**
     * 商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "goods/{id}",method = RequestMethod.GET)
    public Object goods(@PathVariable String id) {
        List<Map<String, Object>> list = goodsService.findMaps(Kv.by("id", id));
        return render().setOne("goods", list);
    }


    /**
     * 商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "goods/{id}",method = RequestMethod.GET,params = {"flag=goodsNO"})
    public Object goodsByCode(@PathVariable String id) {
        List<Map<String,Object>> list = goodsService.findMaps(Kv.by("owner",id));
        return render().setOne("goods", list);
    }

    /**
     * 商户信息查询
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "shop")
    public Object owner(SessionUser user) {
        Map<String, Object> owner = ownerService.findMap(Kv.by("id", user.getOwnerId()));
        Map<String, Object> app = appService.findMap(Kv.by("ownerId", user.getOwnerId()));
        return render().set("owner", owner).merge("owner", app);
    }




    /**
     * 订单列表查询
     * @param user
     * @param page
     * @param param
     * @return
     */
    @RequestMapping(value = "orders",method = RequestMethod.GET)
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
        List<Map> list = orderService.findPage(user, page, param, TOrder.SOURCE_0, TOrder.SOURCE_2);
        return warpObject(new ListWrapper(list));
    }



    /**
     * 订单详情
     * @param user
     * @param no
     * @return
     */
    @RequestMapping(value = "orders/{no}",method = RequestMethod.GET)
    public Tip orders(SessionUser user,@PathVariable String no) {
        Map<String, Object> map = orderService.findMap(Kv.obj("orderNo", no).set("user", user));
        return render().set("order", map);
    }


    /**
     * 收货地址列表
     * @param user
     * @return
     */
    @RequestMapping(value = "address",method = RequestMethod.GET)
    public Object address(SessionUser user) {
        List<Map<String, Object>> list = addressService.findMaps(Kv.obj("ownerId", user.getOwnerId()).set("creater", user.getOpenId()).set("isDelete", TAddress.is_delete_0).set("orderBy"," is_default desc, created_time desc "));
        return render(list);
    }

    /**
     * 收货地址详情
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "address/{id}",method = RequestMethod.GET)
    public Tip address(SessionUser user, @PathVariable String id){
        Map<String, Object> address = addressService.findMap(Kv.obj().set("id", id).set("creater", user.getOpenId()).set("ownerId", user.getOwnerId()));
        return render().set("address", address);
    }

    /**
     * 删除收货地址
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "address/del",method = RequestMethod.POST)
    public Object addressDel(SessionUser user, @RequestBody Id id, BindingResult result) throws Exception {
        Validator.valid(result);
        Map<String, Object> address = addressService.findMap(Kv.obj().set("id", id.getId()).set("creater", user.getOpenId()).set("ownerId", user.getOwnerId()));
        TAddress a = BeanMapUtils.toObject(address, TAddress.class, true);
        addressService.deleteById(user, a);
        return render();
    }



    /**
     * 添加/编辑 用户地址
     * @param user
     * @param address
     * @return
     */
    @RequestMapping(value = "address/man",method = RequestMethod.POST)
    public Object address(SessionUser user,@RequestBody TAddress address) throws Exception {
        if (address.getId() == null) {
            address.setOwnerId(user.getOwnerId());
            address.setCreater(user.getOpenId());
            address.setCreatedTime(new Date());
            boolean b = addressService.addAddress(user, address);
            return render(b);
        }else{
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
     * 意见反馈
     * @param user
     * @param question
     */
    @RequestMapping(value = "question",method = RequestMethod.POST)
    public Tip question(SessionUser user,@RequestBody TQuestion question){
        boolean b = questionService.add(user,question);
        return render(b);
    }



    /**
     * 查看购物车
     * @param user
     * @param flag
     * @param param
     * @return
     */
    @RequestMapping(value = "cart")
    public Tip owner(SessionUser user, @RequestParam(required = false,defaultValue = "all") String flag, @RequestBody CartParam param) throws Exception {
        int round = BigDecimal.ROUND_HALF_UP;
        List<Integer> nums = param.getNums();
        List<String> units = param.getUnits();//todo yancc unit id 需要查询转换
        String ids = "'" + StringUtils.join(param.getIds(), "','") + "'";
        //查询所有订单
        List<Kv<String, Object>> all = Kv.toKvs(goodsService.findMaps(Kv.obj().set("ownerId", user.getOwnerId()).set("ids", ids)));
        TOwner owner = BeanMapUtils.toObject(ownerService.findMap(Kv.by("id", user.getOwnerId())), TOwner.class);
        TOwnerConfig config = BeanMapUtils.toObject(ownerConfigService.findMap(Kv.obj().set("ownerId", user.getOwnerId())), TOwnerConfig.class);
        if (all.size() != nums.size() || all.size() != param.getIds().size()) {
            render(false, "订单错误！");
        }
        //商品信息
        Kv<String,Object> g;
        //商品总价（不包含配送费）
        BigDecimal total = BigDecimal.ZERO;
        //商品总数
        int count = 0;
        List<Kv<String, Object>> maps = Lists.newArrayList();
        Kv<String,Object> m;
        boolean isPromotion;//是否促销
        BigDecimal price;//商品单价
        BigDecimal promotionPrice = BigDecimal.ZERO;
        BigDecimal num;//商品数量
        BigDecimal goodsMoney;//商品总价（商品单价*商品数量）
        for (int i = 0; i < all.size(); i++) {
            // 计算单个商品的总价（总价 = 购买数量 * 商品单价）
            // let goodsMoney = parseInt(cartGoodsItems[2]) * parseFloat(result.price_unit);
            g = all.get(i);
            isPromotion = g.getBoolean("isPromotion", false);
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
                m.put("name",g.get("name"));
                m.put("pics", g.get("pics"));
                m.put("measurementUnit",g.get("measurementUnit"));
                m.put("price",price);
                m.put("specifications",units.get(i));//todo yancc 要干什么
                m.put("count", num.intValue());
                m.put("total", goodsMoney.setScale(2,round));
                m.put("status",g.get("status"));
                m.put("isPromotion",isPromotion);
                if (isPromotion) {
                   //正常促销
                   m.put("promotionMoney",promotionPrice);
                }else{
                   //没有促销
                   m.put("promotion_price", 0);
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
     * @param param
     * @return
     */
/*
    @RequestMapping(value = "orders/{no}",method = RequestMethod.POST)
    public Tip orders(SessionUser user,@PathVariable String no,@RequestBody PayAfterOrderParam param) throws Exception {
        // 如果是在线支付，则向买家发送【订单支付成功】模板消息
        TOrder order = BeanMapUtils.toObject(orderService.findMap(Kv.obj().set("orderNo", no).set("creater", user.getOpenId())), TOrder.class);
        long current = System.currentTimeMillis();
        String pack = param.getPack();
        SWxApp app = appService.find(user);
        if (param.getMoneyChannel() == TOrder.SOURCE_0) {
            String tempKey = "ms:ppi:" + no;
            redisTemplate.opsForValue().set(tempKey,pack,604800, TimeUnit.SECONDS);
            logger.debug("买家支付订单时保存PrepayID {} = {}",tempKey,pack);
            // 微信支付时发送模板消息
            String pay = ownerTempService.getTplId(user, "pay");
            WxMaService service = hzcxWxService.getWxMaService(app);
            List<WxMaTemplateData> msList = new ArrayList<>();
            // 订单编号
            msList.add(new WxMaTemplateData("keyword1", no));
            // 下单时间
            msList.add(new WxMaTemplateData("keyword2", DateUtil.format(param.getCreated_at(),"YYYY-MM-DD HH:mm:ss")));
            // 订单金额
            msList.add(new WxMaTemplateData("keyword3", order.getPayMoney() + "元"));
            // 商品名称
            msList.add(new WxMaTemplateData("keyword4", param.getGoodsName()));
            // 收货地址
            msList.add(new WxMaTemplateData("keyword5", param.getAddress()));
            // 备注信息
            msList.add(new WxMaTemplateData("keyword6", "如有疑问，请进入小程序联系商家"));
            orderService.buildOrderTemplateMessage(pay, pack, order);
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
            smsService.sendNotify(phone, WXContants.TENCENT_TEMPLATE_ID4, param.gevoidNO());
        }
        // IM通知店铺
        imService.sendNewOrderNotify(user, order);
        String tempKey = "ms:fit:" + param.gevoidNO();
        redisTemplate.opsForValue().set(tempKey,param.getFormID(), 604800,TimeUnit.SECONDS);
        logger.info("买家支付订单时保存FormID {} = {}", tempKey, param.getFormID());
        // 标识订单来源
        if (Integer.valueOf(TOrder.SOURCE_2).equals(param.getSource())) {
            //计数器加1
            redisTemplate.opsForValue().increment("counter:" + user.getAppId(), 1);
            Object s = redisTemplate.opsForValue().get("counter:" + user.getAppId());
            order.setPickupNo(Long.valueOf(s.toString()));
            order.setPickupTime(new Date(current));
            order.setRunMoney(BigDecimal.ZERO);
            order.setPayMoney(order.getPayMoney());

        } else {
            order.setPickupTime(null);
            order.setPickupNo(null);
        }
        order.setSource(param.getSource());
        order.setMoneyChannel(param.getMoneyChannel());
        order.setRemark(param.getRemark());
        if (TOrder.MONEY_CHANNEL_1.equals(order.getMoneyChannel())) {
            order.setStatus(TOrder.MONEY_CHANNEL_1);
        }
        if (StringUtils.isNotBlank(param.getTelephone())) {
            order.setConsigneeMobile(param.getTelephone());
        }
        boolean update = orderService.update(order, new EntityWrapper<>(new TOrder().setId(order.getId()).setOwnerId(user.getOwnerId()).setStatus(order.getStatus())));
        return render(update);
    }
*/



}
