package com.soft.ware.rest.modular.auth.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.common.persistence.dao.TblCustomerHistoryMapper;
import com.soft.ware.rest.common.persistence.model.TblCustomerHistory;
import com.soft.ware.rest.modular.auth.service.TblCustomerHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblCustomerHistoryServiceImpl extends BaseService<TblCustomerHistoryMapper,TblCustomerHistory> implements TblCustomerHistoryService {

    @Resource
    private TblCustomerHistoryMapper tblCustomerHistoryMapper;

}