package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import org.apache.ibatis.annotations.Param;

public interface TblOwnerStaffMapper extends BaseMapper<TblOwnerStaff> {

    TOwnerStaff findStaffByPhone(@Param(value = "phone")String phone);
}
