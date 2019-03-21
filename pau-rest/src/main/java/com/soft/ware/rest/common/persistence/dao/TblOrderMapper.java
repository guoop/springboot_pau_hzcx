package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TblOrderMapper extends BaseMapper<TblOrder> {

    Long findListCountByStatus(@Param("user") TblOwnerStaff user, @Param("status") String status);

    List<Map> findListByStatus(@Param("user") TblOwnerStaff user, @Param("page") Page page, @Param("status") String status);

    Map<String, Object> findByNo(@Param("user") TblOwnerStaff user, @Param("no") String no);

    int updateStatusByNo(@Param("user") TblOwnerStaff user, @Param("no") String no, @Param("status") String status);
}
