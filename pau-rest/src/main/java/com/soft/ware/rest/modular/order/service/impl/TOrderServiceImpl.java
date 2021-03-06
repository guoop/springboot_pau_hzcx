package com.soft.ware.rest.modular.order.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.common.collect.Lists;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.modular.address.model.TAddress;
import com.soft.ware.rest.modular.address.service.ITAddressService;
import com.soft.ware.rest.modular.auth.controller.dto.OrderDeleteParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.util.RegexUtils;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.goods.model.TUnit;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import com.soft.ware.rest.modular.goods.service.ITUnitService;
import com.soft.ware.rest.modular.order.controller.dto.CreateOrderParam;
import com.soft.ware.rest.modular.order.dao.TOrderMapper;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.model.TOrderChild;
import com.soft.ware.rest.modular.order.model.TRefund;
import com.soft.ware.rest.modular.order.service.ITOrderChildService;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import com.soft.ware.rest.modular.order.service.ITRefundService;
import com.soft.ware.rest.modular.order_money_diff.model.TOrderMoneyDiff;
import com.soft.ware.rest.modular.order_money_diff.service.ITOrderMoneyDiffService;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_config.model.TOwnerConfig;
import com.soft.ware.rest.modular.owner_config.service.ITOwnerConfigService;
import com.soft.ware.rest.modular.owner_temp.service.ITOwnerTempService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * 线上订单信息 服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@Service
@Transactional
public class TOrderServiceImpl extends BaseService<TOrderMapper, TOrder> implements ITOrderService {

    @Resource
    private TOrderMapper orderMapper;

    @Autowired
    private ITGoodsService goodsService;

    @Autowired
    private ITOwnerService ownerService;

    @Autowired
    private ITAddressService addressService;

    @Autowired
    private ITOwnerConfigService configService;

    @Autowired
    private ITUnitService unitService;

    @Autowired
    private ITOrderChildService orderChildService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private HzcxWxService hzcxWxService;

    @Autowired
    private ITAddressService tAddressService;

    @Autowired
    private ISWxAppService isWxAppService;

    @Autowired
    private ISWxAppService appService;

    @Autowired
    private WXContants wxContants;

    @Autowired
    private ITOrderMoneyDiffService orderMoneyDiffService;

    @Autowired
    private ITRefundService itRefundService;

    @Autowired
    private ITOwnerTempService ownerTempService;



    @Override
    public List<Map<String,Object>> findPage(SessionUser user, Page page, OrderPageParam param, Integer... sources) {
        Kv<String, Object> map = Kv
                .obj("creater", user.getOpenId())
                .set("ownerId", user.getOwnerId())
                .set("page", page)
                .set("status", param.getStatus())
                .set("isDelete", TOrder.is_delete_0)
                .set("sources", "'" + StringUtils.join(sources, "','") + "'")
                .set("orderBy", "a.create_time desc");
        long count = findPageCount(user, param,sources);
        page.setTotal(count);
        return findMaps(map);
    }

