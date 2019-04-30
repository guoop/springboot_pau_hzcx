package com.soft.ware.rest.modular.wx_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;

import java.util.List;
import java.util.Map;

public interface ISWxAppService extends IService<SWxApp> {

    SWxApp findByAppId(String appId);

    SWxApp find(TOwner owner);

    Map<String,Object> findMap(Map<String, Object> appId);

    List<Map<String, Object>> findMaps(Map<String, Object> map);

    SWxApp find(SessionUser user);
}
