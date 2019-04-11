package com.soft.ware.rest.modular.order_app.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.order_app.model.TOrderApp;

import java.util.List;

public interface TOrderAppMapper extends BaseMapper<TOrderApp>  {

    List<TOrderApp> getAppOrderList();

}
