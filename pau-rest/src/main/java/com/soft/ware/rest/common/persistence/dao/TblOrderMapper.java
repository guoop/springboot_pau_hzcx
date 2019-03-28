package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.modular.auth.controller.dto.OrderDeleteParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TblOrderMapper extends BaseMapper<TblOrder> {

    Long findListCount(@Param("user") SessionUser owner, @Param("param") OrderParam param, @Param("source") Integer... source);

    List<Map> findList(@Param("user") SessionUser owner, @Param("page") Page page, @Param("param") OrderParam param, @Param("source") Integer... source);

    TblOrder findByNo(@Param("user") SessionUser user, @Param("no") String no);

    int updateStatusByNo(@Param("user") SessionUser user, @Param("no") String no, @Param("status") String status);

    int customerDelete(@Param("user") SessionUser user, @Param("param") OrderDeleteParam param);

    int customerCancel(@Param("user") SessionUser user, @Param("param") OrderDeleteParam param);
    
    List<TblOrder> findOrderListByStatus(Map<String,Object> map);
    
}
