package com.soft.ware.rest.modular.owner_staff.service;
import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;

public interface TOwnerStaffService extends IService<TOwnerStaff> {

    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    TOwnerStaff findByPhone(String phone);

    TOwnerStaff selectStaffByOwnerId(String ownerId);


}
