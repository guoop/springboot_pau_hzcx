package com.soft.ware.modular.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.core.datascope.DataScope;
import com.soft.ware.modular.system.dao.UserMapper;
import com.soft.ware.modular.system.model.User;
import com.soft.ware.modular.system.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public int setStatus(Integer userId, int status) {
        return this.baseMapper.setStatus(userId, status);
    }

    @Override
    public int changePwd(Integer userId, String pwd) {
        return this.baseMapper.changePwd(userId, pwd);
    }

    @Override
    public List<Map<String, Object>> selectUsers(DataScope dataScope, String name, String beginTime, String endTime, Integer deptid, int memberId,String creater) {
        return this.baseMapper.selectUsers(dataScope,name,beginTime,endTime,deptid,memberId,creater);
    }

    @Override
    public int setRoles(Integer userId, String roleIds) {
        return this.baseMapper.setRoles(userId, roleIds);
    }

    @Override
    public User getByAccount(String account) {
        return this.baseMapper.getByAccount(account);
    }

    @Override
    public List<User> getUserListByMemberId(Integer memberId) {
        return this.baseMapper.getUserListByMemberId(memberId);
    }
}
