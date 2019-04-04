package com.soft.ware.core.util;

import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Kv<K,V> extends Tip implements Map<K,V>  {

    private Map<K,V> map;

    public Kv() {
        this(new LinkedHashMap<>());
    }

    public Kv(Map<K, V> map) {
        this.map = map;
    }

    public static Kv<String,Object> view(boolean result){
        if (result) {
            SuccessTip s = new SuccessTip();
            return Kv.obj().set("code", s.getCode()).set("msg", s.getMsg());
        } else {
            SuccessTip s = new SuccessTip();
            return Kv.obj().set("code", s.getCode()).set("msg", s.getMsg());
        }
    }

    public static Kv<String,Object> view(boolean result,String msg){
        if (result) {
            SuccessTip s = new SuccessTip(msg);
            return Kv.obj().set("code", s.getCode()).set("msg", s.getMsg());
        } else {
            SuccessTip s = new SuccessTip(msg);
            return Kv.obj().set("code",s.getCode()).set("msg", msg);
        }
    }


    public static <K,V> Kv<K,V> init(){
        Kv kv = new Kv<>(new LinkedHashMap<>());
        return kv;
    }

    public static Kv<String,Object> obj(){
        LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
        Kv kv = new Kv(map);
        return kv;
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

    public Integer getInt(K k){
        V v = map.get(k);
        if (v != null) {
            return Integer.valueOf(v + "");
        }
        return null;
    }

    public Integer getInt(K k,Integer def){
        Integer i = getInt(k);
        return i == null ? def : i;
    }

    public Long getLong(K k){
        V v = map.get(k);
        if (v != null) {
            return Long.valueOf(v + "");
        }
        return null;
    }

    public Long getLong(K k,Long def){
        Long i = getLong(k);
        return i == null ? def : i;
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



}
