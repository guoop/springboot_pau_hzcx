package com.soft.ware.rest.modular.auth.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.common.persistence.dao.TblOrderAppMapper;
import com.soft.ware.rest.common.persistence.model.TblOrderApp;
import com.soft.ware.rest.modular.auth.service.TblOrderAppService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblOrderAppServiceImpl extends BaseService<TblOrderAppMapper,TblOrderApp> implements TblOrderAppService {

    @Resource
    private TblOrderAppMapper tblOrderAppMapper;

}