package com.soft.ware.rest.modular.auth.service;

import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.SessionOwnerUser;
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
    void syncUsers(SessionOwnerUser user, TblOwner owner, TblOwnerStaff... ss) throws Exception;

    /**
     * 发送商品添加通知
     * @param user
     * @param goods
     */
    void sendAddGoodsNotify(SessionOwnerUser user, TblGoods goods);
}
