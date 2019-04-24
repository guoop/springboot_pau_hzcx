package com.soft.ware.rest.modular.auth.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.EntPayServiceImpl;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import com.soft.ware.rest.modular.wx_secret.model.SWxSecret;
import com.soft.ware.rest.modular.wx_secret.service.ISWxSecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
//@Transactional
public class HzcxWxPayServiceImpl implements HzcxWxService {


    @Autowired
    private ITOwnerService ownerService;

    private HashMap<String,WxPayService> map = new HashMap<>();
    private HashMap<String,WxMaService> map2 = new HashMap<>();
    @Value(value = "${wx.pay.notify_host}")
    private String customerPayHost;

    @Value(value = "${wx.pay.notify_url_customer_pay_pickup}")
    private String customerPayPickup;

    @Value(value = "${wx.pay.notify_url_customer_pay}")
    private String customerPay;

    @Autowired
    private ISWxAppService appService;

    @Autowired
    private ISWxSecretService secretService;

    private WxMaService wxMaService;



    @Override
    public WxPayService getWxPayService(TOwner owner) {
        SWxApp app = appService.find(owner);
        return getWxPayService(app);
    }

    @Override
    public WxMaService getWxMaService(TOwner owner){
        SWxApp app = appService.find(owner);
        return getWxMaService(app);
    }

    @Override
    public WxMaService getWxMaService(SWxApp app) {
        if (map2.containsKey(app.getAppId())) {
            return map2.get(app.getAppId());
        } else {
            WxMaService service = new WxMaServiceImpl();
            WxMaInMemoryConfig config = new WxMaInMemoryConfig();
            config.setAppid(app.getAppId());
            config.setSecret(app.getAppSecret());
            service.setWxMaConfig(config);
            map2.put(app.getAppId(), service);
            return service;
        }
    }

    @Override
    public WxPayService getWxPayService(SWxApp app) {
        SWxSecret secret = secretService.find(app);
        if (map.containsKey(app.getAppId())) {
            return map.get(app.getAppId());
        } else {
            WxPayServiceImpl service = new WxPayServiceImpl();
            WxPayConfig config = new WxPayConfig();
            config.setAppId(app.getAppId());
            config.setMchId(secret.getShopNo());//商户号
            config.setNotifyUrl(customerPayHost + customerPay);
            config.setMchKey(secret.getPayKey());
            config.setTradeType(WxPayConstants.TradeType.JSAPI);
            config.setSignType(WxPayConstants.SignType.MD5);
            config.setKeyPath("classpath:p12/" + app.getOwnerId() + ".p12");
            service.setConfig(config);
            service.setEntPayService(new EntPayServiceImpl(service));
            map.put(app.getAppId(), service);
            return service;
        }
    }

    @Deprecated
    @Override
    public WxMaService getWxMaService(SessionUser user) throws Exception {
        SWxApp app = appService.find(user);
        return getWxMaService(app);
    }



    @Deprecated
    @Override
    public WxPayService getWxPayService(SessionUser user) throws Exception {
        SWxApp app = appService.find(user);
        return getWxPayService(app);
    }




    @Override
    public WxMaService getWxMaService() {
        if (wxMaService != null) {
            return wxMaService;
        } else {

            wxMaService = new WxMaServiceImpl();
            WxMaInMemoryConfig config = new WxMaInMemoryConfig();
            config.setAppid(WXContants.OWNER_APP_ID);
            config.setSecret(WXContants.OWNER_APP_SECRET);
            wxMaService.setWxMaConfig(config);
        }
        return wxMaService;
    }

}
