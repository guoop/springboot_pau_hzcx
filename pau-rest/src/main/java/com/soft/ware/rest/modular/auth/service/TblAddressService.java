package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblAddress;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

import java.util.List;

public interface TblAddressService extends IService<TblAddress> {

    /**
     * 查询用户所有地址
     * @param user
     * @return
     */
    List<TblAddress> findAll(SessionUser user);

    /**
     * 根据id查询地址详情
     * @param user
     * @param id
     * @return
     */
    TblAddress findById(SessionUser user, long id);

    /**
     * 添加地址
     * @param user
     * @param address
     * @return
     */
    boolean addAddress(SessionUser user,TblAddress address);

    /**
     * 修改地址
     * @param user
     * @param address
     * @return
     */
    boolean updateAddress(SessionUser user,TblAddress address);

    /**
     * 删除地址（标记删除）
     * @param user
     * @param address
     * @return
     */
    boolean deleteById(SessionUser user, TblAddress address);
}
