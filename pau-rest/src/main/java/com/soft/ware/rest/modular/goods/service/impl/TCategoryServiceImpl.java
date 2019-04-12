package com.soft.ware.rest.modular.goods.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.modular.auth.controller.dto.CategorySortParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.goods.dao.TCategoryMapper;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return mapper.selectList(new EntityWrapper<>(new TCategory().setOwnerId(owner.getOwnerId()).setIsDelete(TCategory.is_delete_0)).orderBy("sort", true));
    }

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public List<TCategory> selectParentCategoryList(Map<String, Object> map) {
        List<TCategory> list = mapper.selectParentCategoryList(map);
        List<TCategory> resultList = new ArrayList<>();
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                TCategory tCategory = list.get(i);
                map.put("pid",tCategory.getId());
                List<TCategory> childList = mapper.selectChildrenCategoryList(map);
                if(childList.size() > 0){
                    tCategory.setChildCategory(childList);
                }
                resultList.add(tCategory);
            }
        }
        return resultList;
    }



    @Override
    public List<TCategory> selectChildrenCategoryList(Map<String, Object> map) {
        return mapper.selectChildrenCategoryList(map);
    }

    @Override
    public boolean updateOrSort(CategorySortParam categorySortParam) {
        return mapper.updateOrSort(categorySortParam);
    }

}
