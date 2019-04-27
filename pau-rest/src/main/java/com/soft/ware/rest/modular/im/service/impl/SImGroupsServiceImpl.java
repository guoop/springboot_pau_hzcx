package com.soft.ware.rest.modular.im.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.im.dao.SImGroupsMapper;
import com.soft.ware.rest.modular.im.model.SImGroups;
import com.soft.ware.rest.modular.im.service.ISImGroupsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
    public Kv<String, Object> findMap(Map<String, Object> map) {
    List<Map<String, Object>> maps = findMaps(map);
        return maps.size() == 1 ? null : Kv.toKv(maps.get(0));
    }

    @Override
    public List<SImGroups> find(SessionUser user, int type) {
        return mapper.selectList(new EntityWrapper<>(new SImGroups().setOwnerId(user.getOwnerId()).setType(type)));
    }



    public SImGroups findByUsername(String username){
        SImGroups g = mapper.selectOne(new SImGroups().setOwnerUsername(username));
        return g;
    }


    @Override
    public boolean saveOrUpdate(SessionUser user, SImGroups g) {
        SImGroups old = findByUsername(g.getOwnerUsername());
        int row;
        if (old == null) {
            g.setOwnerId(user.getOwnerId());
            g.setOwnerUsername(g.getOwnerUsername());
            g.setId(IdGenerator.getId());
            g.setCreateTime(new Date());
            row = mapper.insert(g);
        } else {
            g.setUpdateTime(new Date());
            row = mapper.updateById(g);
        }
        return row == 0;
    }

    @Override
    public boolean deleteByUsername(SessionUser user, String ownerUsername) {
        SImGroups g = findByUsername(ownerUsername);
        if (g != null) {
            return delete(new EntityWrapper<>(new SImGroups().setId(g.getId())));
        }
        return true;
    }


}