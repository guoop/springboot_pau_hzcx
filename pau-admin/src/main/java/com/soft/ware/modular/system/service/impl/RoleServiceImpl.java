package com.soft.ware.modular.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.core.node.ZTreeNode;
import com.soft.ware.core.util.Convert;
import com.soft.ware.modular.system.dao.RelationMapper;
import com.soft.ware.modular.system.dao.RoleMapper;
import com.soft.ware.modular.system.model.Relation;
import com.soft.ware.modular.system.model.Role;
import com.soft.ware.modular.system.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RelationMapper relationMapper;

    @Override
    @Transactional(readOnly = false)
    public void setAuthority(Integer roleId, String ids) {

        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);

        // 添加新的权限
        for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", ids))) {
            Relation relation = new Relation();
            relation.setRoleid(roleId);
            relation.setMenuid(id);
            this.relationMapper.insert(relation);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void delRoleById(Integer roleId) {
        //删除角色
        this.roleMapper.deleteById(roleId);

        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);
    }

    @Override
    public List<Map<String, Object>> selectRoles(String condition,Integer memberId) {
        return this.baseMapper.selectRoles(condition,memberId);
    }

    @Override
    public int deleteRolesById(Integer roleId) {
        return this.baseMapper.deleteRolesById(roleId);
    }

    @Override
    public List<ZTreeNode> roleTreeList(Integer memberId) {
        return this.baseMapper.roleTreeList(memberId);
    }

    @Override
    public List<ZTreeNode> roleTreeListByRoleId(Map map) {
        return this.baseMapper.roleTreeListByRoleId(map);
    }

}
