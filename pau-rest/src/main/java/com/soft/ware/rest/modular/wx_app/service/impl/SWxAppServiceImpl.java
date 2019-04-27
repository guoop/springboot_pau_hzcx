package com.soft.ware.rest.modular.wx_app.service.impl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
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
    public Map<String, Object> findMap(Map<String,Object> map) {
        List<Map<String, Object>> maps = findMaps(map);
        return maps.size() == 1 ? null : Kv.toKv(maps.get(0));
    }

    @Override
    public List<Map<String, Object>> findMaps(Map<String,Object> map) {
        return mapper.findMap(map);
    }

    @Override
    public SWxApp find(SessionUser user) {
        try {
            return BeanMapUtils.toObject(findMap(Kv.by("ownerId", user.getOwnerId())), SWxApp.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}