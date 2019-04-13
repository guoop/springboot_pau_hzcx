package com.soft.ware.rest.modular.im_user.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.im_user.dao.SImUserMapper;
import com.soft.ware.rest.modular.im_user.model.SImUser;
import com.soft.ware.rest.modular.im_user.service.ISImUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SImUserServiceImpl extends BaseService<SImUserMapper,SImUser> implements  ISImUserService {

    @Resource
    private SImUserMapper mapper;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Map<String, Object> findMap(Map<String, Object> map) {
    List<Map<String, Object>> maps = findMaps(map);
        return maps.isEmpty() ? null : maps.get(0);
    }


}