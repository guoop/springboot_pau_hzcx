package com.soft.ware.rest.modular.owner_temp.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.owner_temp.model.TOwnerTemp;

import java.util.List;
import java.util.Map;

public interface ITOwnerTempService extends IService<TOwnerTemp> {

    List<Map<String,Object>> findMaps(Map<String,Object> map);

    Kv<String,Object> findMap(Map<String,Object> map);

    /**
     * 获取模板id
     * @param user 登录用户
     * @param key  pay,cancel,deliver,confirm,refund,diff
     * @return
     * @throws Exception
     */
    String getTplId(SessionUser user, String key) throws Exception;
}
