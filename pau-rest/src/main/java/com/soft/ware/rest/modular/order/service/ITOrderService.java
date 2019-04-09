package com.soft.ware.rest.modular.order.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.order.model.TOrder;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 线上订单信息 服务类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface ITOrderService extends IService<TOrder> {


        List<TOrder> selectOrderListByMap(Map<String,Object> param);
}
