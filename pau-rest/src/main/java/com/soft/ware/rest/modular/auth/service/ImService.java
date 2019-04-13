package com.soft.ware.rest.modular.auth.service;

import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.order.model.TOrder;

public interface ImService {

    /**
     * 发送新订单通知
     * @param user
     */
    void sendNewOrderNotify(SessionUser user, TOrder order);

    /**
     * 添加店员到极光im群,并且添加到群组
     * @param user
     * @param ss
     */
    void syncUsers(SessionUser user, TblOwner owner, TblOwnerStaff... ss) throws Exception;

    /**
     * 发送商品添加通知
     * @param user
     * @param goods
     */
    void sendAddGoodsNotify(SessionUser user, TGoods goods);
}
