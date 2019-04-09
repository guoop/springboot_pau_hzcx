package com.soft.ware.rest.modular.auth.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.common.collect.Lists;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.dao.TblOrderMapper;
import com.soft.ware.rest.common.persistence.model.*;
import com.soft.ware.rest.modular.auth.controller.dto.*;
import com.soft.ware.rest.modular.auth.service.*;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.util.RegexUtils;
import com.soft.ware.rest.modular.auth.util.WXContants;
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
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private HzcxWxService hzcxWxService;


    @Autowired
    private WXContants contants;

    @Autowired
    private TblOrderMoneyDiffService diffService;

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
        o.setOwner(user.getOwnerId());

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
    public List<Map> findPage(SessionUser user, Page page,OrderPageParam param,Integer... source) {
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
        return orderMapper.selectOne(new TblOrder().setNo(no).setCreatedBy(user.getOpenId()).setOwner(user.getOwnerId()));
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
        TblOwner owner = ownerService.find(user.getOwnerId());
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
        o.setOwner(user.getOwnerId());
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

    @Override
    public List<Map> findOwnerOrderPage(SessionUser user, Page page, OrderPageParam param) {
        long count = orderMapper.findOwnerOrderListCount(user, param);
        page.setTotal(count);
        return orderMapper.findOwnerOrderList(user, page, param);
    }

    @Override
    public Map findOwnerOrder(SessionUser user, String no) {
        return orderMapper.findOwnerOrder(user,no);
    }


    private WxMaTemplateMessage buildOrderTemplateMessage(String templateID, String fromID, TblOrder order){
        StringBuilder goodsName = new StringBuilder();
        String[] gs = order.getGoods().split(",");
        int i = 0;
        do {
            String[] s = gs[i].split("__");
            goodsName.append(",");
            goodsName.append(s[2]);
            if (i == 2) {
                //最多拼接三个
                break;
            }
            i++;
        } while (i < gs.length && i < 3);
        if (gs.length > 3) {
            goodsName.append("...共").append(gs.length).append("种商品");
        }
        ArrayList<WxMaTemplateData> data = Lists.newArrayList();
        data.add(new WxMaTemplateData("keyword1", order.getNo()));// 订单编号
        data.add(new WxMaTemplateData("keyword2", DateUtil.format(order.getCreatedAt(), WXContants.date_format)));// 下单时间
        data.add(new WxMaTemplateData("keyword3", order.getPayMoney().setScale(2, WXContants.big_decimal_sale) + ""));// 订单金额
        data.add(new WxMaTemplateData("keyword4", goodsName.toString()));// 商品名称
        data.add(new WxMaTemplateData("keyword5", order.getConsigneeName() + ' ' + order.getConsigneeMobile() + ' ' + order.getConsigneeAddress()));// 收货地址
        WxMaTemplateMessage msg = WxMaTemplateMessage.builder()
                .templateId(templateID)
                .formId(fromID)
                .toUser(order.getCreatedBy())
                .page("pages/mine/index")
                .data(data)
                .build();
        return msg;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean updateOwnerOrder(SessionUser user, String status, TblOrder order, TblOwner owner,String reason) throws WxErrorException {
        //todo yancc 更新成功后是否需要清除 redis 中的 formId
        String no = order.getNo();
        long current = System.currentTimeMillis();
        String dateFormat = "YYYY-MM-DD HH:mm:ss";
        Date date = new Date(current);
        WxMaService service = hzcxWxService.getWxMaService(owner);
        List<String> ids = Lists.newArrayList();
        List<String> nums = Lists.newArrayList();
        String[] gs = order.getGoods().split(",");
        for (String g : gs) {
            String[] s = g.split("__");
            ids.add(s[0]);
            nums.add(s[4]);
        }
        //todo yancc 需要添加 行锁
        boolean update = false;
        if ("cancel".equals(status)) {
            // 只允许取消货到付款、待商家确认的订单
            if (order.getMoneyChannel().equals(TblOrder.MONEY_CHANNEL_1)  && order.getStatus().equals(TblOrder.STATUS_1)) {
                order.setStatus(TblOrder.STATUS_01);
                order.setCancelBy(user.getPhone());
                order.setCancelAt(date);
                order.setCancelReason(reason);
                update = this.update(order, new EntityWrapper<>(new TblOrder().setId(order.getId()).setOwner(user.getOwnerId())));
                logger.info("取消订单 - {}", order.getNo());
                String templateFormId = redisTemplate.opsForValue().get("ms:fio:" + no);
                WxMaTemplateMessage msg = this.buildOrderTemplateMessage("cancel", templateFormId, order);
                msg.getData().add(new WxMaTemplateData("keyword6", order.getCancelReason()));// 取消原因
                msg.getData().add(new WxMaTemplateData("keyword7", "如有疑问，请进入小程序联系商家"));// 备注信息
                service.getMsgService().sendTemplateMsg(msg);
            } else {
                throw new PauException(BizExceptionEnum.ORDER_CANEL_FAIL);
            }
        } else if ("confirm".equals(status)) {
            // 只允许确认待商家确认的订单
            if (order.getStatus().equals(TblOrder.STATUS_1)) {
                order.setStatus(TblOrder.STATUS_10);
                order.setConfirmBy(user.getPhone());
                order.setConfirmAt(date);
                update = this.update(order, new EntityWrapper<>(new TblOrder().setId(order.getId()).setOwner(user.getOwnerId())));
                if (update) {
                    update = goodsService.updateStock(user, ids, nums);
                }
                logger.info("确认订单 - {}", no);
                String templateFormId = redisTemplate.opsForValue().get("ms:fio:" + no);
                WxMaTemplateMessage msg = this.buildOrderTemplateMessage("confirm", templateFormId, order);
                msg.getData().add(new WxMaTemplateData("keyword6", DateUtil.format(order.getConfirmAt(), dateFormat)));// 确认时间
                msg.getData().add(new WxMaTemplateData("keyword7", "如有疑问，请进入小程序联系商家"));// 备注信息
            } else {
                throw new PauException(BizExceptionEnum.ORDER_CONFIRM_FAIL);
            }
        } else if ("deliver".equals(status)) {
            // 只允许对已经经过商家确认的订单进行配送
            if (order.getStatus().equals(TblOrder.STATUS_10)) {
                order.setStatus(TblOrder.STATUS_2);
                order.setDistributionBy(user.getPhone());
                order.setDistributionAt(date);
                update = this.update(order, new EntityWrapper<>(new TblOrder().setId(order.getId()).setOwner(user.getOwnerId())));
                if (order.getSource().equals(TblOrder.SOURCE_0)) {
                    logger.info("配送订单 - ", no);
                    String templateFormId = redisTemplate.opsForValue().get("ms:fit:" + no);
                    WxMaTemplateMessage msg = this.buildOrderTemplateMessage("deliver", templateFormId, order);
                    msg.getData().add(new WxMaTemplateData("keyword6", "配送人员已经开始为您配送，请保持手机畅通"));// 温馨提示
                    msg.getData().add(new WxMaTemplateData("keyword7", "如有疑问，请进入小程序联系商家"));// 备注信息
                    service.getMsgService().sendTemplateMsg(msg);
                }
            } else {
                throw new PauException(BizExceptionEnum.ORDER_DELIVER_FAIL);
            }
        } else if ("done".equals(status)) {
            // 只允许对配送中的订单进行标记完成操作
            if (order.getStatus().equals(TblOrder.SOURCE_2)) {
                order.setStatus(TblOrder.STATUS_3);
                order.setDoneBy(user.getPhone());
                order.setDoneAt(date);
                update = this.update(order, new EntityWrapper<>(new TblOrder().setId(order.getId()).setOwner(user.getOwnerId())));
            } else {
                throw new PauException(BizExceptionEnum.ORDER_DONE_FAIL);
            }
        } else {
            // 不允许其他操作
            throw new PauException(BizExceptionEnum.NO_OPTION);
        }

        if (update) {
            return true;
        }
        throw new PauException(BizExceptionEnum.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean refund(SessionUser user, OrderRefundParam param) throws WxPayException {
        //todo yancc 更新成功后是否需要清除 redis 中的 formId
        int round = WXContants.big_decimal_sale;
        Date date = new Date();
        TblOrder order = this.findByNo(user, param.getOrder());
        if (!order.getMoneyChannel().equals(TblOrder.MONEY_CHANNEL_0) || (!order.getStatus().equals(TblOrder.STATUS_1) && !order.getStatus().equals(TblOrder.STATUS_3))) {
            throw new PauException(BizExceptionEnum.ORDER_REFUND_NOT_SUPPORT);
        } else if (TblOrder.REFUND_STATUS_0.equals(order.getRefundStatus())) {
            throw new PauException(BizExceptionEnum.ORDER_REFUND_RUNNING);
        } else if (TblOrder.REFUND_STATUS_1.equals(order.getRefundStatus())) {
            throw new PauException(BizExceptionEnum.ORDER_REFUND_FINISHED);
        }

        // 退款金额
        BigDecimal refundMoney = BigDecimal.ZERO;
        // 全额退款
        if ("all".equals(param.getRefundType())) {
            refundMoney = (order.getPayMoney().multiply(BigDecimal.valueOf(100))).setScale(2, round);
        }
        // 部分退款
        if ("part".equals(param.getRefundType())) {
            refundMoney = (param.getRefundMoney().multiply(BigDecimal.valueOf(100))).setScale(2, round);
        }

        // 如果是到店自提的订单
        String orderNO = order.getNo();
        if (order.getSource().equals(TblOrder.SOURCE_2)) {
            orderNO = order.getConsigneeMobile() + "" + order.getCreatedAt().getTime();
        }
        TblOwner owner = ownerService.find(user);
        WxPayService service = hzcxWxService.getWxPayService(owner);
        //先执行操作，在发送通知，发送失败可以回滚
        //todo yancc 需要添加 行锁
        order.setStatus(param.getRefundType().equals("all") ? -1 : 3);
        order.setRefundBy(user.getPhone());
        order.setRefundAt(date);
        order.setRefundReason(param.getRefundReason());
        order.setRefundMoney(refundMoney);
        boolean update = this.update(order, new EntityWrapper<>(new TblOrder().setId(order.getId()).setOwner(user.getOwnerId())));
        if (update) {
            WxPayRefundRequest req = WxPayRefundRequest
                    .newBuilder()
                    .outTradeNo(orderNO)
                    .outRefundNo(IdGenerator.getId())
                    .totalFee(order.getPayMoney().multiply(BigDecimal.valueOf(100)).intValue())
                    .refundFee(refundMoney.intValue())
                    .refundDesc(ToolUtil.isEmpty(param.getRefundReason()) ? "" : param.getRefundReason())
                    //.notifyUrl(contants.getCustomerPayHost() + "/") //todo yancc 回调地址
                    .build();
            WxPayRefundResult result = service.refund(req);
            logger.info("微信退款请求执行成功:订单号{}，错误码：{}", order.getNo(), result.getErrCode());
            try{
                // 全额退款则意味着取消订单
                if ("all".equals(param.getRefundType())) {
                    order.setCancelBy(user.getPhone());
                    order.setCancelAt(date);
                    order.setCancelReason(order.getRefundReason());
                }

                String formID = redisTemplate.opsForValue().get("ms:ppi:" + order.getNo());
                String templateID = (String)redisTemplate.opsForHash().get("ms:tpl:" + owner.getAppId(), "refund");

                WxMaTemplateMessage msg = buildOrderTemplateMessage(templateID, formID, order);
                msg.getData().set(4, new WxMaTemplateData("keyword5", "all".equals(param.getRefundType()) ? order.getPayMoney().setScale(2, round) + "元" : refundMoney.setScale(2, round) + "元"));
                msg.getData().add(new WxMaTemplateData("keyword6", order.getRefundReason()));
                msg.getData().add(new WxMaTemplateData("keyword7", "到账金额以微信到账金额为准，请知晓"));
                msg.getData().add(new WxMaTemplateData("keyword8", "如有疑问，请进入小程序联系商家"));

                hzcxWxService.getWxMaService(owner).getMsgService().sendTemplateMsg(msg);
            }catch (Exception e){
                e.printStackTrace();
                //todo yancc 失败是否补发通知
                logger.error("{}退款成功：退款通知发送失败!", order.getNo());
            }

            return true;

        }



        return false;
    }

    @Override
    public boolean refundDiff(SessionUser user, OrderRefundParam param) {
        Date date = new Date();
        String no = param.getOrder();
        TblOrder order = this.findByNo(user, no);
        TblOrderMoneyDiff diff = diffService.findByNo(user, order);
        TblOwner owner = ownerService.find(user);
        if (TblOrderMoneyDiff.status_1.equals(diff.getStatus())) {
            throw new PauException(BizExceptionEnum.ORDER_REFUND_FINISHED);
        } else if (TblOrderMoneyDiff.refund_status_0.equals(diff.getRefundStatus())) {
            throw new PauException(BizExceptionEnum.ORDER_DIFF_REFUND_RUNNING);
        } else if (TblOrderMoneyDiff.refund_status_1.equals(diff.getRefundStatus())) {
            throw new PauException(BizExceptionEnum.ORDER_DIFF_REFUND_FINISHED);
        }
        // 如果是到店自提的订单
        if (order.getSource().equals(TblOrder.SOURCE_2)) {
            no = order.getConsigneeMobile() + "" + order.getCreatedAt().getTime();
        }

        diff.setStatus(TblOrderMoneyDiff.status_1);
        diff.setRefundBy(user.getPhone());
        diff.setRefundAt(date);
        diff.setRefundStatus(TblOrderMoneyDiff.refund_status_1);
        //todo yancc 需要乐观锁
        boolean update = diffService.update(diff, new EntityWrapper<>(new TblOrderMoneyDiff().setId(diff.getId()).setOwner(user.getOwnerId())));

        try {
            if (update) {
                WxPayService service = hzcxWxService.getWxPayService(owner);
                BigDecimal refundFee = BigDecimal.valueOf(Math.abs(diff.getMoneyDiff().doubleValue()));
                WxPayRefundRequest req = WxPayRefundRequest
                        .newBuilder()
                        .outTradeNo(no)
                        .outRefundNo(IdGenerator.getId())
                        .totalFee(order.getPayMoney().multiply(BigDecimal.valueOf(100)).intValue())
                        .refundFee(refundFee.multiply(BigDecimal.valueOf(100)).intValue())
                        .build();
                service.refund(req);
                String formID = redisTemplate.opsForValue().get("ms:ppi:" + order.getNo());
                String templateID = (String)redisTemplate.opsForHash().get("ms:tpl:" + owner.getAppId(), "refund");
                WxMaTemplateMessage msg = buildOrderTemplateMessage(templateID, formID, order);
                msg.getData().set(4, new WxMaTemplateData("keyword5", refundFee.setScale(2, WXContants.big_decimal_sale).toString() + "元"));
                msg.getData().add(new WxMaTemplateData("keyword6",ToolUtil.isEmpty(param.getRefundReason()) ? "小票差额退款" : param.getRefundReason()));
                msg.getData().add(new WxMaTemplateData("keyword8", "如有疑问，请进入小程序联系商家"));
                try {
                    hzcxWxService.getWxMaService(owner).getMsgService().sendTemplateMsg(msg);
                } catch (WxErrorException e) {
                    logger.error("订单差价退款成功，但是通知发送失败,订单号:{},退款单号:{},错误码：{},错误信息：{}", order.getNo(), req.getOutRefundNo(), e.getError().getErrorCode(), e.getError().getErrorMsg());
                    e.printStackTrace();
                }

                return true;
            }
        } catch (WxPayException e) {
            //todo yancc 需要乐观锁
            logger.info("订单差价退款失败,订单号{}，错误码{},错误原因{}", no, e.getErrCode(), e.getErrCodeDes());
            //退款失败
            diff.setRefundBy(user.getPhone());
            diff.setRefundAt(date);
            diff.setRefundStatus(TblOrderMoneyDiff.refund_status_2);
            diffService.update(diff, new EntityWrapper<>(new TblOrderMoneyDiff().setId(diff.getId()).setOwner(user.getOwnerId())));
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Map> findOrderMapByNo(SessionUser user, String no) {
        return orderMapper.findOrderMapByNo(user, no);
    }


}