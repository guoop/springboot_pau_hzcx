package com.soft.ware.rest.modular.order.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
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

    List<Map> findPage(SessionUser user, Page page, OrderPageParam param, int... source);

    List<Map<String,Object>> findMaps(Map<String,Object> map);

    Map<String,Object> findMap(Map<String,Object> map);

   // WxMaTemplateMessage buildOrderTemplateMessage(String templateID, String fromID, TOrder order);
}
