package com.soft.ware.rest.modular.address.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.address.model.TAddress;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

import java.util.List;
import java.util.Map;

public interface ITAddressService extends IService<TAddress> {

    List<Map<String,Object>> findMaps(Map<String, Object> map);

    Map<String,Object> findMap(Map<String, Object> map);

    boolean addAddress(SessionUser user, TAddress address);

    boolean updateAddress(SessionUser user, TAddress address);

    boolean deleteById(SessionUser user, TAddress address);

    TAddress findById(SessionUser user, String addressId) throws Exception;
}
