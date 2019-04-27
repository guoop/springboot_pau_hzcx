package com.soft.ware.rest.modular.order.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.modular.order.dao.TOrderChildMapper;
import com.soft.ware.rest.modular.order.model.TOrderChild;
import com.soft.ware.rest.modular.order.service.ITOrderChildService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-11
 */
@Service
public class TOrderChildServiceImpl extends ServiceImpl<TOrderChildMapper, TOrderChild> implements ITOrderChildService {

    @Resource
    private TOrderChildMapper tOrderChildMapper;

    @Override
    public List<TOrderChild> selectOrderChildListByMap(Map<String, Object> map) {
        return tOrderChildMapper.selectOrderChildListByMap(map);
    }

    @Override
    public List<Map<String,Object>> findMaps(Map<String, Object> map) {
        return tOrderChildMapper.findMaps(map);
    }


}
