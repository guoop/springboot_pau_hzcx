package com.soft.ware.rest.modular;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.app_version.model.TAppVersion;
import com.soft.ware.rest.modular.app_version.service.ITAppVersionService;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AppOtherController extends BaseController {

    @Autowired
    private ITAppVersionService appVersionService;

    @RequestMapping(value = "/version/check",method = RequestMethod.GET)
    public Object appVersion() throws Exception {
        TAppVersion v = appVersionService.findLast(TAppVersion.PLATFORM_CODE_APP_ANDROID);
        Kv<String, String> kv = Kv.by("download_url", v.getDownloadUrl()).set("description", v.getDescription()).set("version", v.getVersion());
        return render().set("force", v.getIsForce()).set("newVersion", kv);
    }

    /**
     * 极光im初始化
     */
    @RequestMapping("/owner/im/init")
    public Tip getPayLoad(){
        Map<String, Object> map = WXUtils.getPayLoad();
        return new SuccessTip(map);
    }



}
