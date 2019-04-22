package com.soft.ware.rest.modular.im.service;
import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.im.model.SImUser;

import java.util.List;
import java.util.Map;

public interface ISImUserService extends IService<SImUser> {

    List<Map<String,Object>> findMaps(Map<String, Object> map);

    Map<String,Object> findMap(Map<String, Object> map);

    /**
     * 添加或更新群组，根据username
     * @param user
     * @param u
     * @return
     */
    boolean saveOrUpdate(SessionUser user, SImUser u);

    /**
     * 删除用户，根据username
     * @param user
     * @param username
     * @return
     */
    boolean deleteByUsername(SessionUser user, String username);

}
