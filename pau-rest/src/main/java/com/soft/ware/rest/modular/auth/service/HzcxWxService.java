package com.soft.ware.rest.modular.auth.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.github.binarywang.wxpay.service.WxPayService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;

public interface HzcxWxService {


    /**
     * 获取微信支付service
     * @param owner
     * @return
     */
    WxPayService getWxPayService(TOwner owner);


    /**
     * 获取微信小程序service
     * @param owner
     * @return
     */
    WxMaService getWxMaService(TOwner owner);

    /**
     * 获取小程序 service
     * @param app
     * @return
     */
    WxMaService getWxMaService(SWxApp app);

    /**
     * 获取微信支付service
     * @param app
     * @return
     */
    WxPayService getWxPayService(SWxApp app);

    /**
     * 根据用户信息获取
     * @param owner
     * @return
     */
    WxMaService getWxMaService(SessionUser owner) throws Exception;

    /**
     * 根据用户信息获取
     * @param user
     * @return
     * @throws Exception
     */
    WxPayService getWxPayService(SessionUser user) throws Exception;

    /**
     * 获取商户版小程序 service
     * @return
     */
    WxMaService getWxMaService();

}
