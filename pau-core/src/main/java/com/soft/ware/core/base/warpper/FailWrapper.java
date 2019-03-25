package com.soft.ware.core.base.warpper;

import com.soft.ware.core.base.controller.BaseController;

import java.util.HashMap;
import java.util.Map;

public class FailWrapper extends BaseControllerWarpper {

    public FailWrapper(Object obj) {
        super(obj);
    }

    public FailWrapper() {
        super(new HashMap<String,Object>());
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        map.put("code", BaseController.ERROR);
    }


}
