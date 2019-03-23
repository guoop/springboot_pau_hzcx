package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.modular.auth.controller.dto.OrderParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TblOrderMapper extends BaseMapper<TblOrder> {

    Long findListCount(@Param("owner") String owner, @Param("param") OrderParam status);

    List<Map> findList(@Param("owner") String owner, @Param("page") Page page, @Param("param") OrderParam param);

    Map<String, Object> findByNo(@Param("user") SessionUser user, @Param("no") String no);

    int updateStatusByNo(@Param("user") SessionUser user, @Param("no") String no, @Param("status") String status);
}
