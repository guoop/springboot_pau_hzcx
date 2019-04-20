package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.ImGroupsService;
import com.soft.ware.rest.modular.im_groups.dao.SImGroupsMapper;
import com.soft.ware.rest.modular.im_groups.model.SImGroups;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Transactional
public class ImGroupsServiceImpl extends BaseService<SImGroupsMapper, SImGroups> implements ImGroupsService {

    @Resource
    private SImGroupsMapper mapper;

    public SImGroups findByUsername(String username){
        SImGroups g = mapper.selectOne(new SImGroups().setOwnerUsername(username));
        return g;
    }


    @Override
    public boolean saveOrUpdate(SessionUser user, SImGroups g) {
        SImGroups old = findByUsername(g.getOwnerUsername());
        int row;
        if (old == null) {
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