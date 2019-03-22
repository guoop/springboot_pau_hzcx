package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblOrderAppMapper;
import com.soft.ware.rest.common.persistence.model.TblOrderApp;
import com.soft.ware.rest.modular.auth.service.TblOrderAppService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblOrderAppServiceImpl extends ServiceImpl<TblOrderAppMapper,TblOrderApp> implements TblOrderAppService {

    @Resource
    private TblOrderAppMapper tblOrderAppMapper;

}