package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblOwnerTemp;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

public interface TblOwnerTempService extends IService<TblOwnerTemp> {


    /**
     * 根据用户信息和模板名获取模板id
     * @param user
     * @param key
     * @return
     */
    String getTplId(SessionUser user,String key) throws Exception;


}
