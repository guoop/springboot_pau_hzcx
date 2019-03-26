package com.soft.ware.core.base.warpper;

import com.soft.ware.core.base.controller.BaseController;

import java.util.HashMap;
import java.util.Map;

public class SuccessWrapper extends BaseControllerWarpper {

    private String msg;

    public SuccessWrapper(String msg) {
        super(new HashMap<String,Object>());
        this.msg = msg;
    }

    public SuccessWrapper() {
        super(new HashMap<String,Object>());
    }

    public SuccessWrapper(Object obj) {
        super(obj);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        map.put("code", BaseController.SUCCESS);
        if (msg != null) {
            map.put("code", BaseController.SUCCESS);
        }
    }
}
