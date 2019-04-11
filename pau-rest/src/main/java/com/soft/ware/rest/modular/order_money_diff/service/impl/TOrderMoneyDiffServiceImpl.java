package com.soft.ware.rest.modular.order_money_diff.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.order_money_diff.dao.TOrderMoneyDiffMapper;
import com.soft.ware.rest.modular.order_money_diff.model.TOrderMoneyDiff;
import com.soft.ware.rest.modular.order_money_diff.service.TOrderMoneyDiffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TOrderMoneyDiffServiceImpl extends BaseService<TOrderMoneyDiffMapper,TOrderMoneyDiff> implements TOrderMoneyDiffService {

    @Resource
    private TOrderMoneyDiffMapper mapper;

}