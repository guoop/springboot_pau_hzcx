package com.soft.ware.rest.modular.question.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.question.dao.TQuestionMapper;
import com.soft.ware.rest.modular.question.model.TQuestion;
import com.soft.ware.rest.modular.question.service.ITQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 问题反馈 服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@Service
@Transactional
public class TQuestionServiceImpl extends BaseService<TQuestionMapper,TQuestion> implements  ITQuestionService {

    @Resource
    private TQuestionMapper mapper;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Kv<String, Object> findMap(Map<String, Object> map) {
    List<Map<String, Object>> maps = findMaps(map);
        return maps.size() == 1 ? null : Kv.toKv(maps.get(0));
    }

    @Override
    public boolean add(SessionUser user,TQuestion question) {
        question.setId(IdGenerator.getId());
        question.setCreateTime(new Date());
        question.setOpenId(user.getOpenId());
        question.setOwnerId(user.getOwnerId());
        return mapper.insert(question) == 1;
    }

}