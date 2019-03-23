package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblQuestionMapper;
import com.soft.ware.rest.common.persistence.model.TblQuestion;
import com.soft.ware.rest.modular.auth.service.TblQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblQuestionServiceImpl extends ServiceImpl<TblQuestionMapper,TblQuestion> implements TblQuestionService {

    @Resource
    private TblQuestionMapper tblQuestionMapper;

    @Override
    public boolean add(TblQuestion question) {
        Integer insert = tblQuestionMapper.insert(question);
        return insert == 1;
    }

}