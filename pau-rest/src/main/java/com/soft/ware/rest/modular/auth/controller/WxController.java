package com.soft.ware.rest.modular.auth.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.util.ResultView;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class WxController extends BaseController {

    @Autowired
    private HzcxWxService hzcxWxService;

    @Autowired
    private ITOwnerService ownerService;

    @Resource
    private ISWxAppService appService;


    /**
     * 获取openId
     * @param code
     * @return
     */
    @RequestMapping(value = {"/customer/v1/wx_identifier"})
    public Object wx_identifier(String code, HttpServletRequest request) throws Exception {
        String appId = WXUtils.getAppId(request);
        SWxApp app = appService.findByAppId(appId);
        TOwner owner = ownerService.find(app);
        WxMaService service = hzcxWxService.getWxMaService(app);
        return renderOpenId(service, appId, code).set("owner", BeanMapUtils.toMap(owner, true));
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
        return renderOpenId(service, appId, code);
    }

    public ResultView renderOpenId(WxMaService service, String appId, String code) throws Exception {
        WxMaJscode2SessionResult result = service.jsCode2SessionInfo(code);
        return render().set("openid", result.getOpenid());
    }



}
