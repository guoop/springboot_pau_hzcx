package com.soft.ware.rest.modular.device.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.device.model.TDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TDeviceMapper extends BaseMapper<TDevice> {
    List<Map<String, Object>> findMaps(@Param("params") Map<String, Object> params);

}
