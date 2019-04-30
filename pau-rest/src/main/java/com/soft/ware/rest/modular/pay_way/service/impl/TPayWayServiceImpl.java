package com.soft.ware.rest.modular.pay_way.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.pay_way.dao.TPayWayMapper;
import com.soft.ware.rest.modular.pay_way.model.TPayWay;
import com.soft.ware.rest.modular.pay_way.service.TPayWayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TPayWayServiceImpl extends BaseService<TPayWayMapper,TPayWay> implements TPayWayService {

    @Resource
    private TPayWayMapper mapper;

}