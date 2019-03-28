package com.soft.ware.rest.modular.auth.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.github.binarywang.wxpay.service.WxPayService;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

public interface HzcxWxService {


    /**
     * 获取微信支付service
     * @param owner
     * @return
     */
    WxPayService getWxPayService(TblOwner owner);


    /**
     * 获取微信小程序service
     * @param owner
     * @return
     */
    WxMaService getWxMaService(TblOwner owner);

    /**
     * 根据用户信息获取
     * @param owner
     * @return
     */
    WxMaService getWxMaService(SessionUser owner);

    WxPayService getWxPayService(SessionUser user);
}
