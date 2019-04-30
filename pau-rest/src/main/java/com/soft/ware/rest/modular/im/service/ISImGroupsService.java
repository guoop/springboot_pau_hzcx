package com.soft.ware.rest.modular.im.service;
import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.im.model.SImGroups;

import java.util.List;
import java.util.Map;

public interface ISImGroupsService extends IService<SImGroups> {

    List<Map<String,Object>> findMaps(Map<String, Object> map);

    Map<String,Object> findMap(Map<String, Object> map);

    List<SImGroups> find(SessionUser user, int type);

    /**
     * 添加或更新群组，根据ownerUsername
     * @param user
     * @param g
     * @return
     */
    boolean saveOrUpdate(SessionUser user, SImGroups g);

    /**
     * 删除群组，根据ownerUsername
     * @param user
     * @param ownerUsername
     * @return
     */
    boolean deleteByUsername(SessionUser user, String ownerUsername);
}
