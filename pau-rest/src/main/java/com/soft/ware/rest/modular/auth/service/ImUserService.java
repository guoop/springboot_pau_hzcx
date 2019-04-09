package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.ImUser;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

public interface ImUserService extends IService<ImUser> {


    /**
     * 添加或更新群组，根据username
     * @param user
     * @param u
     * @return
     */
    boolean saveOrUpdate(SessionUser user, ImUser u);

    /**
     * 删除用户，根据username
     * @param user
     * @param username
     * @return
     */
    boolean deleteByUsername(SessionUser user, String username);
}
