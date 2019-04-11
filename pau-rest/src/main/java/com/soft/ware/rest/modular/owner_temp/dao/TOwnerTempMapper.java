package com.soft.ware.rest.modular.owner_temp.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.owner_temp.model.TOwnerTemp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TOwnerTempMapper extends BaseMapper<TOwnerTemp> {

    List<Map<String, Object>> findMaps(@Param("map") Map<String, Object> map);

}
