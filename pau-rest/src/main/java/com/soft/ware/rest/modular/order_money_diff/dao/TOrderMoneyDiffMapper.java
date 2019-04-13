package com.soft.ware.rest.modular.order_money_diff.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.order_money_diff.model.TOrderMoneyDiff;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TOrderMoneyDiffMapper extends BaseMapper<TOrderMoneyDiff> {
    List<Map<String, Object>> findMaps(@Param("map") Map<String, Object> map);

}
