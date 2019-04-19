package com.soft.ware.rest.modular.owner_config.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
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
    public Map<String, Object> findMap(Map<String, Object> map) {
    List<Map<String, Object>> maps = findMaps(map);
        return maps.isEmpty() ? null : maps.get(0);
    }

    @Override
    public boolean updateOwnerConfig(Map<String,Object> map) {
        TOwnerConfig con = new TOwnerConfig();
        con.setOwnerId(map.get("ownerId").toString());
        con = mapper.selectOne(con);
        con.setIsDelivery(Integer.valueOf(map.get("isDelivery").toString()));
        con.setOrderPhone(map.get("orderPhone").toString());
        if(ToolUtil.isNotEmpty(map.get("defaultDesc"))){
            con.setDefaultDesc(map.get("defaultDesc").toString());
        }
        con.setDefaultRefundReason(map.get("defaultRefundReason").toString());
        con.setDeliveryDistance(Integer.valueOf(map.get("isDelivery").toString()));
        con.setDeliveryGreatMoney(BigDecimal.valueOf(Double.valueOf(map.get("deliveryGreatMoney").toString())));
        con.setDeliveryLessMoney(BigDecimal.valueOf(Double.valueOf(map.get("deliveryLessMoney").toString())));
        con.setIsReachPay(Integer.valueOf(map.get("isReachPay").toString()));
        con.setIsSelfPickup(Integer.valueOf(map.get("isSelfPickup").toString()));
        if(mapper.updateById(con) > 0){
            return true;
        }
        return false;
    }


}