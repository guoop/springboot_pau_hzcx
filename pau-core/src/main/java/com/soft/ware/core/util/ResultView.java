package com.soft.ware.core.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResultView extends Kv<String,Object> {

    private static ResultView view(){
        return new ResultView();
    }

    public static ResultView emptyView(){
        return new ResultView();
    }


    public static ResultView view(boolean result){
        if (result) {
            return view().set("code","success");
        } else {
            return view().set("code", "error");
        }
    }

    public static ResultView view(boolean result,String msg){
        if (result) {
            return view().set("code", "success").set("msg", msg);
        } else {
            return view().set("code", "error").set("msg", msg);
        }
    }

    @Override
    public ResultView set(String s, Object o) {
        super.set(s, o);
        return this;
    }

    public ResultView setOne(String key, List<Map<String,Object>> list){
        super.set(key, list != null && list.size() > 0 ? list.get(0) : new LinkedHashMap<>());
        return this;
    }


    public ResultView remove(String key,String... keys){
        Map<String,Object> map = (Map<String, Object>)get(key);
        if (map != null) {
            for (String s : keys) {
                map.remove(s);
            }
        }
        return this;
    }

    public ResultView merge(String key, Map<String, Object> m) {
        Map<String, Object> map = (Map<String, Object>) get(key);
        if (m != null) {
            for (String s : m.keySet()) {
                if (!map.containsKey(s)) {
                    map.put(s, m.get(s));
                }
            }
        }
        return this;
    }

    /**
     * 合并
     * @param key
     * @param list
     * @return
     */
    public ResultView mergeOne(String key, List<Map<String, Object>> list) {
        Map<String, Object> map = (Map<String, Object>) get(key);
        if (list != null && list.size() > 0) {
            Map<String, Object> m = list.get(0);
            for (String s : m.keySet()) {
                if (!map.containsKey(s)) {
                    map.put(s, m.get(s));
                }
            }
        }
        return this;
    }

    @Override
    public ResultView setAll(Map<String, Object> map) {
        return (ResultView) super.setAll(map);
    }
}
