package com.soft.ware.rest.modular.app_version.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.app_version.model.TAppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TAppVersionMapper extends BaseMapper<TAppVersion> {
    List<Map<String, Object>> findMaps(@Param("params") Map<String, Object> params);

}
