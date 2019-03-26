package com.soft.ware.core.base.warpper;

import java.util.List;
import java.util.Map;

public class ListWrapper extends BaseControllerWarpper {


    public ListWrapper(List<?> obj) {
        super(obj);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {

    }
}
