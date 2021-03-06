package com.soft.ware.rest.modular.owner_staff.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.controller.dto.StaffEditParam;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;

import java.util.List;
import java.util.Map;

public interface ITOwnerStaffService extends IService<TOwnerStaff> {

    List<Map<String,Object>> findMaps(Map<String,Object> map);

    Kv<String,Object> findMap(Map<String,Object> map);

    TOwnerStaff findOne(Map<String, Object> map) throws Exception;

    List<TOwnerStaff> findList(Map<String, Object> params) throws Exception;

    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    @Deprecated//没有过滤删除状态可能会重复
    TOwnerStaff findByPhone(String phone);

    /**
     * 根据手机号查询用户信息，已经过滤删除状态
     * @param phone
     * @return
     */
    TOwnerStaff findByLoginName(String phone);

    List<TOwnerStaff> selectStaffByOwnerId(String ownerId);

    boolean addOrUpdate(SessionUser sessionUser, StaffEditParam param);

    boolean delStaff(Map<String,Object> map,SessionUser sessionUser) throws Exception;


}
