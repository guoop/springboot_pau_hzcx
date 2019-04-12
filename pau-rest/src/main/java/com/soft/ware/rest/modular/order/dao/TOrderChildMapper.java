package com.soft.ware.rest.modular.order.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.order.model.TOrderChild;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author paulo123
 * @since 2019-04-11
 */
public interface TOrderChildMapper extends BaseMapper<TOrderChild> {

    List<Map> findMaps(Map<String, Object> map);
}
