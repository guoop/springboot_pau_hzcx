package com.soft.ware.rest.modular.wx_app.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SWxAppMapper extends BaseMapper<SWxApp> {

    List<Map<String,Object>> findMap(@Param("params") Map<String, Object> map);

}
