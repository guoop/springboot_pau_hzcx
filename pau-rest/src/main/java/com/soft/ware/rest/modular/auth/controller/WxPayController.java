package com.soft.ware.rest.modular.auth.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.service.TblOrderService;
import com.soft.ware.rest.modular.auth.service.TblOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WxPayController extends BaseController {

    @Autowired
    private HzcxWxService hzcxWxService;

    @Autowired
    private TblOrderService orderService;

    @Autowired
    private TblOwnerService ownerService;


    /**
     * 支付回调
     * @param xmlData
     * @return
     */
    @PostMapping("/customer-pay")
    public String parseOrderNotifyResult(@RequestBody String xmlData) {
        try {
            final WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
            TblOwner owner = ownerService.findByAppId(result.getAppid());
            if (owner != null) {
                SessionUser user = new SessionUser(SessionUser.type_customer, owner.getOwner());
                WxPayService service = hzcxWxService.getWxPayService(owner);
                result.checkResult(service, service.getConfig().getSignType(), false);
                orderService.update(result, user, result.getOutTradeNo());
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
    @PostMapping("/customer-pay/pickup")
    public String pickup(@RequestBody String xmlData) {
        try {
            final WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
            TblOwner owner = ownerService.findByAppId(result.getAppid());
            if (owner != null) {
                SessionUser user = new SessionUser(SessionUser.type_customer, owner.getOwner());
                WxPayService service = hzcxWxService.getWxPayService(owner);
                result.checkResult(service, service.getConfig().getSignType(), false);
                orderService.update(result, user, result.getAttach());
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
