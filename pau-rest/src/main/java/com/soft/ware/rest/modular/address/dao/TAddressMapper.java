package com.soft.ware.rest.modular.address.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.address.model.TAddress;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TAddressMapper extends BaseMapper<TAddress> {


    List<Map<String, Object>> findMaps(@Param("map") Map<String, Object> map);

    int deleteDefaultAddress(@Param("user") SessionUser user);
}
