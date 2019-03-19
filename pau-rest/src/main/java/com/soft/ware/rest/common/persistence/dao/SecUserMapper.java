package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.SecUser;
import org.apache.ibatis.annotations.Param;

public interface SecUserMapper extends BaseMapper<SecUser> {

    /**
     * 查询用户根据用户名字
     * @param username
     * @return
     */
    SecUser findByUsername(@Param(value = "username")String username);
}
