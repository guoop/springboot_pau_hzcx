package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblOwnerAggregatePaymentMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerAggregatePayment;
import com.soft.ware.rest.modular.auth.service.TblOwnerAggregatePaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblOwnerAggregatePaymentServiceImpl extends ServiceImpl<TblOwnerAggregatePaymentMapper,TblOwnerAggregatePayment> implements TblOwnerAggregatePaymentService {

    @Resource
    private TblOwnerAggregatePaymentMapper tblOwnerAggregatePaymentMapper;

}