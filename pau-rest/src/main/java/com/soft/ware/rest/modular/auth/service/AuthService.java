package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;

public interface AuthService extends IService<TOwnerStaff> {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    TOwnerStaff findByUsername(String username);
}
