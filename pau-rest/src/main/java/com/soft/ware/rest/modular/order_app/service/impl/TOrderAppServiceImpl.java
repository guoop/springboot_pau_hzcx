package com.soft.ware.rest.modular.order_app.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.order_app.dao.TOrderAppMapper;
import com.soft.ware.rest.modular.order_app.model.TOrderApp;
import com.soft.ware.rest.modular.order_app.service.TOrderAppService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TOrderAppServiceImpl extends BaseService<TOrderAppMapper,TOrderApp> implements TOrderAppService {

    @Resource
    private TOrderAppMapper mapper;

    @Override
    public List<TOrderApp> getAppOrderList(Map<String, Object> map) {
        return null;
    }
}