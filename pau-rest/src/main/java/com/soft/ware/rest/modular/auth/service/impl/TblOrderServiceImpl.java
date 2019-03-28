package com.soft.ware.rest.modular.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.dao.TblOrderMapper;
import com.soft.ware.rest.common.persistence.model.TblAddress;
import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.modular.auth.controller.dto.*;
import com.soft.ware.rest.modular.auth.service.TblAddressService;
import com.soft.ware.rest.modular.auth.service.TblGoodsService;
import com.soft.ware.rest.modular.auth.service.TblOrderService;
import com.soft.ware.rest.modular.auth.service.TblOwnerService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.util.RegexUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
@Transactional
public class TblOrderServiceImpl extends BaseService<TblOrderMapper,TblOrder> implements TblOrderService {


    @Resource
    private TblOrderMapper orderMapper;

    @Resource
    private TblGoodsService goodsService;

    @Resource
    private TblAddressService addressService;

    @Resource
    private TblOwnerService ownerService;

    @Resource
    private RedisTemplate redisTemplate;

    @Value(value = "${wx.pay.notify_host}")
    private String customerPayHost;

    @Value(value = "${wx.pay.notify_url_customer_pay_pickup}")
    private String customerPayPickup;

    @Value(value = "${wx.pay.notify_url_customer_pay}")
    private String customerPay;

    /**
     * 收银app下单
     * @param user
     * @param param
     * @return
     */
    @Override
    public TblOrder createOrder(SessionUser user, AddOrderParam param) {
        Date date = new Date();
        TblOrder o = new TblOrder();
        o.setOwner(user.getOwner());

        o.setNo(param.getNo());
        o.setMoneyChannel(param.getMoney_channel());
        o.setMoney(param.getMoney());
        o.setSource(param.getSource());
        o.setMoney(param.getMoney());
        o.setPayMoney(param.getMoney_shishou());//todo 不对应
        o.setPayAt(param.getPay_at());//todo 不对应
        o.setCreatedAt(date);
        o.setRemark("收银机订单");
        //o.setCreatedBy(user.getId());//todo 有疑问

        param.getMoney_dpay();//todo 优惠后金额
        param.getMoney_zhaol();//todo 找不到

        o.setStatus(param.getStatus());
        o.setSettlementBy(param.getSettlement_by());
        o.setGoods(param.getGoods());
        Integer insert = orderMapper.insert(o);
        return (insert != null && insert > 0 ) ? o : null;
    }

    /**
     * 买家小程序/收银app分页查询订单列表
     * @param user
     * @param page
     * @param param
     * @return
     */
    @Override
    public List<Map> findPage(SessionUser user, Page page,OrderParam param,Integer... source) {
        Long count = orderMapper.findListCount(user, param,source);
        page.setTotal(count);
        List<Map> list = orderMapper.findList(user, page, param,source);
        return list;
    }

    /**
     *
     * 收银app根据订单号查询订单详情
     * @param user
     * @param no
     * @return
     */
    @Override
    public TblOrder findByNo(SessionUser user,String no) {
        return orderMapper.findByNo(user,no);
    }


    @Override
    public boolean updateStatus(SessionUser user, String orderNO, String status) {
        int i = orderMapper.updateStatusByNo(user, orderNO, status);
        if (i != 1) {
            throw new PauException(BizExceptionEnum.UPDATE_ERROR);
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
	public List<TblOrder> findOrderListByStatus(Map<String, Object> map) {
		return orderMapper.findOrderListByStatus(map);
	}

    @Override
    public boolean update(WxPayOrderNotifyResult result,SessionUser user,String no) throws Exception {
        user.setOpenId(result.getOpenid());
        TblOrder order = this.findByNo(user,no);
        Integer beforeStatus = order.getStatus();
        order.setPayAt(DateUtil.parse(result.getTimeEnd(),"yyyyMMddHHmmss"));
        order.setStatus(TblOrder.STATUS_1);
        Map<String, Object> map = BeanMapUtils.toMap(result, true);
        order.setPayResponse(JSON.toJSONString(map));
        Integer update = orderMapper.update(order, new EntityWrapper<TblOrder>(new TblOrder().setId(order.getId()).setStatus(beforeStatus)));
        if (update != 1) {
            //这样做也没什么用，但是微信会尝试重新执行回调
            throw new PauException(BizExceptionEnum.ORDER_CREATE_FAIL);
        }
        return true;
    }


    @Override
    public TblOrder createMiniAppOrder(SessionUser user, CartParam param) {
        int round = BigDecimal.ROUND_HALF_UP;
        //Map<String,Object> map = new HashMap<>();
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
        if (!this.insert(o)) {
            throw new PauException(BizExceptionEnum.ORDER_CREATE_FAIL);
        }
        String tempKey = "ms:fio:" + orderNO;
        redisTemplate.opsForValue().set(tempKey,param.getFormID(), 604800, TimeUnit.SECONDS);
        logger.debug("买家下单时保存FormID {tempKey} = {req.body.formID}", tempKey, param.getFormID());
        return o;
    }

    @Override
    public boolean update(WxPayOrderQueryResult result, SessionUser user) {
        return false;
    }

}