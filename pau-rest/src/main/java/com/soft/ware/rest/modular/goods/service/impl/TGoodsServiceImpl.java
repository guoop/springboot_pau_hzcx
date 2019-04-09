package com.soft.ware.rest.modular.goods.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.goods.dao.TGoodsMapper;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息表 服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@Service
public class TGoodsServiceImpl extends ServiceImpl<TGoodsMapper, TGoods> implements ITGoodsService {

    @Resource
    private TGoodsMapper mapper;

    @Override
    public List<Map> findPage(SessionUser user, Page page, GoodsPageParam param) {
        Long count = mapper.findPageCount(user.getOwnerId(), param);
        page.setTotal(count);
        return mapper.findPage(user.getOwnerId(), param, page);
    }

    @Override
    public List<Map<String,Object>> find(Map<String, Object> map) {
        return mapper.find(map);
    }

}
