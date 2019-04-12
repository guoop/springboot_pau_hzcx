package com.soft.ware.core.util;

import com.soft.ware.core.base.tips.Tip;

import java.math.BigDecimal;
import java.util.*;

public class Kv<K,V> extends Tip implements Map<K,V>  {

    private Map<K,V> map;

    public Kv() {
        this(new LinkedHashMap<>());
    }

    public Kv(Map<K, V> map) {
        this.map = map;
    }


    public static <K,V> Kv<K,V> init(){
        return new Kv<>(new LinkedHashMap<>());
    }

    public static Kv<String,Object> obj(){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        return new Kv<>(map);
    }

    public static Kv<String,Object> obj(String key,Object value){
        return obj().set(key, value);
    }


    public static <K,V> Kv<K,V> by(K k, V v){
        Kv<K,V> kv = new Kv<>(new LinkedHashMap<>());
        kv.put(k, v);
        return kv;
    }

    public Kv<K,V> set(K k,V v){
       map.put(k, v);
       return this;
    }

    public Kv<K,V> setAll(Map<K,V> map){
        this.map.putAll(map);
        return this;
    }

    public Integer getInt(K k){
        V v = map.get(k);
        if (v != null) {
            return Integer.valueOf(v + "");
        }
        return null;
    }

    public Integer requiredInt(K k){
        Integer v = getInt(k);
        if (v == null) {
            throw new IllegalArgumentException(k + "不能为空");
        }
        return v;
    }

    public Integer getInt(K k,Integer def){
        Integer i = getInt(k);
        return i == null ? def : i;
    }

    public String getStr(K k){
        V v = map.get(k);
        return v == null ? null : v.toString();
    }

    public String requiredStr(K k){
        String str = getStr(k);
        if (str == null) {
            throw new IllegalArgumentException(k + "不能为空");
        }
        return str;
    }

    public String getStr(K k, String def) {
        String s = getStr(k);
        return s == null ? def : s;
    }

    public Long getLong(K k){
        V v = map.get(k);
        if (v != null) {
            return Long.valueOf(v + "");
        }
        return null;
    }

    public Long requiredLong(K k){
        Long v = getLong(k);
        if (v == null) {
            throw new IllegalArgumentException(k + "不能为空");
        }
        return v;
    }


    public Long getLong(K k,Long def){
        Long i = getLong(k);
        return i == null ? def : i;
    }

    public Boolean getBoolean(K key) {
        Object o = map.get(key);
        if (o != null) {
            if (o instanceof Boolean) {
                return (Boolean) o;
            }
            o = o + "";
            if ("true".equals(key)) {
                return true;
            }
            if ("false".equals(key)) {
                return true;
            }
            if ("1".equals(o)) {
                return true;
            }
            if ("0".equals(o)) {
                return false;
            }
        }
        return null;
    }

    public boolean requiredBoolean(K k){
        Boolean v = getBoolean(k);
        if (v == null) {
            throw new IllegalArgumentException(k + "不能为空");
        }
        return v;
    }

    public Boolean getBoolean(K key, boolean def) {
        return getBoolean(key) == null ? def : getBoolean(key);
    }

    /**
     * 注意数据类型需要一至
     * @param key
     * @param trueValue
     * @param falseValue
     * @return
     */
    public Boolean getBoolean(K key,Object trueValue,Object falseValue){
        V v = get(key);
        if (trueValue.equals(v)) {
            return true;
        }
        if (falseValue.equals(v)) {
            return false;
        }
        return null;
    }

    public BigDecimal getBigDecimal(K key){
        Object v = map.get(key);
        if (v != null) {
            return BigDecimal.valueOf(Double.valueOf(v.toString()));
        }
        return null;
    }

    public BigDecimal requiredBigDecimal(K k){
        BigDecimal v = getBigDecimal(k);
        if (v == null) {
            throw new IllegalArgumentException(k + "不能为空");
        }
        return v;
    }

    public BigDecimal getBigDecimal(K key,BigDecimal def){
        BigDecimal v = getBigDecimal(key);
        return v == null ? def : v;
    }

    public List<Kv<String,Object>> getKvs(K key){
        V v = map.get(key);
        if (v != null) {
            if(v instanceof List){
                List<Map<String,Object>> ls = (List)v;
                return toKvs(ls);
            }
        }
        return null;
    }

    public List<Kv<String,Object>> getRequiredList(K key){
        List<Kv<String, Object>> list = getKvs(key);
        return list == null ? new ArrayList<>() : list;
    }

    public List<Kv<String,Object>> getList(K key,List<Kv<String,Object>> list){
        List<Kv<String, Object>> ls = getKvs(key);
        return ls == null ? list : ls;
    }

    public Map<K,V> toMap(){
        return map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    public static Kv<String,Object> toKv(Map<String,Object> map){
        return Kv.obj().setAll(map);
    }


    public static List<Kv<String,Object>> toKvs(List<Map<String,Object>> maps){
        List<Kv<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> m : maps) {
            list.add(toKv(m));
        }
        return list;
    }


}
