package com.soft.ware.rest.modular.order.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.order.dao.TOrderMapper;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 线上订单信息 服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements ITOrderService {

    @Resource
    private TOrderMapper orderMapper;


    @Override
    public List<TOrder> selectOrderListByMap(Map<String, Object> param) {
        return orderMapper.selectOrderListByMap(param);
    }

    @Override
    public List<Map> findPage(SessionUser user, Page page, OrderPageParam param, int... source) {
        Long count = orderMapper.findListCount(user, param,source);
        page.setTotal(count);
        return orderMapper.findList(user, page, param,source);
    }

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return orderMapper.findMaps(map);
    }

    @Override
    public Map<String, Object> findMap(Map<String, Object> map) {
        List<Map<String, Object>> maps = findMaps(map);
        return maps.isEmpty() ? null : maps.get(0);
    }

    @Override
    public List<Map<String, Object>> selectOrdersListByMap(Map<String, Object> map) {

        List<Map<String,Object>> listMap = orderMapper.selectOrdersListByMap(map);
        List<Map<String,Object>> resultList = new ArrayList<>();
        if(listMap.size() > 0){
            for (int i = 0; i < listMap.size(); i++) {
                Map<String,Object>  resultMap = listMap.get(i);
                if(resultMap.get("source").toString().equals("2")){
                   resultMap.remove("address");
                   resultMap.remove("addressId");
                }
                resultList.add(resultMap);
            }
        }
        return resultList;
    }






/*
    @Override
    public WxMaTemplateMessage buildOrderTemplateMessage(String templateID, String fromID, TOrder order){
        StringBuilder goodsName = new StringBuilder();
        String[] gs = order.getGoods().split(",");
        int i = 0;
        do {
            String[] s = gs[i].split("__");
            goodsName.append(",");
            goodsName.append(s[2]);
            if (i == 2) {
                //最多拼接三个
                break;
            }
            i++;
        } while (i < gs.length && i < 3);
        if (gs.length > 3) {
            goodsName.append("...共").append(gs.length).append("种商品");
        }
        ArrayList<WxMaTemplateData> data = Lists.newArrayList();
        data.add(new WxMaTemplateData("keyword1", order.getOrderNo()));// 订单编号
        data.add(new WxMaTemplateData("keyword2", DateUtil.format(order.getCreateTime(), WXContants.date_format)));// 下单时间
        data.add(new WxMaTemplateData("keyword3", order.getPayMoney().setScale(2, WXContants.big_decimal_sale) + ""));// 订单金额
        data.add(new WxMaTemplateData("keyword4", goodsName.toString()));// 商品名称
        data.add(new WxMaTemplateData("keyword5", order.getConsigneeName() + ' ' + order.getConsigneeMobile() + ' ' + order.getConsigneeAddress()));// 收货地址
        WxMaTemplateMessage msg = WxMaTemplateMessage.builder()
                .templateId(templateID)
                .formId(fromID)
                .toUser(order.getCreatedBy())
                .page("pages/mine/index")
                .data(data)
                .build();
        return msg;
    }
*/



}
