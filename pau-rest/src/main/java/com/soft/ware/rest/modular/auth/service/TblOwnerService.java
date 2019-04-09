package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

public interface TblOwnerService extends IService<TblOwner> {

    TblOwner find(SessionUser user);

    TblOwner find(SessionUser user, boolean detail);

    TblOwner find(String owner);

    TblOwner findByAppId(String appId);

    TblOwner findByPhone(String phone);
}
