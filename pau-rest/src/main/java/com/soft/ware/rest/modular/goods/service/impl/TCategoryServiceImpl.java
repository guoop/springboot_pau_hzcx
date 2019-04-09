package com.soft.ware.rest.modular.goods.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.goods.dao.TCategoryMapper;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@Service
public class TCategoryServiceImpl extends ServiceImpl<TCategoryMapper, TCategory> implements ITCategoryService {


    @Resource
    private TCategoryMapper mapper;

    @Override
    public List<TCategory> findAllCategory(SessionUser owner) {
        return mapper.selectList(new EntityWrapper<>(new TCategory().setOwnerId(owner.getId()).setIsDetele(TCategory.is_delete_0)).orderBy("sort", true));
    }

}
