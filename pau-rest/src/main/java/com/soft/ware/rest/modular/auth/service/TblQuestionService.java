package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblQuestion;

public interface TblQuestionService extends IService<TblQuestion> {

    boolean add(TblQuestion question);
}
