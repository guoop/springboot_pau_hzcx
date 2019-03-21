package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblAppVersion;

public interface TblAppVersionService extends IService<TblAppVersion> {

    /**
     * 查询最新版本的app
     * @param platformCode
     * @return
     */
    TblAppVersion findLast(String platformCode);
}
