package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblAppBaseMapper;
import com.soft.ware.rest.common.persistence.model.TblAppBase;
import com.soft.ware.rest.modular.auth.service.TblAppBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblAppBaseServiceImpl extends ServiceImpl<TblAppBaseMapper,TblAppBase> implements TblAppBaseService {

    @Resource
    private TblAppBaseMapper tblAppBaseMapper;

}