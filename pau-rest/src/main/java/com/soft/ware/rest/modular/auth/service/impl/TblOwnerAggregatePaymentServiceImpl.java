package com.soft.ware.rest.modular.auth.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.common.persistence.dao.TblOwnerAggregatePaymentMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerAggregatePayment;
import com.soft.ware.rest.modular.auth.service.TblOwnerAggregatePaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblOwnerAggregatePaymentServiceImpl extends BaseService<TblOwnerAggregatePaymentMapper,TblOwnerAggregatePayment> implements TblOwnerAggregatePaymentService {

    @Resource
    private TblOwnerAggregatePaymentMapper tblOwnerAggregatePaymentMapper;

}