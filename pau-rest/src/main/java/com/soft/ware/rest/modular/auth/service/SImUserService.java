package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.im_user.model.SImUser;

public interface SImUserService extends IService<SImUser> {


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
