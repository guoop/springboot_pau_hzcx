package com.soft.ware.rest.modular.order_app.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.order_app.model.TOrderApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TOrderAppMapper extends BaseMapper<TOrderApp>  {
    List<Map<String, Object>> findMaps(@Param("params") Map<String, Object> params);

    List<TOrderApp> getAppOrderList(Map<String,Object> map);

}
