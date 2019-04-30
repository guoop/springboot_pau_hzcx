package com.soft.ware.rest.modular.im.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.im.model.SImGroups;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SImGroupsMapper extends BaseMapper<SImGroups> {
    List<Map<String, Object>> findMaps(@Param("params") Map<String, Object> params);

}
