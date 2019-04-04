package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.rest.common.persistence.dao.ImGroupsMapper;
import com.soft.ware.rest.common.persistence.model.ImGroups;
import com.soft.ware.rest.modular.auth.controller.dto.SessionOwnerUser;
import com.soft.ware.rest.modular.auth.service.ImGroupsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Transactional
public class ImGroupsServiceImpl extends BaseService<ImGroupsMapper,ImGroups> implements ImGroupsService {

    @Resource
    private ImGroupsMapper mapper;

    public ImGroups findByUsername(String username){
        ImGroups g = mapper.selectOne(new ImGroups().setOwnerUsername(username));
        return g;
    }


    @Override
    public boolean saveOrUpdate(SessionOwnerUser user, ImGroups g) {
        ImGroups old = findByUsername(g.getOwnerUsername());
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
    public boolean deleteByUsername(SessionOwnerUser user, String ownerUsername) {
        ImGroups g = findByUsername(ownerUsername);
        if (g != null) {
            return delete(new EntityWrapper<>(new ImGroups().setId(g.getId())));
        }
        return true;
    }
}