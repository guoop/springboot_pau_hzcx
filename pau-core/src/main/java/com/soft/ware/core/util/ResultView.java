package com.soft.ware.core.util;

import org.springframework.util.CollectionUtils;

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
        return (ResultView) super.set(s, o);
    }

    public ResultView setOne(String key, List<Map<String,Object>> list){
        return (ResultView) super.set(key, CollectionUtils.isEmpty(list) ? new LinkedHashMap<>() : list.get(0));
    }

    @Override
    public ResultView setAll(Map<String, Object> map) {
        return (ResultView) super.setAll(map);
    }
}
