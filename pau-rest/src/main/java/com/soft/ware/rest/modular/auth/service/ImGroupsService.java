package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.ImGroups;
import com.soft.ware.rest.modular.auth.controller.dto.SessionOwnerUser;

public interface ImGroupsService extends IService<ImGroups> {

    /**
     * 添加或更新群组，根据ownerUsername
     * @param user
     * @param g
     * @return
     */
    boolean saveOrUpdate(SessionOwnerUser user, ImGroups g);

    /**
     * 删除群组，根据ownerUsername
     * @param user
     * @param ownerUsername
     * @return
     */
    boolean deleteByUsername(SessionOwnerUser user, String ownerUsername);
}
