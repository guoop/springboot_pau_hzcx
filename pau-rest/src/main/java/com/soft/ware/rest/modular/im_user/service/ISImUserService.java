package com.soft.ware.rest.modular.im_user.service;
import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.im_user.model.SImUser;

import java.util.List;
import java.util.Map;

public interface ISImUserService extends IService<SImUser> {

    List<Map<String,Object>> findMaps(Map<String, Object> map);

    Map<String,Object> findMap(Map<String, Object> map);
}
