package com.soft.ware.rest.modular.owner.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import org.apache.ibatis.annotations.Param;

public interface OwnerMapper extends BaseMapper<TblOwner> {

    TblOwner findOwnerByOwner(@Param(value = "owner") String owner);
}
