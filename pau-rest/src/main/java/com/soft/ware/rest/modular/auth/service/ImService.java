package com.soft.ware.rest.modular.auth.service;

import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

public interface ImService {

    /**
     * 发送新订单通知
     * @param user
     */
    void sendNewOrderNotify(SessionUser user);
}
