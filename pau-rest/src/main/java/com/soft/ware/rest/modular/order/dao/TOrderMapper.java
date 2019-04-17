package com.soft.ware.rest.modular.order.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.auth.controller.dto.OrderDeleteParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.order.model.TOrder;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
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

    Long findListCount(@Param("user") SessionUser user, @Param("param") OrderPageParam param, @Param("source") int... source);

    List<Map> findList(@Param("user") SessionUser user, @Param("page") Page page, @Param("param") OrderPageParam param, @Param("source") int... source);

    List<Map<String, Object>> findMaps(@Param("map") Map<String, Object> map);

    List<HashMap<String,Object>> selectOrdersListByMap(Map<String,Object> map);

    int customerDelete(@Param("user") SessionUser user, @Param("param") OrderDeleteParam param);

    int customerCancel(@Param("user") SessionUser user, @Param("param") OrderDeleteParam param);

    int selectOrderCount(String ownerId);

   Map<String,Object> selectOrderDetailById(String orderNo);

    boolean updateOrderAddressAndPhone(SessionUser user, String id, String phone, Integer version);
}
