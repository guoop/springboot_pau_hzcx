package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblOwnerGroupsMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerGroups;
import com.soft.ware.rest.modular.auth.service.TblOwnerGroupsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblOwnerGroupsServiceImpl extends ServiceImpl<TblOwnerGroupsMapper,TblOwnerGroups> implements TblOwnerGroupsService {

    @Resource
    private TblOwnerGroupsMapper tblOwnerGroupsMapper;

}