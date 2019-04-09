package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.ImGroups;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

public interface ImGroupsService extends IService<ImGroups> {

    /**
     * 添加或更新群组，根据ownerUsername
     * @param user
     * @param g
     * @return
     */
    boolean saveOrUpdate(SessionUser user, ImGroups g);

    /**
     * 删除群组，根据ownerUsername
     * @param user
     * @param ownerUsername
     * @return
     */
    boolean deleteByUsername(SessionUser user, String ownerUsername);
}
