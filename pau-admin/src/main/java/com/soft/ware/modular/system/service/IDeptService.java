package com.soft.ware.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.core.node.ZTreeNode;
import com.soft.ware.modular.system.model.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 部门服务
 */
public interface IDeptService extends IService<Dept> {

    /**
     * 删除部门
     */
    void deleteDept(Integer deptId);

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree(Integer memberId);

    /**
     * 获取所有部门列表
     */
    List<Map<String, Object>> list(@Param("condition") String condition,@Param("memberId") Integer memberId);
}
