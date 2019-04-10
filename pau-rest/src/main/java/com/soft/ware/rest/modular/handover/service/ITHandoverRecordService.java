package com.soft.ware.rest.modular.handover.service;

import com.baomidou.mybatisplus.service.IService;
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

    public List<THandoverRecord> getHandOver(Map<String,Object> param);

}
