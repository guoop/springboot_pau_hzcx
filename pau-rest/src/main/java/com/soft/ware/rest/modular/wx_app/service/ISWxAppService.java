package com.soft.ware.rest.modular.wx_app.service;
import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;

public interface ISWxAppService extends IService<SWxApp> {

    SWxApp findByAppId(String appId);

    SWxApp find(TOwner owner);
}
