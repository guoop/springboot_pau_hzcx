package com.soft.ware.rest.modular.app_version.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.app_version.dao.TAppVersionMapper;
import com.soft.ware.rest.modular.app_version.model.TAppVersion;
import com.soft.ware.rest.modular.app_version.service.ITAppVersionService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TAppVersionServiceImpl extends BaseService<TAppVersionMapper,TAppVersion> implements  ITAppVersionService {

    @Resource
    private TAppVersionMapper mapper;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Kv<String, Object> findMap(Map<String, Object> map) {
    List<Map<String, Object>> maps = findMaps(map);
        return maps.size() == 1 ? null : Kv.toKv(maps.get(0));
    }

    @Override
    public TAppVersion findOne(Map<String,Object> map) throws Exception {
        return BeanMapUtils.toObject(map, TAppVersion.class);
    }

    @Override
    public TAppVersion findLast(String platformCode) throws Exception {
        return BeanMapUtils.toObject(findMap(Kv.obj("code", platformCode)), TAppVersion.class);
    }
}