package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.im_groups.model.SImGroups;

public interface ImGroupsService extends IService<SImGroups> {

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
