package com.soft.ware.rest.modular.order_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.AddOrderParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.order_app.model.TOrderApp;

import java.util.List;
import java.util.Map;

public interface ITOrderAppService extends IService<TOrderApp> {

    List<Map<String, Object>> findMaps(Map<String, Object> map);

    Map<String,Object> findMap(Map<String,Object> map);

    TOrderApp findOne(Map<String, Object> map) throws Exception;

    long findPageCount(SessionUser user, OrderPageParam param, Integer[] sources);

    List<TOrderApp> getAppOrderList(Map<String,Object> map);

    List<Map<String, Object>> findMapPage(SessionUser user, Page page, OrderPageParam param, Integer... sources);

    TOrderApp addOrder(SessionUser user, AddOrderParam param);
}
