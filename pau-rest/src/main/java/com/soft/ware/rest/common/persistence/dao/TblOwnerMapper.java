package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import org.apache.ibatis.annotations.Param;

public interface TblOwnerMapper extends BaseMapper<TblOwner> {

    TblOwner findOwnerByOwner(@Param(value = "owner") String owner);
}
