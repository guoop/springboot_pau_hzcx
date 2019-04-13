package com.soft.ware.rest.modular.goods.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.goods.controller.dto.GoodsParam;
import com.soft.ware.rest.modular.goods.dao.TGoodsMapper;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <p>
 * 商品信息表 服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@Service
public class TGoodsServiceImpl extends BaseService<TGoodsMapper, TGoods> implements ITGoodsService {

    @Resource
    private TGoodsMapper mapper;

    @Override
    public List<Map> findPage(SessionUser user, Page page, GoodsPageParam param) {
        Long count = mapper.findPageCount(user.getOwnerId(), param);
        page.setTotal(count);
        return mapper.findPage(user.getOwnerId(), param, page);
    }

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
    public List<Map<String,Object>> selectTGoodsListByMap(Map<String, Object> map) throws ParseException {
        List<Map<String,Object>> mapList = mapper.selectTGoodsListByMap(map);
        if(mapList.size() > 0){
            for (int i = 0; i < mapList.size(); i++) {
                if(ToolUtil.isNotEmpty(mapList.get(i).get("endTime"))){
                    String endTime = mapList.get(i).get("endTime").toString();
                    long endDate = Long.valueOf(DateUtil.dateToStamp(endTime));
                    long currentDate = Long.valueOf(DateUtil.timestampToDate()) ;
                    if(currentDate > endDate){
                        mapList.get(i).put("promotionInProgress",0);
                    }
                    mapList.get(i).put("promotionInProgress",1);
                }
            }
            return mapList;
        }
        return mapList;
    }

    @Override
    public boolean updateGoodsTopTimeOrStatus(Map<String, Object> map) {
        if(map.get("flag").toString().equals("yes")){
            map.put("top_time", new Date());
            if(mapper.updateGoodsTopTimeOrStatus(map)){
                return true;
            }
        }
        return false;
    }

    @Override
    public HashMap<String,Object> findById(String id ) {
        HashMap<String,Object> mapdata = mapper.findById(id);
        return mapdata;
    }


}
