package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.rest.common.persistence.dao.ImUserMapper;
import com.soft.ware.rest.common.persistence.model.ImUser;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.ImUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Transactional
public class ImUserServiceImpl extends BaseService<ImUserMapper,ImUser> implements ImUserService {

    @Resource
    private ImUserMapper mapper;

    public ImUser findByUsername(String username){
        ImUser user = mapper.selectOne(new ImUser().setUsername(username));
        return user;
    }


    @Override
    public boolean saveOrUpdate(SessionUser user, ImUser u) {
        ImUser old = findByUsername(u.getUsername());
        u.setOwnerId(user.getOwnerId());
        int row;
        if (old == null) {
            u.setId(IdGenerator.getId());
            u.setCreateTime(new Date());
            u.setCreater(user.getName());
            row = mapper.insert(u);
        } else {
            u.setOwnerId(user.getOwnerId());
            u.setId(old.getId());
            row = mapper.updateById(u);
        }

        return row == 0;

    }

    @Override
    public boolean deleteByUsername(SessionUser user, String username) {
        ImUser u = findByUsername(username);
        if (u != null) {
            return delete(new EntityWrapper<>(new ImUser().setId(u.getId()).setOwnerId(user.getOwnerId())));
        }
        return false;
    }
}