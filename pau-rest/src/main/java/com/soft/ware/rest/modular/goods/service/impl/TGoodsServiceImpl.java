package com.soft.ware.rest.modular.goods.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.goods.dao.TGoodsMapper;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.goods.model.TRepository;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import com.soft.ware.rest.modular.goods.service.ITRepositoryService;
import com.soft.ware.rest.modular.goods_storage.model.TGoodsStorage;
import com.soft.ware.rest.modular.goods_storage.service.ITGoodsStorageService;
import com.soft.ware.rest.modular.im.service.ImService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
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

    @Autowired
    private ITRepositoryService repositoryService;

    @Autowired
    private ITGoodsStorageService storageService;

    @Autowired
    private ImService imService;


    @Override
    public List<Map<String, Object>> findPage(SessionUser user, Page page, GoodsPageParam param) throws Exception {
        TGoods g = new TGoods().setOwnerId(user.getOwnerId());
        if (ToolUtil.isNotEmpty(param.getCategory())) {
            g.setCategoryId(param.getCategory());
        }
        Kv<String, Object> params = Kv.obj("ownerId", user.getOwnerId())
                .set("like_name", param.getName())
                .set("lastUpdateTime", param.getBeginTime());

        String c = param.getCategory();
        if ("-3".equals(c)) {
            params.set("promotion", TGoods.is_promotion_1);
        } else {
            TCategory category = categoryService.selectById(c);
            List<Map<String, Object>> child = categoryService.findChild(user, category, true);
            List<String> ids = child.stream().map(m -> m.get("id").toString()).filter(s -> !s.isEmpty()).collect(Collectors.toList());
            if (!ids.isEmpty()) {
                params.set("categoryIds", "'" + StringUtils.join(ids, "','") + "'");
            }
        }
        long count = mapper.findMapsCount(params);
        page.setTotal(count);
        params.put("page", page);
        return mapper.findMaps(params);
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
        Kv<String,Object> params = Kv.toKv(param);
        String ownerId = user.getOwnerId();
        Object category = params.get("categoryId");
        params.remove("categoryId");
        //传过来的categoryId，是父分类或者子分类
        if (ToolUtil.isNotEmpty(category)) {
            if ("-3".equals(category)) {
                //查询促销商品
                params.put("promotionInProgress", 1);
            } else if("-2".equals(category)){
                params.put("status", TGoods.status_1);
            } else {
                TCategory c = categoryService.selectById(category.toString());
                List<String> ids = categoryService.findChild(user, c, true).stream().map(s -> (String)s.get("id")).collect(Collectors.toList());
                ids = ids.stream().filter(ToolUtil::isNotEmpty).collect(Collectors.toList());
                if (!ids.isEmpty()) {
                    params.put("categoryIds", "'"+StringUtils.join(ids,"','")+"'");
                }
            }
        }
        long count = mapper.selectTGoodsListByMapCount(params);
        page.setTotal(count);
        params.put("page", page);
        params.put("ownerId", ownerId);
        return mapper.selectTGoodsListByMap(params,page);
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
    public boolean top(String goodsId, Date date) {
        return mapper.top(goodsId, date);
    }

    @Override
    public Map<String,Object> findById(String id ) {
        Map<String,Object> mapdata = mapper.findById(id);
        return mapdata;
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean addByScan(SessionUser user, TGoods g, TGoodsStorage s) {
        Date date = new Date();
        List<Map<String,Object>> list = this.findByCode(user, g.getCode());
        if (!list.isEmpty()) {
            throw new PauException(BizExceptionEnum.GOODS_REPEAT);
        }
        g.setId(IdGenerator.getId());
        g.setCategoryId("-");//todo yancc
        g.setUnitId("");//todo yancc
        g.setCreateTime(date);
        g.setCreater(user.getId());
        g.setOwnerId(user.getOwnerId());
        g.setStatus(TGoods.status_1);
        g.setSort(1);
        g.setSource(TGoods.source_1);
        g.setIsDelete(TGoods.is_delete_0);
        if(this.insert(g)){
            s.setId(IdGenerator.getId());
            s.setOwnerId(user.getOwnerId());
            s.setGoodsId(g.getId());
            s.setBeforeBaseline(BigDecimal.ZERO);
            s.setCreateTime(date);
            s.setInTime(date);

            if (repositoryService.selectRepositoryByGoodsCode(g.getCode()) == null) {
                TRepository r = new TRepository();
                r.setId(IdGenerator.getId());
                r.setCode(g.getCode());
                r.setCreateTime(date);
                //r.setMeasurement(gcate.getMeasurementUnit()); //todo yancc
                r.setName(g.getName());
                r.setPics(g.getPics());
                r.setIsDelete(TRepository.is_delete_0);
                repositoryService.insert(r);
            }
            storageService.insert(s);
            imService.sendAddGoodsNotify(user, g);
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> findByCode(SessionUser user, String code) {
        List<Map<String, Object>> maps = mapper.findByCode(user.getOwnerId(), code);
        return maps;
    }

    @Override
    public boolean addByManual(SessionUser user, TGoods g, TGoodsStorage s) {
        Date date = new Date();
        List<Map<String,Object>> list = this.findByCode(user, g.getCode());
        if (!list.isEmpty()) {
            throw new PauException(BizExceptionEnum.GOODS_REPEAT);
        }
        g.setUnitId("");//todo yancc
        g.setCategoryId("-");
        g.setId(IdGenerator.getId());
        g.setCreateTime(date);
        g.setCreater(user.getId());
        g.setOwnerId(user.getOwnerId());
        g.setStatus(TGoods.status_1);
        g.setSort(1);
        g.setSource(TGoods.source_2);
        g.setIsDelete(TGoods.is_delete_0);
        if(this.insert(g)){
            s.setId(IdGenerator.getId());
            s.setOwnerId(user.getOwnerId());
            s.setGoodsId(g.getId());
            s.setBeforeBaseline(BigDecimal.ZERO);
            s.setCreateTime(date);
            s.setInTime(date);
            storageService.insert(s);
            imService.sendAddGoodsNotify(user, g);
            return true;
        }
        return  false;
    }

}
