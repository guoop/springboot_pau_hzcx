package com.soft.ware.core.base.warpper;

import java.util.HashMap;
import java.util.Map;

public class MapWrapper extends BaseControllerWarpper {

    private Map<String, Object> o;

    public MapWrapper(Map<String,Object> map) {
        super(map);
        o = (HashMap<String,Object>)obj;
    }

    public MapWrapper() {
        super(new HashMap<>());
        o = (HashMap<String,Object>)obj;
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {

    }



    public Map<String,Object> put(String key,Object value){
        o.put(key, value);
        return o;
    }
}
