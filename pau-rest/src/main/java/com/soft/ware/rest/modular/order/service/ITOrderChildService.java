package com.soft.ware.rest.modular.order.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.order.model.TOrderChild;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-11
 */
public interface ITOrderChildService extends IService<TOrderChild> {

    List<TOrderChild> selectOrderChildListByMap(Map<String,Object> map);

    List<Map> findMaps(Map<String,Object> map);
}
