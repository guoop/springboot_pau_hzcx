package com.soft.ware.rest.modular.order_money_diff.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.order_money_diff.dao.voidMoneyDiffMapper;
import com.soft.ware.rest.modular.order_money_diff.model.voidMoneyDiff;
import com.soft.ware.rest.modular.order_money_diff.service.voidMoneyDiffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class voidMoneyDiffServiceImpl extends BaseService<voidMoneyDiffMapper,voidMoneyDiff> implements voidMoneyDiffService {

    @Resource
    private voidMoneyDiffMapper mapper;

}