    @Override
    public long findPageCount(SessionUser user, OrderPageParam param, Integer... sources){
        Wrapper<TOrder> wrapper = new EntityWrapper<>(new TOrder().setOwnerId(user.getOwnerId()).setCreater(user.getOpenId()).setIsDelete(TOrder.is_delete_0)).in("status", param.getStatus()).in("source", sources);
        return selectCount(wrapper);
    }

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        List<Map<String, Object>> maps = orderMapper.findMaps(map);
        maps.forEach(s->s.remove("payResponse"));
        return maps;
    }

    @Override
    public Kv<String, Object> findMap(Map<String, Object> map) {
        List<Map<String, Object>> maps = findMaps(map);
        return maps.size() == 1 ? Kv.toKv(maps.get(0)) : null;
    }

    @Override
    public List<HashMap<String, Object>> selectOrdersListByMap(Map<String, Object> map,Page page) {
        int count = orderMapper.selectOrdersListByMapCount(map,page);
        page.setTotal(count);
        List<HashMap<String,Object>> listMap = orderMapper.selectOrdersListByMap(map,page);
        List<HashMap<String,Object>> resultList = new ArrayList<>();
        if(listMap.size() > 0){
            for (int i = 0; i < listMap.size(); i++) {
                HashMap<String,Object>  resultMap = listMap.get(i);
                if(resultMap.get("source").toString().equals("2")){
                   resultMap.remove("address");
                   resultMap.remove("addressId");
                }
                if(ToolUtil.isNotEmpty(resultMap.get("id").toString())){
                    Map<String,Object> param = new HashMap<>();
                    param.put("orderId",resultMap.get("id").toString());
                    List<TOrderChild> orderChildList = orderChildService.selectOrderChildListByMap(param);
                    resultMap.put("goodsList",orderChildList);
                }
                resultList.add(resultMap);
            }
        }
        return resultList;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean orderRefund(Map<String, Object> paramMap, SessionUser sessionUser) throws Exception {
        Kv<String, Object> param = Kv.toKv(paramMap);
        String orderNo = param.getStr("orderNo");
        String refundType = param.requiredStr("refundType");
        String refundReason = param.getStr("refundReason", "");
        int round = WXContants.big_decimal_sale;
        BigDecimal v100 = BigDecimal.valueOf(100);
        TOrder tOrder =  orderMapper.selectOne(new TOrder().setOrderNo(orderNo));
        List<Map<String,Object>> childOrders = orderChildService.findMaps(Kv.by("orderId", tOrder.getId()));
        List<String> goodsNames = childOrders.stream().map(map -> map.get("goodsName") + "").collect(Collectors.toList());
        SWxApp app = isWxAppService.find(sessionUser);
        WxPayService service = hzcxWxService.getWxPayService(app);

        boolean isSuccess = false;
        //如果相关联的订单下面涉及到补差价时，首先先把差价退掉其次退订单或者反之判断
        TOrderMoneyDiff tOrderMoneyDiff = orderMoneyDiffService.selectOne(new EntityWrapper<>(new TOrderMoneyDiff().setOrderNo(orderNo)));
        if(ToolUtil.isNotEmpty(tOrderMoneyDiff)){
            if(tOrderMoneyDiff.getStatus() == 1){
                TRefund diffRefund = new TRefund();
                diffRefund.setOrderNo(tOrderMoneyDiff.getNo());
                diffRefund.setReason(param.getStr("refundReason"));
                diffRefund.setCreateTime(new Date());
                diffRefund.setCreater(sessionUser.getPhone());
                diffRefund.setId(IdGenerator.getId());
                diffRefund.setOrderMoney(tOrderMoneyDiff.getMoney());
                diffRefund.setNo(IdGenerator.getOrderNo());
                diffRefund.setStatus(TRefund.status_1);
                isSuccess = itRefundService.insert(diffRefund);
                if (!isSuccess) {
                    throw new PauException(BizExceptionEnum.ERROR);//数据库造作失败回滚
                }
                WxPayRefundRequest req = WxPayRefundRequest
                        .newBuilder()
                        .outTradeNo(tOrderMoneyDiff.getNo())
                        .outRefundNo(diffRefund.getNo())
                        .totalFee(tOrder.getPayMoney().multiply(v100).intValue())
                        .refundFee(tOrderMoneyDiff.getMoney().multiply(v100).intValue())
                        .refundDesc(param.getStr("refundReason"))
                        .build();
                WxPayRefundResult  result = service.refund(req);
                if (!"SUCCESS".equals(result.getReturnCode())) {
                    throw new PauException(result.getReturnMsg());
                }
                logger.info("微信退款请求执行成功:订单号{}", tOrderMoneyDiff.getNo());
                String formID = redisTemplate.opsForValue().get("ms:diff_form_id:" + tOrderMoneyDiff.getNo());
                WxMaTemplateMessage msg = buildOrderTemplateMessage(sessionUser,"refund", formID, tOrder,goodsNames,null);
                msg.getData().add(new WxMaTemplateData("keyword7", "到账金额以微信到账金额为准，请知晓"));
                msg.getData().set(4, new WxMaTemplateData("keyword5", "all".equals(refundType) ? tOrderMoneyDiff.getMoney().setScale(2, round) + "元" : diffRefund.getOrderMoney().setScale(2, round) + "元"));
                msg.getData().add(new WxMaTemplateData("keyword8", "如有疑问，请进入小程序联系商家"));
                msg.getData().add(new WxMaTemplateData("keyword6",refundReason));
                hzcxWxService.getWxMaService(app).getMsgService().sendTemplateMsg(msg);
            }else{
                throw new PauException(BizExceptionEnum.ORDER_REFUND_NOT_SUPPORT);
            }
        }
        TRefund tRefund  = itRefundService.selectOne(new EntityWrapper<>(new TRefund().setOrderNo(orderNo)));
        //
       /* if (tOrder.getMoneyChannel() != TOrder.MONEY_CHANNEL_0 || tOrder.getMoneyChannel() != TOrder.MONEY_CHANNEL_3 ) {
            throw new PauException(BizExceptionEnum.ORDER_REFUND_NOT_SUPPORT);
        }*/
        // 退款金额
        BigDecimal refundMoney;
        // 全额退款
        if ("all".equals(refundType)) {
            // 全额退款则意味着取消订单
            tOrder.setConfirmer(sessionUser.getPhone());
            tOrder.setCancelReason(refundReason);
            tOrder.setCancelTime(new Date());
            tOrder.setStatus(TOrder.STATUS_01);//订单取消
            isSuccess = updateByVersion(tOrder);
            refundMoney = tOrder.getPayMoney();
        } else {
            tOrder.setStatus(TOrder.STATUS_03);//订单完成
            refundMoney = param.requiredBigDecimal("refundMoney");
            isSuccess = updateByVersion(tOrder);
        }
        if (!isSuccess) {
            throw new PauException(BizExceptionEnum.ERROR);//数据库造作失败回滚
        }
       /* List<TOrderMoneyDiff> diffList = orderMoneyDiffService.selectList(new EntityWrapper<>(new TOrderMoneyDiff().setOrderNo(tOrder.getOrderNo())));
        //去掉差价
        for (TOrderMoneyDiff diff : diffList) {
            refundMoney = refundMoney.add(diff.getMoney());//加上差价 正数或者负数
        }*/
        //先执行操作，在发送通知，发送失败可以回滚
        if(ToolUtil.isEmpty(tRefund)){
            tRefund= new TRefund();
            tRefund.setStatus(TRefund.status_1);//退款成功
            tRefund.setCreater(sessionUser.getPhone());
            tRefund.setCreateTime(new Date());
            tRefund.setReason(refundReason);
            tRefund.setOrderNo(tOrder.getOrderNo());
            tRefund.setOrderMoney(tOrder.getOrderMoney());
            tRefund.setId(IdGenerator.getId());
            tRefund.setNo(IdGenerator.getOrderNo());
            isSuccess = itRefundService.insert(tRefund);
        }
        if (!isSuccess) {
            throw new PauException(BizExceptionEnum.ERROR);//数据库造作失败回滚
        }
        WxPayRefundRequest req = WxPayRefundRequest
                .newBuilder()
                .outTradeNo(tOrder.getOrderNo())
                .outRefundNo(tRefund.getNo())
                .totalFee(tOrder.getPayMoney().multiply(v100).intValue())
                .refundFee(refundMoney.multiply(v100).intValue())
                .refundDesc(refundReason)
                .build();
        WxPayRefundResult  result = service.refund(req);
        if (!"SUCCESS".equals(result.getReturnCode())) {
            throw new PauException(result.getReturnMsg());
        }
        if (!"SUCCESS".equals(result.getResultCode())) {
            throw new PauException(result.getErrCodeDes());
        }
        logger.info("微信退款请求执行成功:订单号{}", tOrder.getOrderNo());
        String formID = redisTemplate.opsForValue().get("ms:ppi:" + tOrder.getOrderNo());
        WxMaTemplateMessage msg = buildOrderTemplateMessage(sessionUser,"refund", formID, tOrder,goodsNames,null);
        msg.getData().set(4, new WxMaTemplateData("keyword5", "all".equals(refundType) ? tOrder.getPayMoney().setScale(2, round) + "元" : refundMoney.setScale(2, round) + "元"));
        msg.getData().add(new WxMaTemplateData("keyword6",refundReason));
        msg.getData().add(new WxMaTemplateData("keyword7", "到账金额以微信到账金额为准，请知晓"));
        msg.getData().add(new WxMaTemplateData("keyword8", "如有疑问，请进入小程序联系商家"));
        hzcxWxService.getWxMaService(app).getMsgService().sendTemplateMsg(msg);
        return true;
    }


    @Override
    public WxMaTemplateMessage buildOrderTemplateMessage(SessionUser user,String templateKey, String fromID, TOrder order, List<String> gs, TAddress address) throws Exception {
        String templateID = ownerTempService.getTplId(user , templateKey);
        StringBuilder goodsName = new StringBuilder();
        int i = 0;
        do {
            goodsName.append(",");
            goodsName.append(gs.get(i));
            if (i == 2) {
                //最多拼接三个
                break;
            }
            i++;
        } while (i < gs.size() && i < 3);
        if (gs.size() > 3) {
            goodsName.append("...共").append(gs.size()).append("种商品");
        }
        goodsName.replace(0, 1, "");//删除，号
        ArrayList<WxMaTemplateData> data = Lists.newArrayList();
        data.add(new WxMaTemplateData("keyword1", order.getOrderNo()));// 订单编号
        data.add(new WxMaTemplateData("keyword2", DateUtil.format(order.getCreateTime(), WXContants.date_format)));// 下单时间
        data.add(new WxMaTemplateData("keyword3", order.getPayMoney().setScale(2, WXContants.big_decimal_sale) + ""));// 订单金额
        data.add(new WxMaTemplateData("keyword4", goodsName.toString()));// 商品名称
        if (TOrder.SOURCE_0.equals(order.getSource()) && ToolUtil.isNotEmpty(address)) {
            data.add(new WxMaTemplateData("keyword5", address.getName() + ' ' + address.getPhone() + ' ' + address.getProvince() + " " + address.getDetail()));// 收货地址
        } else {
            data.add(new WxMaTemplateData("keyword5", ""));
        }
        WxMaTemplateMessage msg = WxMaTemplateMessage.builder()
                .templateId(templateID)
                .formId(fromID)
                .toUser(order.getCreater())
                .page("pages/mine/orderForShow?orderNO=" + order.getOrderNo())
                .data(data)
                .build();
        return msg;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public TOrder createMiniAppOrder(SessionUser user, CreateOrderParam param) throws Exception {
        int round = BigDecimal.ROUND_HALF_UP;
        String ids = "'" + StringUtils.join(param.getIds(), "','") + "'";
        List<Kv<String, Object>> list = Kv.toKvs(goodsService.findMaps(Kv.obj().set("ownerId", user.getOwnerId()).set("ids", ids)));
        Map<String, Kv<String, Object>> goodsMap = list.stream().collect(Collectors.toMap(s -> s.getStr("id"), s -> s));
        //Map<String, Object> id = addressService.findMap(Kv.by("id", param.getAddressId()));
        long current = System.currentTimeMillis();
        BigDecimal total = BigDecimal.ZERO;
        boolean isPromotion;
        BigDecimal price;  // 商品单价
        BigDecimal num;
        Kv<String,Object> g;
        Kv<String, Object> spec;
        TUnit unit;
        TOrderChild c;
        for (int i = 0; i < param.getGoods().size(); i++) {
            c = param.getGoods().get(i);
            g = goodsMap.get(c.getGoodsId());
            isPromotion = g.getBoolean("isPromotion", false);
            price = g.getBigDecimal("price");
            num = BigDecimal.valueOf(c.getGoodsNum());
            // 只计算在售商品
            if (TGoods.status_1.equals(g.getInt("status"))) {
                // let goodsPrice = parseFloat(result.price_unit);             // 商品单价
                // let goodsMoney = parseInt(cartGoodsItems[2]) * goodsPrice;  // 商品总价 = 商品单价 * 购买数量
                BigDecimal goodsMoney = num.multiply(price);   // 商品总价 = 商品单价 * 购买数量
                // 判断是否促销商品
                if (isPromotion) {
                    price = g.getBigDecimal("promotionMoney");                           // 商品促销单价
                    goodsMoney = num.multiply(price);   // 商品总价 = 商品促销单价 * 购买数量
                }
                //设置总价格，下面的保存订单逻辑要用到
                param.setTotal(goodsMoney, i);
                total = total.add(goodsMoney);

                final String specId = c.getGoodsSpecId();
                spec = g.getRequiredList("specs").stream().filter(s -> s.get("id").equals(specId)).findFirst().orElse(Kv.obj());
                c.setGoodsPic(g.getStr("pics"));
                c.setGoodsPrice(price);
                c.setGoodsPromotionId(g.getStr("promotionId"));
                c.setGoodsSpecId(spec.getStr("id"));
                c.setGoodsUnitId(g.getStr("unitId"));
                c.setGoodsName(g.getStr("name"));
                c.setTotalPrice(param.getTotal(i));
                unit = unitService.selectById(c.getGoodsUnitId());

                if (g.get("code") != null && g.get("code").toString().length() == 5) {
                    //称重商品
                    String a = RegexUtils.find(unit.getName(), Pattern.compile("\\d+"), "1").get(0);
                    String b = RegexUtils.find(unit.getName(), Pattern.compile("[^\\d]"), "").get(0);
                    BigDecimal cc = BigDecimal.valueOf(Float.valueOf(a)).multiply(num);
                }
            }
        }

        TOwnerConfig config = BeanMapUtils.toObject(configService.findMap(Kv.by("ownerId", user.getOwnerId())), TOwnerConfig.class);
        BigDecimal goodsMoney = total;   // 商品总价（不包含配送费）
        BigDecimal actualFee; // 运费
        if (goodsMoney.compareTo(config.getDeliveryMoney()) < 0) {
            // 配送费取delivery_less_money
            actualFee = config.getDeliveryLessMoney();
        } else {
            // 配送费取delivery_great_money
            actualFee = config.getDeliveryGreatMoney();
        }
        final String orderNO =  IdGenerator.getOrderNo();
        TOrder o = new TOrder();
        o.setId(IdGenerator.getId());
        o.setOrderNo(orderNO);
        o.setMoneyChannel(TOrder.MONEY_CHANNEL_0);
        o.setOrderMoney(goodsMoney.setScale(2, round));
        o.setRunMoney(actualFee.setScale(2, round));
        o.setPayMoney(goodsMoney.add(actualFee).setScale(2, round));
        o.setCreateTime(new Date(current));
        o.setCreater(user.getOpenId());
        o.setOwnerId(user.getOwnerId());
        o.setStatus(TOrder.STATUS_0);
        o.setVersion(0);
        boolean result = false;
        for (TOrderChild oc : param.getGoods()) {
            oc.setOrderId(o.getId());
            oc.setId(IdGenerator.getId());
            oc.setCreateTime(o.getCreateTime());
            result = orderChildService.insert(oc);
            if (!result) {
                break;
            }
        }
        if (!this.insert(o) || !result) {
            throw new PauException(BizExceptionEnum.ORDER_CREATE_FAIL);
        }
        String tempKey = "ms:fio:" + orderNO;
        redisTemplate.opsForValue().set(tempKey,param.getFormID(), 604800, TimeUnit.SECONDS);
        logger.debug("买家下单时保存FormID {tempKey} = {req.body.formID}", tempKey, param.getFormID());
        return o;
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean updatePayCallback(WxPayOrderNotifyResult result, SessionUser user,TOrder order) throws Exception {
        user.setOpenId(result.getOpenid());
        Integer beforeStatus = order.getStatus();
        order.setPayTime(DateUtil.parse(result.getTimeEnd(),"yyyyMMddHHmmss"));
        order.setStatus(TOrder.STATUS_1);
        Map<String, Object> map = BeanMapUtils.toMap(result, true);
        order.setPayResponse(JSON.toJSONString(map));
        Integer version = order.getVersion();
        //todo yancc 订单更新失败怎么办
        order.setVersion(order.getVersion() + 1);
        return this.update(order, new EntityWrapper<>(new TOrder().setId(order.getId()).setStatus(beforeStatus).setVersion(version)));
    }

    @Override
    public boolean customerDelete(SessionUser user, OrderDeleteParam param) {
        int i = orderMapper.customerDelete(user, param);
        //update t_order set status = -3 where owner_id = #{user.ownerId} and order_no = #{param.no} and creater = #{user.openId} and status in (-2, -1, 0, 3);
        if (i == 1) {
            return true;
        } else {
            Map<String, Object> order = findMap(Kv.by("orderNo", param.getNo()));
            logger.info("订单删除失败：{} 当前订单状态{}", param.getNo(), order.get("orderNo"));
            throw new PauException(BizExceptionEnum.UPDATE_ERROR);
        }
    }

    @Override
    public boolean customerCancel(SessionUser user, OrderDeleteParam param) {
        int i = orderMapper.customerCancel(user, param);
        if (i != 1) {
            Map<String, Object> order = findMap(Kv.by("orderNo", param.getNo()));
            logger.info("订单取消失败：{} 当前订单状态{}", param.getNo(), order.get("orderNo"));
            throw new PauException(BizExceptionEnum.UPDATE_ERROR);
        }
        return true;
    }

    @Override
    public int selectOrderCount(String ownerId) {
    return orderMapper.selectOrderCount(ownerId);
    }

    @Override
    @Transactional
    public Map<String,Object> selectOrderDetailById(String orderNo) {
        Map<String,Object> tOrder = orderMapper.selectOrderDetailById(orderNo);
        if(ToolUtil.isNotEmpty(tOrder)){
            if(ToolUtil.isNotEmpty(tOrder.get("addressId"))){
                TAddress tAddress=tAddressService.selectById(tOrder.get("addressId").toString());
                tOrder.put("addressInfo",tAddress);
            }
            Map<String,Object> param = new HashMap<>();
            param.put("orderId",tOrder.get("id").toString());
            List<TOrderChild>  childList = orderChildService.selectOrderChildListByMap(param);
            tOrder.put("childList",childList);
        }
        return tOrder;
    }

    @Override
    public boolean updateByVersion(TOrder order) {
        Integer version = order.getVersion();
        order.setVersion(version+1);
        return super.update(order, new EntityWrapper<>(new TOrder().setId(order.getId()).setVersion(version)));
    }

    public WxPayMpOrderResult pay(SWxApp app, SessionUser user, String no, Integer total_fee, String notifyUrl, String body, String attach, String ip) throws WxPayException {
        WxPayUnifiedOrderRequest req = WxPayUnifiedOrderRequest
                .newBuilder()
                .body(body)
                .attach(attach)
                .notifyUrl(notifyUrl)
                .openid(user.getOpenId())
                .outTradeNo(no)
                .spbillCreateIp(ip)
                .tradeType(WxPayConstants.TradeType.JSAPI)
                .totalFee(total_fee).build();
        return hzcxWxService.getWxPayService(app).createOrder(req);
    }


    @Override
    public boolean orderSignStatu(SessionUser sessionUser,Map<String, Object> param) {
        boolean isSuccess = false;
        Integer updateNum = 0;
        SWxApp sWxApp = isWxAppService.find(new TOwner().setId(sessionUser.getOwnerId()));
        WxMaService service = hzcxWxService.getWxMaService(sWxApp);
        TOrder tOrder = new TOrder();
        tOrder.setOrderNo(param.get("orderNo").toString());
        tOrder = orderMapper.selectOne(tOrder);
        List<Map<String,Object>> childOrders = orderChildService.findMaps(Kv.by("orderId", tOrder.getId()));
        List<String> goodsNames = childOrders.stream().map(map -> map.get("goodsName") + "").collect(Collectors.toList());
        TAddress address = null;
        if(ToolUtil.isNotEmpty(tOrder.getAddressId())){
            address = tAddressService.selectById(tOrder.getAddressId());
        }
        String status = param.get("status").toString();
        switch (status){
            //待商家确认,商家确认接单
            case "deliver":
                // 只允许对已经经过商家确认的订单进行配送
                try {
                    if (tOrder.getStatus().equals(TOrder.STATUS_10)) {
                        tOrder.setStatus(TOrder.STATUS_2);
                        tOrder.setDistributioner(sessionUser.getPhone());
                        tOrder.setDistributionTime(new Date());
                        updateNum = orderMapper.updateById(tOrder);
                        //update = this.update(order, new EntityWrapper<>(new TblOrder().setId(order.getId()).setOwner(user.getOwnerId())));
                            logger.info("配送订单 - ", tOrder.getOrderNo());
                            String templateFormId = redisTemplate.opsForValue().get("ms:fit:" + tOrder.getOrderNo());
                            WxMaTemplateMessage msg = this.buildOrderTemplateMessage(sessionUser,"deliver", templateFormId, tOrder,goodsNames,address);
                            msg.getData().add(new WxMaTemplateData("keyword6", "配送人员已经开始为您配送，请保持手机畅通"));// 温馨提示
                            msg.getData().add(new WxMaTemplateData("keyword7", "如有疑问，请进入小程序联系商家"));// 备注信息
                            service.getMsgService().sendTemplateMsg(msg);

                    } else {
                        throw new PauException(BizExceptionEnum.ORDER_DELIVER_FAIL);
                    }
                } catch (WxErrorException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "confirm":
                try {
                    if (tOrder.getStatus().equals(TOrder.STATUS_1)) { //如果当前订单状态是待商家确认订单
                        tOrder.setStatus(TOrder.STATUS_10);//那么订单状态设置商家确认订单
                        tOrder.setConfirmer(tOrder.getConfirmer());
                        tOrder.setConfirmTime(new Date());
                        updateNum = orderMapper.updateById(tOrder);
                        logger.info("确认订单 - {}", tOrder.getOrderNo());
                        String templateFormId = redisTemplate.opsForValue().get("ms:fio:" + tOrder.getOrderNo());
                        WxMaTemplateMessage msg = this.buildOrderTemplateMessage(sessionUser,"confirm", templateFormId,tOrder,goodsNames,address );
                        msg.getData().add(new WxMaTemplateData("keyword6",DateUtil.format(tOrder.getCreateTime(), "YYYY-MM-dd HH:mm:ss")));// 确认时间
                        msg.getData().add(new WxMaTemplateData("keyword7", "如有疑问，请进入小程序联系商家"));// 备注信息*/
                        service.getMsgService().sendTemplateMsg(msg);
                    } else {
                        throw new PauException(BizExceptionEnum.ORDER_CONFIRM_FAIL);
                    }
                } catch (WxErrorException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            //已完成
            case "done":
                // 只允许对配送中的订单进行标记完成操作
                if (tOrder.getStatus().equals(TOrder.SOURCE_2)) {
                    tOrder.setStatus(TOrder.STATUS_3);
                    tOrder.setDoner(sessionUser.getPhone());
                    tOrder.setDoneTime(new Date());
                    updateNum = orderMapper.updateById(tOrder);
                } else {
                    throw new PauException(BizExceptionEnum.ORDER_DONE_FAIL);
                }
                //手动取消
            case "cancel":
                //如果支付方式是货到付款，
                if(tOrder.getMoneyChannel() == TOrder.MONEY_CHANNEL_1 && tOrder.getStatus() == TOrder.STATUS_1) {
                    try {
                        tOrder.setStatus(TOrder.STATUS_1);
                        tOrder.setCanceler(param.get("openId").toString());
                        tOrder.setCancelTime(new Date());
                        tOrder.setCancelReason(param.get("concelReason").toString());
                        updateNum = orderMapper.updateById(tOrder);
                        String templateFormId = redisTemplate.opsForValue().get("ms:fio:" + tOrder.getOrderNo());
                        //订单下的子订单商品名称
                        WxMaTemplateMessage msg = this.buildOrderTemplateMessage(sessionUser,"cancel", templateFormId, tOrder,goodsNames,address);
                        msg.getData().add(new WxMaTemplateData("keyword6",tOrder.getCancelReason()));// 取消原因
                        msg.getData().add(new WxMaTemplateData("keyword7", "如有疑问，请进入小程序联系商家"));// 备注信息
                        service.getMsgService().sendTemplateMsg(msg);
                    } catch (WxErrorException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        if(updateNum > 0){
            return true;
        }
        return false;
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public WxPayMpOrderResult unifiedorder(SessionUser user, String no, Integer source, String spbill_create_ip, String phone, String remark,Date pickupTime) throws Exception {
        TOrder order = BeanMapUtils.toObject(this.findMap(Kv.obj("creater", user.getOpenId()).set("orderNo", no)), TOrder.class);
        SWxApp app = appService.find(user);
        long current = System.currentTimeMillis();
        boolean source2 = Integer.valueOf(TOrder.SOURCE_2).equals(source);
        // 商品描述
        String body = "购买商品";
        // 支付成功的回调地址  可访问 不带参数
        String notify_url = wxContants.getCustomerPayHost() + wxContants.getCustomerPay();
        // 订单价格 单位是 分
        int total_fee = order.getOrderMoney().multiply(BigDecimal.valueOf(100)).add(order.getRunMoney().multiply(BigDecimal.valueOf(100))).intValue();
        // 商户订单号
        order.setSource(source);
        order.setRemark(remark);
        //暂不支持
        /*if (TOrder.MONEY_CHANNEL_1.equals(order.getMoneyChannel())) {
            //货到付款等待商家确认
            order.setStatus(TOrder.STATUS_1);
        }*/
        if (source2) {
            if (StringUtils.isBlank(phone)) {
                throw new PauException(BizExceptionEnum.SMS_ERROR_PHONE_FORMAT);
            }
            order.setPhone(phone);
            notify_url = wxContants.getCustomerPayHost() + wxContants.getCustomerPayPickup();
            total_fee = order.getOrderMoney().multiply(BigDecimal.valueOf(100)).intValue();
            //修改支付金额
            order.setPayMoney(order.getOrderMoney());
            //清除运费
            order.setRunMoney(BigDecimal.ZERO);
            //设置取货码
            redisTemplate.opsForValue().increment("counter:" + user.getAppId(), 1);
            Object s = redisTemplate.opsForValue().get("counter:" + user.getAppId());
            order.setPickupNo(Integer.valueOf(s.toString()));
            //设置取货时间
            order.setPickupTime(pickupTime == null ? new Date(current) : pickupTime);
            order.setMoneyChannel(TOrder.MONEY_CHANNEL_3);//仅支持微信支付
            this.updateByVersion(order);
        } else {
            order.setPickupTime(null);
            order.setPickupNo(null);
            this.updateByVersion(order);
        }
        WxPayMpOrderResult result = this.pay(app, user, no, total_fee, notify_url, body, no, spbill_create_ip);
        try {
            //保存支付package 用来发送通知
            String tempKey = "ms:ppi:" + no;
            String pack = result.getPackageValue().substring("prepay_id=".length());
            redisTemplate.opsForValue().set(tempKey, pack, 604800, TimeUnit.SECONDS);
            logger.debug("买家支付订单时保存PrepayID {} = {}", tempKey, result.getPackageValue());
        } catch (Exception e) {
            e.printStackTrace();
            //不影响支付结果返回
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public WxPayMpOrderResult unifiedorderDiff(SessionUser user, String no,String spbill_create_ip) throws Exception {
        Map<String, Object> map = orderMoneyDiffService.findMap(Kv.obj("no", no).set("ownerId", user.getOwnerId()));
        TOrderMoneyDiff diff = BeanMapUtils.toObject(map, TOrderMoneyDiff.class);
        SWxApp app = appService.find(user);
        String remark = "无";
        // 商品描述
        String body = "购买商品";
        // 支付成功的回调地址  可访问 不带参数
        String notify_url = wxContants.getCustomerPayHost() + wxContants.getCustomerPayDiff();
        // 订单价格 单位是 分
        Integer total_fee = diff.getMoney().multiply(BigDecimal.valueOf(100)).intValue();
        return this.pay(app, user, no, total_fee, notify_url, body, remark, spbill_create_ip);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean diffMoney(Map<String, Object> paramMap, SessionUser sessionUser) throws Exception {
        Kv<String,Object> param = Kv.toKv(paramMap);String no = param.requiredStr("orderNo");
        BigDecimal money = param.getBigDecimal("money");
        String pic = param.getStr("pic");
        String reason = param.getStr("refundReason", "小票差额");
        Date date = new Date();
        boolean isSuccess;
        TOrder tOrder = orderMapper.selectOne(new TOrder().setOrderNo(no));
        String orderNO = tOrder.getOrderNo();
        TOrderMoneyDiff  diff = orderMoneyDiffService.selectOne(new EntityWrapper<>(new TOrderMoneyDiff().setOrderNo(orderNO)));
        if (diff != null) {
            if (TOrderMoneyDiff.status_1.equals(diff.getStatus())) {
                throw new PauException(BizExceptionEnum.ORDER_REFUND_FINISHED);
            }
        }
        SWxApp sWxApp = isWxAppService.find(new TOwner().setId(sessionUser.getOwnerId()));
        WxPayService service = hzcxWxService.getWxPayService(sWxApp);
        diff = new TOrderMoneyDiff();
        diff.setId(IdGenerator.getId());
        diff.setStatus(TOrderMoneyDiff.status_0);
        diff.setTicketMoney(money);
        diff.setNo(IdGenerator.getOrderNo());
        diff.setOwnerId(sessionUser.getOwnerId());
        diff.setOrderNo(orderNO);
        diff.setPayTime(date);
        BigDecimal payMoney = tOrder.getPayMoney();
        diff.setMoney(money.subtract(payMoney));
        diff.setCreater(sessionUser.getPhone());
        diff.setCreateTime(date);
        diff.setPic(pic);
        diff.setReason(reason);
        logger.info("小票"+money+"支付金额"+payMoney+"差价"+money.subtract(payMoney));
        //退差价
        if(money.compareTo(payMoney) < 0){

            diff.setPayTime(tOrder.getPayTime());
            diff.setStatus(TOrderMoneyDiff.status_1);//直接退款成功，否则下面抛出异常回滚
            isSuccess = orderMoneyDiffService.insert(diff);
            if (isSuccess) {
                BigDecimal refundFee = diff.getMoney().abs();
                WxPayRefundRequest req = WxPayRefundRequest
                        .newBuilder()
                        .outTradeNo(orderNO)
                        .outRefundNo(diff.getNo())
                        .totalFee(tOrder.getPayMoney().multiply(BigDecimal.valueOf(100)).intValue())
                        .refundFee(refundFee.multiply(BigDecimal.valueOf(100)).intValue())
                        .notifyUrl(wxContants.getCustomerPayHost() + wxContants.getCustomerPayDiffRefund())
                        .build();
                WxPayRefundResult refund = service.refund(req);
                if (!"SUCCESS".equals(refund.getReturnCode())) {
                    throw new PauException(refund.getReturnMsg());
                }
                if (!"SUCCESS".equals(refund.getResultCode())) {
                    throw new PauException(refund.getErrCodeDes());
                }
            }
            //补差价
        }else if(money.compareTo(payMoney) > 0){
            TAddress address = addressService.findById(sessionUser, tOrder.getAddressId());
            List<Map<String,Object>> childOrders = orderChildService.findMaps(Kv.by("orderId", tOrder.getId()));
            List<String> goodsNames = childOrders.stream().map(map -> map.get("goodsName") + "").collect(Collectors.toList());
            isSuccess = orderMoneyDiffService.insert(diff);
            String formID = redisTemplate.opsForValue().get("ms:ppi:" + tOrder.getOrderNo());
            WxMaTemplateMessage msg = buildOrderTemplateMessage(sessionUser,"diff", formID, tOrder,goodsNames,address);
            msg.getData().set(3,new WxMaTemplateData("keyword3",goodsNames.get(0)));
            msg.getData().add(new WxMaTemplateData("keyword7",tOrder.getPayMoney().setScale(2,WXContants.big_decimal_sale)+"元"));
            msg.getData().add(new WxMaTemplateData("keyword8",diff.getMoney().setScale(2,WXContants.big_decimal_sale)+"元"));
            msg.getData().add(new WxMaTemplateData("keyword9", diff.getMoney().setScale(2,WXContants.big_decimal_sale)+"元"));
            hzcxWxService.getWxMaService(sWxApp).getMsgService().sendTemplateMsg(msg);
        }else{
            throw new PauException(BizExceptionEnum.ORDER_DIFF_REFUND_EXCEPTION);
        }
        return isSuccess;
    }

}
