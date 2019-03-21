package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblRepositoryMapper;
import com.soft.ware.rest.common.persistence.model.TblRepository;
import com.soft.ware.rest.modular.auth.service.TblRepositoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblRepositoryServiceImpl extends ServiceImpl<TblRepositoryMapper,TblRepository> implements TblRepositoryService {

    @Resource
    private TblRepositoryMapper tblRepositoryMapper;

}