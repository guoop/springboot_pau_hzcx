package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.Customer;

public interface TblOwnerService extends IService<TblOwner> {

    TblOwner find(TblOwnerStaff user);

    TblOwner find(Customer customer);

    TblOwner find(Customer customer, boolean detail);
}
