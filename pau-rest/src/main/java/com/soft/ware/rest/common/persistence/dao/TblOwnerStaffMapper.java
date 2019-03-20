package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import org.apache.ibatis.annotations.Param;

public interface TblOwnerStaffMapper extends BaseMapper<TblOwnerStaff> {

    TblOwnerStaff findStaffByPhone(@Param(value = "phone")String phone);
}
