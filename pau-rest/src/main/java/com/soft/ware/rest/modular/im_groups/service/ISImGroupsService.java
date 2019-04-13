package com.soft.ware.rest.modular.im_groups.service;
import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.im_groups.model.SImGroups;

import java.util.List;
import java.util.Map;

public interface ISImGroupsService extends IService<SImGroups> {

    List<Map<String,Object>> findMaps(Map<String, Object> map);

    Map<String,Object> findMap(Map<String, Object> map);

    List<SImGroups> find(SessionUser user, int type);
}
