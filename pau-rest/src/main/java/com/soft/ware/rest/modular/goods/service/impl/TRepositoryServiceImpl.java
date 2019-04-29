package com.soft.ware.rest.modular.goods.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.goods.dao.TRepositoryMapper;
import com.soft.ware.rest.modular.goods.model.TRepository;
import com.soft.ware.rest.modular.goods.service.ITRepositoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品库 服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@Service
public class TRepositoryServiceImpl extends ServiceImpl<TRepositoryMapper, TRepository> implements ITRepositoryService {

    @Resource
    private TRepositoryMapper mapper;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> params) {
        return mapper.findMaps(params);
    }

    @Override
    public Kv<String, Object> findMap(Map<String, Object> params) {
        List<Map<String, Object>> maps = findMaps(params);
        return maps.size() == 1 ? null : Kv.toKv(maps.get(0));
    }

    @Override
    public TRepository findOne(Map<String,Object> params) throws Exception {
        return BeanMapUtils.toObject(params, TRepository.class);
    }

    @Override
    public List<TRepository> findList(Kv<String,Object> params) throws Exception {
        List<Map<String, Object>> maps = findMaps(params);
        return BeanMapUtils.toObject(maps, TRepository.class);
    }

    @Override
    public TRepository selectRepositoryByGoodsCode(String code) {
        return mapper.selectRepositoryByGoodsCode(code);
    }

}
