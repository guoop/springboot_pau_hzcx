package com.soft.ware.rest.modular.auth.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.common.persistence.dao.TblOwnerStaffMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.service.TblOwnerStaffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblOwnerStaffServiceImpl extends BaseService<TblOwnerStaffMapper,TblOwnerStaff> implements TblOwnerStaffService {

    @Resource
    private TblOwnerStaffMapper tblOwnerStaffMapper;

}