package com.soft.ware.rest.modular.order_money_diff.service;
import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.order_money_diff.model.TOrderMoneyDiff;

import java.util.List;
import java.util.Map;

public interface ITOrderMoneyDiffService extends IService<TOrderMoneyDiff> {

    List<Map<String,Object>> findMaps(Map<String,Object> map);

    Map<String,Object> findMap(Map<String,Object> map);

    boolean update(TOrderMoneyDiff order, SessionUser user) throws Exception;
}
