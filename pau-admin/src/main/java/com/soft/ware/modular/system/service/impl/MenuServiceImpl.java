package com.soft.ware.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.core.node.MenuNode;
import com.soft.ware.core.node.ZTreeNode;
import com.soft.ware.modular.system.dao.MenuMapper;
import com.soft.ware.modular.system.model.Menu;
import com.soft.ware.modular.system.service.IMenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * 菜单服务
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;
    
    @Override
    public void delMenu(Long menuId) {

        //删除菜单
        this.menuMapper.deleteById(menuId);

        //删除关联的relation
        this.menuMapper.deleteRelationByMenu(menuId);
    }
    @Override
    public void delMenuContainSubMenus(Long menuId) {

        Menu menu = menuMapper.selectById(menuId);

        //删除当前菜单
        delMenu(menuId);

        //删除所有子菜单
        Wrapper<Menu> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pcodes", "%[" + menu.getCode() + "]%");
        List<Menu> menus = menuMapper.selectList(wrapper);
        for (Menu temp : menus) {
            delMenu(temp.getId());
        }
    }
    @Override
    public List<Map<String, Object>> selectMenus(String condition, String level) {
        return this.baseMapper.selectMenus(condition, level);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Integer roleId) {
        return this.baseMapper.getMenuIdsByRoleId(roleId);
    }

    @Override
    public List<ZTreeNode> menuTreeList() {
        return this.baseMapper.menuTreeList();
    }

    @Override
    public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds) {
        return this.baseMapper.menuTreeListByMenuIds(menuIds);
    }

    @Override
    public int deleteRelationByMenu(Long menuId) {
        return this.baseMapper.deleteRelationByMenu(menuId);
    }

    @Override
    public List<String> getResUrlsByRoleId(Integer roleId) {
        return this.baseMapper.getResUrlsByRoleId(roleId);
    }

    @Override
    public List<MenuNode> getMenusByRoleIds(List<Integer> roleIds,Integer memberId) {
        return this.baseMapper.getMenusByRoleIds(roleIds,memberId);
    }

    @Override
    public List<Map<String, Object>> selectListByRoleId(List<Integer> roleIds) {
        return this.baseMapper.selectListByRoleId(roleIds);
    }
}
