package com.soft.ware.rest.modular.auth.controller;

import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.address.model.TAddress;
import com.soft.ware.rest.modular.address.service.ITAddressService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.service.ITOrderChildService;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import com.soft.ware.rest.modular.order_money_diff.model.TOrderMoneyDiff;
import com.soft.ware.rest.modular.order_money_diff.service.ITOrderMoneyDiffService;
import com.soft.ware.rest.modular.owner_temp.service.ITOwnerTempService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class WxPayController extends BaseController {

    @Autowired
    private HzcxWxService hzcxWxService;

    @Autowired
    private ITOrderService orderService;

    @Autowired
    private ISWxAppService appService;

    @Autowired
    private ITOrderMoneyDiffService orderMoneyDiffService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ITOwnerTempService ownerTempService;

    @Autowired
    private ITAddressService addressService;

    @Autowired
    private ITOrderChildService orderChildService;




    /**
     * 支付回调
     * @param xmlData
     * @return
     */
    @PostMapping(value = "${wx.pay.notify_url_customer_pay}")
    public String parseOrderNotifyResult(@RequestBody String xmlData) {
        try {
            WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
            SWxApp app = appService.findByAppId(result.getAppid());
            SessionUser user = new SessionUser().setAppId(app.getAppId()).setOwnerId(app.getOwnerId());
            WxPayService service = hzcxWxService.getWxPayService(app);
            result.checkResult(service, service.getConfig().getSignType(), false);
            //根据订单号 orderNo
            TOrder order = BeanMapUtils.toObject(orderService.findMap(Kv.obj("orderNo", result.getOutTradeNo()).set("creater", user.getOpenId())), TOrder.class);
            if (order.getPayTime() == null) {
                boolean b = orderService.updatePayCallback(result, user, order);
                if (b) {
                    paySuccessNotify(order, user, app);
                    logger.info("商家配送支付回调成功：" + xmlData);
                    return WxPayNotifyResponse.success("成功");
                }
            } else {
                //订单已经支付
                return WxPayNotifyResponse.success("成功1");
            }
            logger.info("商家配送支付回调可能失败：" + xmlData);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        //todo yancc 处理失败
        logger.error("商家配送支付回调失败:" + xmlData);
        return WxPayNotifyResponse.fail("失败");
    }

    /**
     * 到店自提支付回调
     * @param xmlData
     * @return
     * @throws WxPayException
     */
    @PostMapping(value = "${wx.pay.notify_url_customer_pay_pickup}")
    public String pickup(@RequestBody String xmlData) {
        try {
            WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
            SWxApp app = appService.findByAppId(result.getAppid());
            SessionUser user = new SessionUser().setAppId(app.getAppId()).setOwnerId(app.getOwnerId());
            WxPayService service = hzcxWxService.getWxPayService(app);
            result.checkResult(service, service.getConfig().getSignType(), false);
            //根据订单号 attach
            TOrder order = BeanMapUtils.toObject(orderService.findMap(Kv.obj("orderNo", result.getAttach()).set("creater", user.getOpenId())), TOrder.class);
            if (order.getPayTime() == null) {
                boolean b = orderService.updatePayCallback(result, user, order);
                if (b) {
                    paySuccessNotify(order, user, app);
                    logger.info("到店自取支付回调成功：" + xmlData);
                    return WxPayNotifyResponse.success("成功");
                }
            } else {
                //订单已经支付
                return WxPayNotifyResponse.success("成功1");
            }
            logger.info("到店自取支付回调可能失败：" + xmlData);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        //todo yancc 处理失败
        logger.info("到店自取支付回调失败：" + xmlData);
        return WxPayNotifyResponse.fail("失败");
    }

    /**
     * 补差价回调
     * @param xmlData
     * @return
     */
    @PostMapping(value = "${wx.pay.notify_url_customer_pay_diff}")
    public String diff(@RequestBody String xmlData) {
        try {
            WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
            SWxApp app = appService.findByAppId(result.getAppid());
            SessionUser user = new SessionUser().setAppId(app.getAppId()).setOwnerId(app.getOwnerId());
            WxPayService service = hzcxWxService.getWxPayService(app);
            result.checkResult(service, service.getConfig().getSignType(), false);
            TOrderMoneyDiff order = BeanMapUtils.toObject(orderMoneyDiffService.findMap(Kv.obj("creater", user.getOpenId()).set("no", result.getOutTradeNo())), TOrderMoneyDiff.class);
            order.setStatus(TOrderMoneyDiff.status_1);
            order.setPayTime(DateUtil.parse(result.getTimeEnd(),"yyyyMMddHHmmss"));
            order.setResponse(JSON.toJSONString(result));
            boolean update = orderMoneyDiffService.update(order, user);
            if (update) {
                logger.info("补差价支付回调成功：" + xmlData);
                return WxPayNotifyResponse.success("成功");
            } else {
                logger.info("补差价支付回调可能失败：" + xmlData);
                return WxPayNotifyResponse.success("成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //todo yancc 处理失败
            logger.info(e.getMessage());
        }
        logger.info("补差价支付回调失败：" + xmlData);
        return WxPayNotifyResponse.fail("失败");
    }


    /**
     * 补差价退款支付回调
     * @param xmlData
     * @return
     * @throws WxPayException
     */
    @Deprecated //微信退款直接能获取结果，不使用这个回调
    @PostMapping(value = "${wx.pay.notify_url_customer_pay_diff_refund}")
    public String parseRefundNotifyResult(@RequestBody String xmlData) throws Exception {
        WxPayRefundNotifyResult result = WxPayRefundNotifyResult.fromXML(xmlData, WxPayRefundNotifyResult.class);
        SWxApp app = appService.findByAppId(result.getAppid());
        result = hzcxWxService.getWxPayService(app).parseRefundNotifyResult(xmlData);
        WxPayRefundNotifyResult.ReqInfo info = result.getReqInfo();
        Map<String, Object> map = orderMoneyDiffService.findMap(Kv.obj("no", info.getOutRefundNo()));
        TOrderMoneyDiff diff = BeanMapUtils.toObject(map, TOrderMoneyDiff.class);
        diff.setStatus(TOrderMoneyDiff.status_1);
        diff.setResponse(JSON.toJSONString(result));
        orderMoneyDiffService.updateById(diff);

        try{
            TOrder order = BeanMapUtils.toObject(orderService.findMap(Kv.by("orderNo", diff.getOrderNo())), TOrder.class);
            List<Map> childOrders = orderChildService.findMaps(Kv.by("orderId", order.getId()));
            List<String> goodsNames = childOrders.stream().map(m -> m.get("goodsName") + "").collect(Collectors.toList());
            String formID = redisTemplate.opsForValue().get("ms:ppi:" + info.getOutTradeNo());
            SessionUser user = new SessionUser().setOwnerId(app.getOwnerId());
            WxMaTemplateMessage msg = orderService.buildOrderTemplateMessage(user, "refund", formID, order, goodsNames, null);
            msg.getData().add(new WxMaTemplateData("keyword5", diff.getMoney().setScale(2, WXContants.big_decimal_sale).toString() + "元"));
            msg.getData().add(new WxMaTemplateData("keyword6", diff.getReason()));
            msg.getData().add(new WxMaTemplateData("keyword7", "如有疑问，请进入小程序联系商家"));
            hzcxWxService.getWxMaService(app).getMsgService().sendTemplateMsg(msg);
        } catch (WxErrorException e){
            logger.error("订单差价退款回调发送通知失败：{}   {}", e.getError().getErrorCode(), e.getError().getErrorMsg());
        } catch (Exception e) {
            logger.error("订单差价退款回调发送通知失败：{}  ", e.getMessage());
            e.printStackTrace();
        }
        return WxPayNotifyResponse.success("成功");
    }

    //@ApiOperation(value = "扫码支付回调通知处理")
    @PostMapping("/notify/scanpay")
    public String parseScanPayNotifyResult(String xmlData) throws WxPayException {
       // final WxScanPayNotifyResult result = hzcxWxService.getWxPayService(null).parseScanPayNotifyResult(xmlData);
        return WxPayNotifyResponse.success("成功");
    }


    /**
     * 订单支付成功后发送通知
     * @param order
     * @param user
     * @param app
     * @throws Exception
     */
    private void paySuccessNotify(TOrder order,SessionUser user,SWxApp app) {
        try {
            TAddress address = BeanMapUtils.toObject(addressService.findMap(Kv.by("id", order.getAddressId())), TAddress.class);
            List<Map> childOrders = orderChildService.findMaps(Kv.by("orderId", order.getId()));
            List<String> names = childOrders.stream().map(s -> s.get("goodsName") + "").collect(Collectors.toList());
            String tempKey = "ms:ppi:" + order.getOrderNo();
            String pack = redisTemplate.opsForValue().get(tempKey);
            //todo yancc 删掉下面的substring
            if (pack.startsWith("prepay_id=")) {
                pack = pack.substring(10);
            }
            if (TOrder.MONEY_CHANNEL_3.equals(order.getMoneyChannel()) || TOrder.MONEY_CHANNEL_0.equals(order.getMoneyChannel())) {
                logger.debug("买家支付订单时保存PrepayID {} = {}", tempKey, pack);
                // 备注信息
                WxMaTemplateMessage msg = orderService.buildOrderTemplateMessage(user,"pay", pack, order, names, address);
                msg.getData().add(new WxMaTemplateData("keyword6", "如有疑问，请进入小程序联系商家"));
                hzcxWxService.getWxMaService(app).getMsgService().sendTemplateMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("{}发送支付通知失败:{}", order.getOrderNo(), e.getMessage());
        }

    }


}
