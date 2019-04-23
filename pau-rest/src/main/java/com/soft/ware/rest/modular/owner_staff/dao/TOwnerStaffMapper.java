package com.soft.ware.rest.modular.owner_staff.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;

import java.util.List;

public interface TOwnerStaffMapper extends BaseMapper<TOwnerStaff> {

    List<TOwnerStaff> selectStaffByOwnerId(String ownerId);




}
