package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;

public interface TblOwnerService extends IService<TblOwner> {

    TblOwner findByStaff(TblOwnerStaff user);

}
