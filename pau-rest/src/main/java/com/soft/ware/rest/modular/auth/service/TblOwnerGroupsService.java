package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblOwnerGroups;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

import java.util.List;

public interface TblOwnerGroupsService extends IService<TblOwnerGroups> {

    /**
     * 根据用户和 群组类型查询im群组信息
     * @param user
     * @param type
     * @return
     */
    List<TblOwnerGroups> find(SessionUser user, int type);
}
