package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.AddOrderParam;

public interface TblOrderService extends IService<TblOrder> {

    TblOrder createOrder(TblOwnerStaff user, AddOrderParam param);
}
