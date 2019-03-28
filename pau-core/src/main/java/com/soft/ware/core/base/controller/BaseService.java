package com.soft.ware.core.base.controller;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IService<T> {

    protected Logger logger;

    public BaseService() {
         this.logger = LoggerFactory.getLogger(getClass());
    }
}
