package com.soft.ware.rest.modular.auth.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.warpper.MapWrapper;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.service.TblOwnerService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class WxController extends BaseController {

    @Autowired
    private HzcxWxService hzcxWxService;

    @Autowired
    private TblOwnerService ownerService;


    /**
     * 获取openId
     * @param code
     * @return
     */
    @RequestMapping(value = {"/customer/v1/wx_identifier"})
    public Object wx_identifier(String code, HttpServletRequest request) throws Exception {
        String appId = WXUtils.getAppId(request);
        TblOwner owner = ownerService.findByAppId(appId);
        WxMaService service = hzcxWxService.getWxMaService(owner);
        MapWrapper map = getOpenId(service, appId, code);
        map.put("owner", BeanMapUtils.toMap(owner, true));
        return warpObject(map);
    }

    /**
     * 商户版小程序获取openId
     * @param code
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/owner/share/wx_identifier"})
    public Object owner_wx_identifier(String code, HttpServletRequest request) throws Exception {
        String appId = WXContants.OWNER_APP_ID;
        WxMaService service = hzcxWxService.getWxMaService();
        return warpObject(getOpenId(service, appId, code));
    }

    public MapWrapper getOpenId(WxMaService service,String appId,String code) throws Exception {
        WxMaJscode2SessionResult result = service.jsCode2SessionInfo(code);
        MapWrapper map = new MapWrapper();
        map.put("code", SUCCESS);
        map.put("openid", result.getOpenid());
        return map;
    }



}
