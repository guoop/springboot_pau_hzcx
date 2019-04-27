package com.soft.ware.rest.modular.handover.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.handover.model.THandoverRecord;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交接班记录表 服务类
 * </p>
 * @author paulo123
 * @since 2019-04-10
 */
public interface ITHandoverRecordService extends IService<THandoverRecord> {

    List<Map<String,Object>> findMaps(Map<String,Object> map);

    Kv<String,Object> findMap(Map<String,Object> map);

    THandoverRecord findOne(Map<String, Object> map) throws Exception;

    List<Map<String,Object>> findPage(SessionUser user, HandoverPageParam param, Page page);

    THandoverRecord over(SessionUser user, HandoverParam param);
}
