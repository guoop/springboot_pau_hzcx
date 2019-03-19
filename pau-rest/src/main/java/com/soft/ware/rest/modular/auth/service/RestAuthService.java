package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.SecUser;
import com.soft.ware.rest.modular.auth.controller.dto.AuthRequest;

public interface RestAuthService extends IService<SecUser> {

    /**
     * 根据用户名查询用户信息
     * @param req
     * @return
     */
    SecUser findByUsername(AuthRequest req);
}
