package com.soft.ware.rest.modular.handover.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.modular.handover.dao.THandoverRecordMapper;
import com.soft.ware.rest.modular.handover.model.THandoverRecord;
import com.soft.ware.rest.modular.handover.service.ITHandoverRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交接班记录表 服务实现类
 * </p>
 * @author paulo123
 * @since 2019-04-10
 */
@Service
public class THandoverRecordServiceImpl extends ServiceImpl<THandoverRecordMapper, THandoverRecord> implements ITHandoverRecordService {

    @Resource
    private THandoverRecordMapper tHandoverRecordMapper;

    @Override
    public List<THandoverRecord> getHandOver(Map<String, Object> param) {
        return tHandoverRecordMapper.getHandOver(param);
    }
}
