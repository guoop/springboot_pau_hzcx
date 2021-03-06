package com.soft.ware.rest.modular.goods.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.modular.auth.controller.dto.CategorySortParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.goods.dao.TCategoryMapper;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.ITOwnerStaffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private ITOwnerStaffService staffService;

    @Override
    public List<TCategory> findAllCategory(SessionUser owner) {
        return mapper.selectList(new EntityWrapper<>(new TCategory().setOwnerId(owner.getOwnerId()).setIsDelete(TCategory.is_delete_0)).orderBy("sort", true));
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
    public List<Map<String,Object>> findChild(SessionUser user, TCategory c, boolean returnDefault) throws Exception {
        if (c == null) {
            return Lists.newArrayList();
        }
        Map<String, Object> map = BeanMapUtils.toMap(c, false);
        if (ToolUtil.isEmpty(c.getPid())) {
            List<Map<String, Object>> maps = findMaps(Kv.obj("pid", c.getId()).set("ownerId", user.getOwnerId()));
            maps.add(map);
            return maps;
        } else {
            if (returnDefault) {
                ArrayList<Map<String, Object>> maps = new ArrayList<>();
                maps.add(map);
                return maps;
            }
            return new ArrayList<>();
        }
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

    @Override
    public List<TCategory> selectCategoryByIds(String[] ids) {
        return mapper.selectList(new EntityWrapper<TCategory>().in("id", ids));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean saveOrUpdate(SessionUser user, TCategory category) {
        boolean result;
        if (ToolUtil.isEmpty(category.getId())) {
            category.setId(IdGenerator.getId());
            category.setOwnerId(user.getOwnerId());
            category.setSort(0);
            category.setIsSystem(TCategory.is_system_0);
            result = insert(category);
        } else {
            result = updateById(category);
        }
        TOwnerStaff staff = staffService.findByLoginName(user.getPhone());
        if (!TOwnerStaff.shopkeeperFlag.equals(staff.getFunctionList())) {
            //非店主需要同步分类权限
            String s = staff.getCategoryList();
            if (!s.contains(category.getId())) {
                if (!s.endsWith(",")) {
                    s += ",";
                }
                staff.setCategoryList(s + category.getId() + ",");
                staffService.updateById(staff);
            }
        }
        List<String> ids = Lists.newArrayList();
        if (result) {
            List<TCategory> list = category.getChildCategory();
            TCategory c;
            int i = 0;
            do {
                c = list.get(i);
                if (ToolUtil.isEmpty(c.getId())) {
                    c.setId(IdGenerator.getId());
                    c.setSort(i);
                    c.setPid(category.getId());
                    c.setOwnerId(user.getOwnerId());
                    result = insert(c);
                    ids.add(c.getId());
                } else {
                    c.setSort(i);
                    result = updateById(c);
                    ids.add(c.getId());
                }
                i++;
            } while (result && i < list.size());
        }
        //删除未提交的子分类
        delete(new EntityWrapper<>(new TCategory().setOwnerId(user.getOwnerId()).setPid(category.getId())).notIn("id", ids));
        if (!result) {
            throw new PauException(BizExceptionEnum.ERROR);
        }
        return true;
    }
}
