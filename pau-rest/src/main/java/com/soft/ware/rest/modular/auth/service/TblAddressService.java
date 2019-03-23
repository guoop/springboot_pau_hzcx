package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblAddress;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

import java.util.List;

public interface TblAddressService extends IService<TblAddress> {

    List<TblAddress> findAll(SessionUser user);

    TblAddress findById(SessionUser user, long id);

    boolean addAddress(SessionUser user,TblAddress address);

    boolean updateAddress(SessionUser user,TblAddress address);
}
