package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.AddOrderParam;
import com.soft.ware.rest.modular.auth.controller.dto.Customer;
import com.soft.ware.rest.modular.auth.controller.dto.CustomerOrderParam;
import com.soft.ware.rest.modular.auth.util.Page;

import java.util.List;
import java.util.Map;

public interface TblOrderService extends IService<TblOrder> {

    /**
     * 收银机添加订单
     * @param user
     * @param param
     * @return
     */
    TblOrder createOrder(TblOwnerStaff user, AddOrderParam param);

    /**
     * 收银机订单列表
     * @param user
     * @param page
     * @param status
     * @return
     */
    List<Map> findPage(TblOwnerStaff user, Page page, String status);

    /**
     * 根据订单号查询订单详情
     * @param no
     * @return
     */
    Map<String, Object> findByNo(TblOwnerStaff user, String no);

    /**
     * 更新订单状态
     * @param user
     * @param orderNO
     * @param status
     * @return
     */
    boolean updateStatus(TblOwnerStaff user, String orderNO, String status);

    /**
     * 用户订单
     * @param customer
     * @return
     */
    List<Map> findPage(Customer customer, CustomerOrderParam param);
}
