package com.soft.ware.modular.system.service;

import com.soft.ware.modular.system.model.TbMember;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author paulo123
 * @since 2019-09-02
 */
public interface ITbMemberService extends IService<TbMember> {

    List<TbMember> memberList(Map map);

}
