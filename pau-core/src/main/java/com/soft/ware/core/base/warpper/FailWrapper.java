package com.soft.ware.core.base.warpper;

import com.soft.ware.core.base.controller.BaseController;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class FailWrapper extends BaseControllerWarpper {

    private String msg;

    public FailWrapper(String msg) {
        super(new HashMap<String,Object>());
        this.msg = msg;
    }

    public FailWrapper(Object obj) {
        super(obj);
    }

    public FailWrapper() {
        super(new HashMap<String,Object>());
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        map.put("code", BaseController.ERROR);
        if (StringUtils.isNotBlank(msg)) {
            map.put("msg", this.msg);
        }
    }




}
