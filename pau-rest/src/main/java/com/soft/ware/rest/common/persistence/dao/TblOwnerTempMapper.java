package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerTemp;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import org.apache.ibatis.annotations.Param;

public interface TblOwnerTempMapper extends BaseMapper<TblOwnerTemp> {


    /**
     * 根据用户信息查询消息模板
     * @param user
     * @return
     */
    TblOwnerTemp findByUser(@Param("user") SessionUser user);
}
