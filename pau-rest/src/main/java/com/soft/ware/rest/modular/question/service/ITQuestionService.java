package com.soft.ware.rest.modular.question.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.question.model.TQuestion;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 问题反馈 服务类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface ITQuestionService extends IService<TQuestion> {

    List<Map<String,Object>> findMaps(Map<String,Object> map);

    Map<String,Object> findMap(Map<String,Object> map);

    boolean add(SessionUser user, TQuestion question);
}
