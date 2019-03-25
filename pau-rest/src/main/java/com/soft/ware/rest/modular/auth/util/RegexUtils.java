package com.soft.ware.rest.modular.auth.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {


    /**
     * 根据正则查询
     * @param m
     * @param pattern
     * @param t 如果查不到，返回自定义的默认值
     * @return
     */
    public static List<String> find(String m, Pattern pattern,String t){
        List<String> list = find(m, pattern);
        if (list.isEmpty()) {
            list.add(t);
        }
        return list;
    }


    /**
     * 根据正则查询
     * @param m
     * @param pattern
     * @return
     */
    public static List<String> find(String m, Pattern pattern){
        List<String> list = new ArrayList<>();
        Matcher matcher = pattern.matcher(m);
        if (matcher.matches()) {
            while (matcher.find()) {
                list.add(matcher.group());
            }
        }
        return list;
    }

}
