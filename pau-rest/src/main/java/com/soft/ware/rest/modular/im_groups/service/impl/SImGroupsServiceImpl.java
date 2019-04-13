package com.soft.ware.rest.modular.im_groups.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.im_groups.dao.SImGroupsMapper;
import com.soft.ware.rest.modular.im_groups.model.SImGroups;
import com.soft.ware.rest.modular.im_groups.service.ISImGroupsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SImGroupsServiceImpl extends BaseService<SImGroupsMapper,SImGroups> implements  ISImGroupsService {

    @Resource
    private SImGroupsMapper mapper;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Map<String, Object> findMap(Map<String, Object> map) {
    List<Map<String, Object>> maps = findMaps(map);
        return maps.isEmpty() ? null : maps.get(0);
    }

    @Override
    public List<SImGroups> find(SessionUser user, int type) {
        return mapper.selectList(new EntityWrapper<>(new SImGroups().setOwnerId(user.getOwnerId()).setType(type)));
    }


}