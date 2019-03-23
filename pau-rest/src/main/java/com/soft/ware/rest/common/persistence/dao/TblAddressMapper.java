package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblAddress;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TblAddressMapper extends BaseMapper<TblAddress> {

    List<TblAddress> findAll(@Param("user") SessionUser user);


    int deleteDefaultAddress(@Param("user") SessionUser user);
}
