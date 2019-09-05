package com.soft.ware.modular.system.dao;

import com.soft.ware.modular.system.model.TbMember;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 * @author paulo123
 * @since 2019-09-02
 */
public interface TbMemberMapper extends BaseMapper<TbMember> {

    List<TbMember> memberList(Map map);

}
