package com.soft.ware.rest.modular.order_money_diff.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.order_money_diff.dao.TOrderMoneyDiffMapper;
import com.soft.ware.rest.modular.order_money_diff.model.TOrderMoneyDiff;
import com.soft.ware.rest.modular.order_money_diff.service.ITOrderMoneyDiffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TOrderMoneyDiffServiceImpl extends BaseService<TOrderMoneyDiffMapper,TOrderMoneyDiff> implements  ITOrderMoneyDiffService {

    @Resource
    private TOrderMoneyDiffMapper mapper;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Kv<String, Object> findMap(Map<String, Object> map) {
    List<Map<String, Object>> maps = findMaps(map);
        return maps.size() == 1 ? Kv.toKv(maps.get(0)) : null;
    }

    @Override
    public boolean update(TOrderMoneyDiff order, SessionUser user) throws Exception {

        //todo yancc 需要加锁么？
        Integer update = mapper.update(order, new EntityWrapper<>(new TOrderMoneyDiff().setId(order.getId())));
        if (update != 1) {
            throw new PauException(BizExceptionEnum.ORDER_CREATE_FAIL);
        }
        return true;
    }


}