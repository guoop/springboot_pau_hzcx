package com.soft.ware.rest.modular.handover.service;


import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.HandOver;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverParam;

public interface IHandOverService extends IService<HandOver>{

    HandOver over(TblOwnerStaff user, HandoverParam param);
}
