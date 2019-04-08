package com.soft.ware.rest.modular.customer_history.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.customer_history.dao.TCustomerHistoryMapper;
import com.soft.ware.rest.modular.customer_history.model.TCustomerHistory;
import com.soft.ware.rest.modular.customer_history.service.TCustomerHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TCustomerHistoryServiceImpl extends BaseService<TCustomerHistoryMapper,TCustomerHistory> implements TCustomerHistoryService {

    @Resource
    private TCustomerHistoryMapper mapper;

}