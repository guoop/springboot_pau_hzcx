package com.soft.ware.rest.modular.auth.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxScanPayNotifyResult;
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
    //@ApiOperation(value = "支付回调通知处理")
    @PostMapping("/customer-pay")
    public String parseOrderNotifyResult(@RequestBody String xmlData) {
        try {
            final WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
            TblOwner owner = ownerService.findByAppId(result.getAppid());
            if (owner != null) {
                SessionUser user = new SessionUser(SessionUser.type_customer, owner.getOwner());
                WxPayService service = hzcxWxService.getWxPayService(owner);
                result.checkResult(service, service.getConfig().getSignType(), false);
                boolean update = orderService.update(result,user);
                logger.info("到店自取支付回调成功：" + xmlData);
                return WxPayNotifyResponse.success("成功");
            } else {
                //todo yancc 需要处理失败
                return WxPayNotifyResponse.fail("失败");
            }
        } catch (WxPayException e){
            e.printStackTrace();
            logger.info("到店自取支付回调失败：" + xmlData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //todo yancc
        return WxPayNotifyResponse.fail("失败");
    }

    /**
     * 到店自提支付回调
     * @param xmlData
     * @return
     * @throws WxPayException
     */
    //@ApiOperation(value = "支付回调通知处理")
    @PostMapping("/customer-pay/pickup")
    public String pickup(@RequestBody String xmlData) {
        try {
            final WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
            TblOwner owner = ownerService.findByAppId(result.getAppid());
            if (owner != null) {
                SessionUser user = new SessionUser(SessionUser.type_customer, owner.getOwner());
                WxPayService service = hzcxWxService.getWxPayService(owner);
                result.checkResult(service, service.getConfig().getSignType(), false);
                boolean update = orderService.update(result, user);
                logger.info("到店自取支付回调成功：" + xmlData);
                return WxPayNotifyResponse.success("成功");
            } else {
                //todo yancc 需要处理失败
                return WxPayNotifyResponse.fail("失败");
            }
        } catch (WxPayException e){
            e.printStackTrace();
            logger.info("到店自取支付回调失败：" + xmlData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //todo yancc
        return WxPayNotifyResponse.fail("失败");
    }


    //@ApiOperation(value = "退款回调通知处理")
    @PostMapping("/notify/refund")
    public String parseRefundNotifyResult(@RequestBody String xmlData) throws WxPayException {
        final WxPayRefundNotifyResult result = hzcxWxService.getWxPayService(null).parseRefundNotifyResult(xmlData);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }

    //@ApiOperation(value = "扫码支付回调通知处理")
    @PostMapping("/notify/scanpay")
    public String parseScanPayNotifyResult(String xmlData) throws WxPayException {
        final WxScanPayNotifyResult result = hzcxWxService.getWxPayService(null).parseScanPayNotifyResult(xmlData);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }

}
