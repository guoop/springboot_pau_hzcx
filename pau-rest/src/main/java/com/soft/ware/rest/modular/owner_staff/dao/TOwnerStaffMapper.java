package com.soft.ware.rest.modular.owner_staff.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TOwnerStaffMapper extends BaseMapper<TOwnerStaff> {

    List<TOwnerStaff> selectStaffByOwnerId(String ownerId);

    List<Map<String, Object>> findMaps(@Param("params") Map<String, Object> params);


}
