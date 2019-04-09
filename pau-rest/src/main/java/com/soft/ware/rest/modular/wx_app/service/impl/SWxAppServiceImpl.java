package com.soft.ware.rest.modular.wx_app.service.impl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.wx_app.dao.SWxAppMapper;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SWxAppServiceImpl extends BaseService<SWxAppMapper,SWxApp> implements ISWxAppService {

    @Resource
    private SWxAppMapper mapper;

    @Override
    public SWxApp findByAppId(String appId) {
        return selectOne(new EntityWrapper<>(new SWxApp().setAppId(appId)));
    }

    @Override
    public SWxApp find(TOwner owner) {
        return selectOne(new EntityWrapper<>(new SWxApp().setOwnerId(owner.getId())));
    }

    @Override
    public List<Map<String,Object>> find(Map<String, Object> map) {
        return mapper.find(map);
    }
}