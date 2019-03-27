package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblOwnerGroupsMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerGroups;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.TblOwnerGroupsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class TblOwnerGroupsServiceImpl extends ServiceImpl<TblOwnerGroupsMapper,TblOwnerGroups> implements TblOwnerGroupsService {

    @Resource
    private TblOwnerGroupsMapper ownerGroupsMapper;

    @Override
    public List<TblOwnerGroups> find(SessionUser user, int type) {
        return ownerGroupsMapper.selectList(new EntityWrapper<>(new TblOwnerGroups().setOwner(user.getOwner()).setType(type)));
    }
}