package com.soft.ware.rest.modular.auth.service;

import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

public interface ImService {

    /**
     * 发送新订单通知
     * @param user
     */
    void sendNewOrderNotify(SessionUser user, TblOrder order);

    /**
     * 添加店员到极光im群,并且添加到群组
     * @param user
     * @param ss
     */
    void addUsers(SessionUser user, TblOwnerStaff... ss);
}
