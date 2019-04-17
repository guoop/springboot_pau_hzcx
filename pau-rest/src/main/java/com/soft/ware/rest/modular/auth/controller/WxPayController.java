package com.soft.ware.rest.modular.auth.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.service.ImService;
import com.soft.ware.rest.modular.auth.service.SmsService;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import com.soft.ware.rest.modular.order_money_diff.service.ITOrderMoneyDiffService;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_temp.service.ITOwnerTempService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WxPayController extends BaseController {

    @Autowired
    private HzcxWxService hzcxWxService;

    @Autowired
    private ITOrderService orderService;

    @Autowired
    private ITOwnerService ownerService;

    @Autowired
    private ISWxAppService appService;

    @Autowired
    private ITOrderMoneyDiffService orderMoneyDiffService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private SmsService smsService;

    @Autowired
    private ImService imService;

    @Autowired
    private ITOwnerTempService ownerTempService;




    /**
     * 支付回调
     * @param xmlData
     * @return
     */
    @PostMapping(value = "${wx.pay.notify_url_customer_pay}")
    public String parseOrderNotifyResult(@RequestBody String xmlData) {
        try {
            final WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
            SWxApp app = appService.findByAppId(result.getAppid());
            if (app != null) {
                SessionUser user = new SessionUser(app.getId());
                WxPayService service = hzcxWxService.getWxPayService(app);
                result.checkResult(service, service.getConfig().getSignType(), false);
                orderService.updatePayCallback(result, user, result.getOutTradeNo());
                logger.info("商家配送支付回调成功：" + xmlData);
                return WxPayNotifyResponse.success("成功");
            }
        } catch (WxPayException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
            final WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
            SWxApp app = appService.findByAppId(result.getAppid());
            if (app != null) {
                SessionUser user = new SessionUser(app.getOwnerId());
                WxPayService service = hzcxWxService.getWxPayService(app);
                result.checkResult(service, service.getConfig().getSignType(), false);
                orderService.updatePayCallback(result, user, result.getAttach());
                logger.info("到店自取支付回调成功：" + xmlData);
                return WxPayNotifyResponse.success("成功");
            }
        } catch (WxPayException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
            final WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
            SWxApp app = appService.findByAppId(result.getAppid());
            if (app != null) {
                SessionUser user = new SessionUser(app.getOwnerId());
                WxPayService service = hzcxWxService.getWxPayService(app);
                result.checkResult(service, service.getConfig().getSignType(), false);
                orderMoneyDiffService.update(result, user);
                logger.info("到店自取支付回调成功：" + xmlData);
                return WxPayNotifyResponse.success("成功");
            }
        } catch (WxPayException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //todo yancc 处理失败
        logger.info("到店自取支付回调失败：" + xmlData);
        return WxPayNotifyResponse.fail("失败");
    }


    //@ApiOperation(value = "退款回调通知处理")
    @PostMapping("/notify/refund")
    public String parseRefundNotifyResult(@RequestBody String xmlData) throws WxPayException {
        //final WxPayRefundNotifyResult result = hzcxWxService.getWxPayService(null).parseRefundNotifyResult(xmlData);
        return WxPayNotifyResponse.success("成功");
    }

    //@ApiOperation(value = "扫码支付回调通知处理")
    @PostMapping("/notify/scanpay")
    public String parseScanPayNotifyResult(String xmlData) throws WxPayException {
       // final WxScanPayNotifyResult result = hzcxWxService.getWxPayService(null).parseScanPayNotifyResult(xmlData);
        return WxPayNotifyResponse.success("成功");
    }

}
