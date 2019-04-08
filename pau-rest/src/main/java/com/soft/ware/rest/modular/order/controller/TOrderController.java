package com.soft.ware.rest.modular.order.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionOwnerUser;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/owner/v1")
public class TOrderController extends BaseController {

    @Autowired
    private ITOrderService itOrderService;

    /**
     * 通过订单状态查询订单列表
     * @param param (订单状态）,page（页码）
     * @return
     * owner/v2/order 修改 owner/v1/orders/list
     */
    @RequestMapping("/orders/list")
    public Tip getOrderList(SessionOwnerUser user, @RequestParam Map<String,Object> param) {
       param.put("owner_id",user.getOwner());

       List<TOrder>  list = itOrderService.selectByMap(param);
       if(list.size() > 0){
           return new SuccessTip(list);
       }

        return new ErrorTip();
    }

}

