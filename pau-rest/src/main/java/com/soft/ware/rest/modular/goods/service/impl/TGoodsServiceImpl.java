package com.soft.ware.rest.modular.goods.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.goods.dao.TGoodsMapper;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private ITCategoryService categoryService;

    @Override
    public List<Map<String, Object>> findPage(SessionUser user, Page page, GoodsPageParam param) {
        TGoods g = new TGoods().setOwnerId(user.getOwnerId());
        if (ToolUtil.isNotEmpty(param.getCategory())) {
            g.setCategoryId(param.getCategory());
        }
        EntityWrapper<TGoods> wrapper = new EntityWrapper<>(g);
        if (param.getBeginTime() != null) {
            wrapper.gt("update_time", param.getBeginTime());
        }
        if (ToolUtil.isNotEmpty(param.getName())) {
            wrapper.like("name", param.getName());
        }
        int count = mapper.selectCount(wrapper);
        page.setTotal(count);
        return mapper.findMaps(Kv.obj("page", page).set("ownerId", user.getOwnerId()).set("like_name", param.getName()).set("categoryId", param.getCategory()).set("lastUpdateTime", param.getBeginTime()));
    }

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
    public List<Map<String,Object>> selectTGoodsListByMap(SessionUser user,Map<String, Object> param,Page page) throws Exception {
        String ownerId = user.getOwnerId();
        Object category = param.get("categoryId");
        param.remove("categoryId");
        Object status = param.get("status");
        //传过来的categoryId，是父分类或者子分类
        TCategory c = categoryService.selectById(category.toString());
        List<Object> ids = categoryService.findChild(user, c, true).stream().map(s -> (String)s.get("id")).collect(Collectors.toList());
        TGoods g = new TGoods().setOwnerId(ownerId).setIsDelete(TGoods.is_delete_0);
        if (ToolUtil.isNotEmpty(status)) {
            g.setCategoryId(category.toString());
        }
        if (ToolUtil.isNotEmpty(status)) {
            g.setStatus(Integer.valueOf(status.toString()));
        }
        Integer count = mapper.selectCount(new EntityWrapper<>(g).in("category_id", ids));
        page.setTotal(count);
        param.put("page", page);
        param.put("ownerId", ownerId);
        param.put("categoryIds", "'"+StringUtils.join(ids,"','")+"'");
        return mapper.selectTGoodsListByMap(param,page);
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
