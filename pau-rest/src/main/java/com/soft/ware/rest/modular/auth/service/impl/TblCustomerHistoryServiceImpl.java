package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblCustomerHistoryMapper;
import com.soft.ware.rest.common.persistence.model.TblCustomerHistory;
import com.soft.ware.rest.modular.auth.service.TblCustomerHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblCustomerHistoryServiceImpl extends ServiceImpl<TblCustomerHistoryMapper,TblCustomerHistory> implements TblCustomerHistoryService {

    @Resource
    private TblCustomerHistoryMapper tblCustomerHistoryMapper;

}