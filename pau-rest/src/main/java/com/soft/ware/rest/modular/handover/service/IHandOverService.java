package com.soft.ware.rest.modular.handover.service;


import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.handover.model.HandOver;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

import java.util.List;
import java.util.Map;

public interface IHandOverService extends IService<HandOver>{

    HandOver over(SessionUser user, HandoverParam param);
    
    List<HandOver> getHandOver( Map<String,Object> map);
    
    
}
