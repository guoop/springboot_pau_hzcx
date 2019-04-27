package com.soft.ware.rest.modular.owner_config.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.owner_config.dao.TOwnerConfigMapper;
import com.soft.ware.rest.modular.owner_config.model.TOwnerConfig;
import com.soft.ware.rest.modular.owner_config.service.ITOwnerConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TOwnerConfigServiceImpl extends BaseService<TOwnerConfigMapper,TOwnerConfig> implements ITOwnerConfigService {

    @Resource
    private TOwnerConfigMapper mapper;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Kv<String, Object> findMap(Map<String, Object> map) {
    List<Map<String, Object>> maps = findMaps(map);
        return maps.size() == 1 ? Kv.toKv(maps.get(0)) : null;
    }

    @Override
    public boolean updateOwnerConfig(Map<String,Object> map) {
        TOwnerConfig con = new TOwnerConfig();
        con.setOwnerId(map.get("ownerId").toString());
        con = mapper.selectOne(con);
        TOwnerConfig conData = new TOwnerConfig();
        conData.setId(con.getId());
        if(ToolUtil.isNotEmpty(map.get("orderPhone"))){
            conData.setOrderPhone(map.get("orderPhone").toString());
        }
        if(ToolUtil.isNotEmpty(map.get("isDelivery"))){
            conData.setIsDelivery(Integer.valueOf(map.get("isDelivery").toString()));
        }
        if(ToolUtil.isNotEmpty(map.get("defaultDesc"))){
            conData.setDefaultDesc(map.get("defaultDesc").toString());
        }
        if(ToolUtil.isNotEmpty(map.get("defaultRefundReason"))){
            conData.setDefaultRefundReason(map.get("defaultRefundReason").toString());
        }
        if(ToolUtil.isNotEmpty(map.get("deliveryDistance"))){
            conData.setDeliveryDistance(Integer.valueOf(map.get("deliveryDistance").toString()));
        }
        if(ToolUtil.isNotEmpty(map.get("deliveryGreatMoney"))){
            conData.setDeliveryGreatMoney(BigDecimal.valueOf(Double.valueOf(map.get("deliveryGreatMoney").toString())));
        }
        if(ToolUtil.isNotEmpty(map.get("deliveryLessMoney"))){
            conData.setDeliveryLessMoney(BigDecimal.valueOf(Double.valueOf(map.get("deliveryLessMoney").toString())));
        }
        if(ToolUtil.isNotEmpty(map.get("isReachPay"))){
            conData.setIsReachPay(Integer.valueOf(map.get("isReachPay").toString()));
        }
        if(ToolUtil.isNotEmpty(map.get("isSelfPickup"))){
            conData.setIsSelfPickup(Integer.valueOf(map.get("isSelfPickup").toString()));
        }
        if(ToolUtil.isNotEmpty(map.get("deliveryMoney"))){
            conData.setDeliveryMoney(BigDecimal.valueOf(Double.valueOf(map.get("deliveryMoney").toString())));
        }
        if(mapper.updateById(conData) > 0){
            return true;
        }
        return false;
    }


}