package com.soft.ware.rest.modular.wx_app.service;
import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;

public interface ISWxAppService extends IService<SWxApp> {

    SWxApp findByAppId(String appId);

    SWxApp find(TblOwner owner);
}
