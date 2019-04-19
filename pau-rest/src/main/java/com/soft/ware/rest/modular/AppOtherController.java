package com.soft.ware.rest.modular;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.common.persistence.model.TblAppBase;
import com.soft.ware.rest.modular.app_version.model.TAppVersion;
import com.soft.ware.rest.modular.app_version.service.ITAppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppOtherController extends BaseController {

    @Autowired
    private ITAppVersionService appVersionService;

    @RequestMapping(value = "/version/check",method = RequestMethod.GET)
    public Object appVersion() throws Exception {
        TAppVersion v = appVersionService.findLast(TblAppBase.PLATFORM_CODE_APP_ANDROID);
        Kv<String, String> kv = Kv.by("download_url", v.getDownloadUrl()).set("description", v.getDescription()).set("version", v.getVersion());
        return render().set("force", v.getIsForce()).set("newVersion", kv);
    }


}
