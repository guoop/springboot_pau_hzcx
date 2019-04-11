package com.soft.ware.rest.modular.owner_config.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.owner_config.model.TOwnerConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TOwnerConfigMapper extends BaseMapper<TOwnerConfig> {

    List<Map<String, Object>> findMaps(@Param("map") Map<String, Object> map);

}
