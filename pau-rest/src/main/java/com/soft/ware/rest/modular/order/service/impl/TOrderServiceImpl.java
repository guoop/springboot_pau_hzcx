package com.soft.ware.rest.modular.order.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.google.common.collect.Lists;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.modular.address.model.TAddress;
import com.soft.ware.rest.modular.address.service.ITAddressService;
import com.soft.ware.rest.modular.auth.controller.dto.OrderDeleteParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.util.RegexUtils;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.goods.model.TUnit;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import com.soft.ware.rest.modular.goods.service.ITUnitService;
import com.soft.ware.rest.modular.order.controller.dto.CreateOrderParam;
import com.soft.ware.rest.modular.order.dao.TOrderMapper;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.model.TOrderChild;
import com.soft.ware.rest.modular.order.service.ITOrderChildService;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_config.model.TOwnerConfig;
import com.soft.ware.rest.modular.owner_config.service.ITOwnerConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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



    @Override
    public List<TOrder> selectOrderListByMap(Map<String, Object> param) {
        return orderMapper.selectOrderListByMap(param);
    }

    @Override
    public List<Map> findPage(SessionUser user, Page page, OrderPageParam param, int... source) {
        Long count = orderMapper.findListCount(user, param,source);
        page.setTotal(count);
        return orderMapper.findList(user, page, param,source);
    }

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return orderMapper.findMaps(map);
    }

    @Override
    public Map<String, Object> findMap(Map<String, Object> map) {
        List<Map<String, Object>> maps = findMaps(map);
        return maps.isEmpty() ? null : maps.get(0);
    }

    @Override
    public List<HashMap<String, Object>> selectOrdersListByMap(Map<String, Object> map) {

        List<HashMap<String,Object>> listMap = orderMapper.selectOrdersListByMap(map);
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
    public WxMaTemplateMessage buildOrderTemplateMessage(String templateID, String fromID, TOrder order, List<String> gs, TAddress address){
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
        ArrayList<WxMaTemplateData> data = Lists.newArrayList();
        data.add(new WxMaTemplateData("keyword1", order.getOrderNo()));// 订单编号
        data.add(new WxMaTemplateData("keyword2", DateUtil.format(order.getCreateTime(), WXContants.date_format)));// 下单时间
        data.add(new WxMaTemplateData("keyword3", order.getPayMoney().setScale(2, WXContants.big_decimal_sale) + ""));// 订单金额
        data.add(new WxMaTemplateData("keyword4", goodsName.toString()));// 商品名称
        data.add(new WxMaTemplateData("keyword5", address.getName() + ' ' + address.getPhone() + ' ' + address.getProvince() + " " + address.getDetail()));// 收货地址
        WxMaTemplateMessage msg = WxMaTemplateMessage.builder()
                .templateId(templateID)
                .formId(fromID)
                .toUser(order.getCreater())
                .page("pages/mine/index")
                .data(data)
                .build();
        return msg;
    }

    @Override
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
            if (TblGoods.status_1.equals(g.getInt("status"))) {
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
                c.setTotalPrice(total);
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
        final String orderNO =  IdGenerator.getId();
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
        o.setAddressId(param.getAddressId());
        o.setStatus(TOrder.STATUS_0);
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
    public boolean updatePayCallback(WxPayOrderNotifyResult result, SessionUser user, String no) throws Exception {
        user.setOpenId(result.getOpenid());
        TOrder order = BeanMapUtils.toObject(findMap(Kv.obj("orderNo", no).set("creater", user.getOpenId())), TOrder.class);
        Integer beforeStatus = order.getStatus();
        order.setPayTime(DateUtil.parse(result.getTimeEnd(),"yyyyMMddHHmmss"));
        order.setStatus(TOrder.STATUS_1);
        Map<String, Object> map = BeanMapUtils.toMap(result, true);
        order.setPayResponse(JSON.toJSONString(map));
        Integer version = order.getVersion();
        //todo yancc 订单更新失败怎么办
        order.setVersion(order.getVersion() + 1);
        Integer update = orderMapper.update(order, new EntityWrapper<TOrder>(new TOrder().setId(order.getId()).setStatus(beforeStatus).setVersion(version)));
        if (update != 1) {
            //这样做也没什么用，但是微信会尝试重新执行回调
            throw new PauException(BizExceptionEnum.ORDER_CREATE_FAIL);
        }
        return true;
    }

    @Override
    public boolean customerDelete(SessionUser user, OrderDeleteParam param) {
        int i = orderMapper.customerDelete(user, param);
        if (i == 1) {
            return true;
        } else {
            throw new PauException(BizExceptionEnum.UPDATE_ERROR);
        }
    }

    @Override
    public boolean customerCancel(SessionUser user, OrderDeleteParam param) {
        int i = orderMapper.customerCancel(user, param);
        if (i != 1) {
            throw new PauException(BizExceptionEnum.UPDATE_ERROR);
        }
        return true;
    }

    @Override
    public int selectOrderCount(String ownerId) {
    return orderMapper.selectOrderCount(ownerId);
    }

    @Override
    public TOrder selectOrderDetailById(String orderNo) {
        TOrder tOrder = orderMapper.selectOrderDetailById(orderNo);
        if(ToolUtil.isNotEmpty(tOrder)){
            Map<String,Object> param = new HashMap<>();
            param.put("orderId",tOrder.getId());
            List<TOrderChild>  childList = orderChildService.selectOrderChildListByMap(param);
            if(childList.size() == 0 || childList.size() > 0){
                tOrder.setOrderChildList(childList);
            }
        }
        return tOrder;
    }


}
