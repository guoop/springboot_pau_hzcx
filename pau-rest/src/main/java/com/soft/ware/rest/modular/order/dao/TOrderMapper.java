package com.soft.ware.rest.modular.order.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.auth.controller.dto.OrderDeleteParam;
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

    List<Map<String, Object>> findMaps(@Param("params") Map<String, Object> map);

    List<HashMap<String,Object>> selectOrdersListByMap(@Param("params") Map<String,Object> map, @Param("page") Page page);

    int selectOrdersListByMapCount(@Param("params") Map<String,Object> map,@Param("page") Page page);

    int customerDelete(@Param("user") SessionUser user, @Param("param") OrderDeleteParam param);

    int customerCancel(@Param("user") SessionUser user, @Param("param") OrderDeleteParam param);

    int selectOrderCount(String ownerId);

   Map<String,Object> selectOrderDetailById(String orderNo);

}
