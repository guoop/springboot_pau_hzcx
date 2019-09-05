package com.soft.ware.modular.system.service.impl;

import com.soft.ware.modular.system.model.TbMember;
import com.soft.ware.modular.system.dao.TbMemberMapper;
import com.soft.ware.modular.system.service.ITbMemberService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-09-02
 */
@Service
public class TbMemberServiceImpl extends ServiceImpl<TbMemberMapper, TbMember> implements ITbMemberService {

    @Autowired(required = false)
    private TbMemberMapper tbMemberMapper;
    @Override
    public List<TbMember> memberList(Map map) {
        return tbMemberMapper.memberList(map);
    }
}
