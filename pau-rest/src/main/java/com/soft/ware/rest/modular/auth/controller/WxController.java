package com.soft.ware.rest.modular.auth.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ResultView;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_config.service.ITOwnerConfigService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class WxController extends BaseController {

    @Autowired
    private HzcxWxService hzcxWxService;

    @Autowired
    private ITOwnerService ownerService;

    @Autowired
    private ISWxAppService appService;

    @Autowired
    private ITOwnerConfigService configService;


    /**
     * 获取openId
     * @param code
     * @return
     */
    @RequestMapping(value = {"/customer/v1/wx_identifier"})
    public ResultView wx_identifier(String code, HttpServletRequest request) throws Exception {
        String appId = WXUtils.getAppId(request);
        Map<String, Object> app = appService.findMap(Kv.by("appId", appId));
        Map<String, Object> owner = ownerService.findMap(Kv.by("id",app.get("ownerId")));
        WxMaService service = hzcxWxService.getWxMaService(BeanMapUtils.toObject(app, SWxApp.class));
        Map<String, Object> config = configService.findMap(Kv.by("ownerId", app.get("ownerId")));
        return renderOpenId(service, appId, code).set("owner", owner).merge("owner", app).merge("owner",config).del("owner", "app_secret");
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
