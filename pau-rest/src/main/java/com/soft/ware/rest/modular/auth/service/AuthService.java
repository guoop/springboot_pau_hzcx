package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;

public interface AuthService extends IService<TblOwnerStaff> {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    TblOwnerStaff findByUsername(String username);
}
