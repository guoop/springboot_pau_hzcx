package com.soft.ware.rest.modular.handover.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.handover.model.THandoverRecord;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交接班记录表 Mapper 接口
 * </p>
 *
 * @author paulo123
 * @since 2019-04-10
 */
public interface THandoverRecordMapper extends BaseMapper<THandoverRecord> {
     //获取交接班记录
     public List<THandoverRecord> getHandOver(Map<String,Object> param);

}
