package com.soft.ware.rest.modular.order_app.service;
import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.order_app.model.TOrderApp;

import java.util.List;
import java.util.Map;

public interface TOrderAppService extends IService<TOrderApp> {

    List<TOrderApp> getAppOrderList(Map<String,Object> map);

}
