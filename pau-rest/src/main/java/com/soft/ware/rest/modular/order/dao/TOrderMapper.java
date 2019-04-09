package com.soft.ware.rest.modular.order.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.order.model.TOrder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 线上订单信息 Mapper 接口
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface TOrderMapper extends BaseMapper<TOrder> {


    List<TOrder> selectOrderListByMap(Map<String,Object> map);
}
