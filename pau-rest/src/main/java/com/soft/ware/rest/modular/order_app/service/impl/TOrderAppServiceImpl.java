package com.soft.ware.rest.modular.order_app.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.modular.auth.controller.dto.AddOrderParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.model.TOrderChild;
import com.soft.ware.rest.modular.order.service.ITOrderChildService;
import com.soft.ware.rest.modular.order_app.dao.TOrderAppMapper;
import com.soft.ware.rest.modular.order_app.model.TOrderApp;
import com.soft.ware.rest.modular.order_app.service.ITOrderAppService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TOrderAppServiceImpl extends BaseService<TOrderAppMapper,TOrderApp> implements  ITOrderAppService {

    @Resource
    private TOrderAppMapper mapper;

    @Autowired
    private ITOrderChildService orderChildService;

    @Autowired
    private ITGoodsService itGoodsService;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Map<String, Object> findMap(Map<String, Object> map) {
        List<Map<String, Object>> maps = findMaps(map);
        return maps.isEmpty() ? null : maps.get(0);
    }

    @Override
    public TOrderApp findOne(Map<String,Object> map) throws Exception {
        return BeanMapUtils.toObject(map, TOrderApp.class);
    }

    @Override
    public List<Map<String, Object>> findMapPage(SessionUser user, Page page, OrderPageParam param, Integer... sources) {
        Kv<String, Object> map = Kv.obj("creater", user.getOpenId())
                .set("page", page)
                .set("status", param.getStatus())
                .set("isDelete", TOrder.is_delete_0)
                .set("sources", "'" + StringUtils.join(sources, "','") + "'")
                .set("orderBy", "a.create_time desc");
        long count = findPageCount(user, param,sources);
        page.setTotal(count);
        return findMaps(map);
    }

    @Override
    public TOrderApp addOrder(SessionUser user, AddOrderParam param) {
        TOrderApp order = new TOrderApp();
        Date date = new Date();
        order.setId(IdGenerator.getId());
        order.setNo(param.getNo());
        order.setSource(param.getSource());
        order.setMoneyChannel(param.getMoney_channel());
        order.setMoney(param.getMoney());
        order.setMoneyPay(param.getMoney_shishou());
        order.setChannelPay(param.getChannel_pay());
        order.setPayTime(new Date(param.getPay_at()));
        order.setSettlementer(param.getSettlement_by());
        order.setMoneyChange(param.getMoney_zhaol());
        order.setOwnerId(user.getOwnerId());
        order.setRemark(null);
        order.setCreateTime(date);
        order.setMoneyRealIncome(param.getMoney_shishou().add(param.getMoney_zhaol()));//失手
        order.setStatus(param.getStatus());
        boolean insert = insert(order);
        List<TOrderChild> list = param.getGoodsList();
        for (TOrderChild child : list) {
            child.setId(IdGenerator.getId());
            child.setCreateTime(date);
            child.setOrderId(order.getId());
        }
        if (insert) {
            for (TOrderChild child : list) {
                insert = orderChildService.insert(child);
                if (!insert) {
                    throw new PauException(BizExceptionEnum.ORDER_CREATE_FAIL);
                }
            }
        } else {
            throw new PauException(BizExceptionEnum.ORDER_CREATE_FAIL);
        }
        return order;
    }

    @Override
    public long findPageCount(SessionUser user, OrderPageParam param, Integer... sources) {
        TOrderApp p = new TOrderApp()
                .setOwnerId(user.getOwnerId());
        return selectCount(new EntityWrapper<>(p).in("status", param.getStatus()).in("source",sources));
    }


    @Override
    public List<TOrderApp> getAppOrderList(Map<String, Object> map,Page page) {
        List<TOrderApp> listOrderApp = mapper.getAppOrderList(map,page);
        listOrderApp.forEach(o->o.setListGoods(orderChildService.findMaps(Kv.by("orderId", o.getId()))));
        return listOrderApp;
    }
}