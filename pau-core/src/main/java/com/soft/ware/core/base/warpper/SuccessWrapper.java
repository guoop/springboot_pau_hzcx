package com.soft.ware.core.base.warpper;

import com.soft.ware.core.base.controller.BaseController;

import java.util.HashMap;
import java.util.Map;

public class SuccessWrapper extends BaseControllerWarpper {

    public SuccessWrapper() {
        super(new HashMap<String,Object>());
    }

    public SuccessWrapper(Object obj) {
        super(obj);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        map.put("code", BaseController.SUCCESS);
    }
}
