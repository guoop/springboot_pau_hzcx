package com.soft.ware.rest.modular.handover.service;


import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.HandOver;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;

public interface IHandOverService extends IService<HandOver>{

    HandOver over(SessionUser user, HandoverParam param);
    
    Object getHandOver(HandoverParam param,SessionUser user,Page page);
    
    
}
