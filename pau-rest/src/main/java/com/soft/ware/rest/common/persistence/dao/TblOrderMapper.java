package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.modular.auth.controller.dto.OrderDeleteParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TblOrderMapper extends BaseMapper<TblOrder> {

    Long findListCount(@Param("user") SessionUser owner, @Param("param") OrderPageParam param, @Param("source") Integer... source);

    List<Map> findList(@Param("user") SessionUser owner, @Param("page") Page page, @Param("param") OrderPageParam param, @Param("source") Integer... source);

    int updateStatusByNo(@Param("user") SessionUser user, @Param("no") String no, @Param("status") String status);

    int customerDelete(@Param("user") SessionUser user, @Param("param") OrderDeleteParam param);

    int customerCancel(@Param("user") SessionUser user, @Param("param") OrderDeleteParam param);
    
    List<TblOrder> findOrderListByStatus(Map<String,Object> map);

    List<Map> findOwnerOrderList(@Param("user") SessionUser user, @Param("page") Page page, @Param("param") OrderPageParam param);

    long findOwnerOrderListCount(@Param("user") SessionUser user, @Param("param") OrderPageParam param);

    Map findOwnerOrder(@Param("user") SessionUser user, @Param("no") String no);
}
