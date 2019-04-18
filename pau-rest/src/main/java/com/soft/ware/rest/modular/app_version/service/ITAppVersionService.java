package com.soft.ware.rest.modular.app_version.service;
import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.app_version.model.TAppVersion;

import java.util.List;
import java.util.Map;

public interface ITAppVersionService extends IService<TAppVersion> {

    List<Map<String,Object>> findMaps(Map<String, Object> map);

    Map<String,Object> findMap(Map<String, Object> map);

    TAppVersion findOne(Map<String, Object> map) throws Exception;

    TAppVersion findLast(String platformCode) throws Exception;

}
