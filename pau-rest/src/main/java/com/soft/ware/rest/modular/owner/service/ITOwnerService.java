package com.soft.ware.rest.modular.owner.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商户信息 服务类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface ITOwnerService extends IService<TOwner> {

    TOwner find(SWxApp app);

    TOwner findByAppId(String appId);

    List<Map<String,Object>> findMaps(Map<String,Object> map);

    Map<String,Object> findMap(Map<String,Object> map);
}
