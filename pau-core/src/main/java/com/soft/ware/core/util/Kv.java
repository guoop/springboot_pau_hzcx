package com.soft.ware.core.util;

import com.soft.ware.core.base.tips.Tip;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
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

    public Integer getOnceInt(K k){
        Integer v = getInt(k);
        remove(k);
        return v;
    }

    public Integer getOnceInt(K k, Integer def) {
        Integer v = getInt(k, def);
        remove(k);
        return v;
    }

    public Integer requiredOnceInt(K k){
        Integer v = requiredInt(k);
        remove(k);
        return v;
    }

    public String getStr(K k){
        V v = map.get(k);
        return v == null ? null : v.toString();
    }

    public String getOnceStr(K k){
        String v = getStr(k);
        remove(v);
        return v;
    }

    public String getOnceStr(K k, String def) {
        String v = getStr(k, def);
        remove(v);
        return v;
    }

    public String requiredOnceStr(K k){
        String v = requiredStr(k);
        remove(k);
        return v;
    }

    public String requiredStr(K k){
        String v = getStr(k);
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalArgumentException(k + "不能为空");
        }
        return v;
    }

    public String getStr(K k, String def) {
        String v = getStr(k);
        return v == null ? def : v;
    }


    public Date getDate(K k) throws ParseException {
        V v = map.get(k);
        if (v != null) {
            if (v instanceof String) {
                return DateUtils.parseDate(v.toString(), "yyyy-MM-dd HH:mm:ss");
            } else if (v instanceof Long){
                return new Date(Long.valueOf(v.toString()));
            } else {
                return (Date) v;
            }
        }
        return null;
    }

    public Date getOnceDate(K k) throws ParseException {
        Date v = getDate(k);
        remove(v);
        return v;
    }

    public Date getOnceDate(K k, Date def) throws ParseException {
        Date v = getDate(k, def);
        remove(v);
        return v;
    }

    public Date requiredOnceDate(K k) throws ParseException {
        Date v = requiredDate(k);
        remove(k);
        return v;
    }

    public Date requiredDate(K k) throws ParseException {
        Date v = getDate(k);
        if (v == null) {
            throw new IllegalArgumentException(k + "不能为空");
        }
        return v;
    }

    public Date getDate(K k, Date def) throws ParseException {
        Date v = getDate(k);
        return v == null ? def : v;
    }



    public String getDateStr(K k) {
        V v = map.get(k);
        if (v != null) {
            if (v instanceof String) {
                return v.toString();
            } else {
                return DateFormatUtils.format((Date) v, "yyyy-MM-dd HH:mm:ss");
            }
        }
        return null;
    }

    public String getOnceDateStr(K k) {
        String v = getDateStr(k);
        remove(v);
        return v;
    }

    public String getOnceDateStr(K k, String def)  {
        String v = getDateStr(k, def);
        remove(v);
        return v;
    }

    public String requiredOnceDateStr(K k) {
        String v = requiredDateStr(k);
        remove(k);
        return v;
    }

    public String requiredDateStr(K k)  {
        String v = getDateStr(k);
        if (v == null) {
            throw new IllegalArgumentException(k + "不能为空");
        }
        return v;
    }

    public String getDateStr(K k, String def) {
        String v = getDateStr(k);
        return v == null ? def : v;
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
        Long v = getLong(k);
        return v == null ? def : v;
    }

    public Long getOnceLong(K k){
        Long v = getLong(k);
        remove(k);
        return v;
    }

    public Long getOnceLong(K k, Long def) {
        Long v = getLong(k, def);
        remove(k);
        return v;
    }

    public Long requiredOnceLong(K k){
        Long v = requiredLong(k);
        remove(k);
        return v;
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

    public Boolean getOnceBoolean(K k){
        Boolean v = getBoolean(k);
        remove(k);
        return v;
    }

    public Boolean getOnceBoolean(K k, Boolean def) {
        Boolean v = getBoolean(k, def);
        remove(k);
        return v;
    }

    public Boolean requiredOnceBoolean(K k){
        Boolean v = requiredBoolean(k);
        remove(k);
        return v;
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

    public BigDecimal getOnceBigDecimal(K k){
        BigDecimal v = getBigDecimal(k);
        remove(k);
        return v;
    }

    public BigDecimal getOnceBigDecimal(K k, BigDecimal def) {
        BigDecimal v = getBigDecimal(k, def);
        remove(k);
        return v;
    }

    public BigDecimal requiredOnceBigDecimal(K k){
        BigDecimal v = requiredBigDecimal(k);
        remove(k);
        return v;
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
        if (list == null) {
            throw new IllegalArgumentException(key + "不能为空");
        }
        return list;
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

    public V get(K key,V def) {
        return map.containsKey(key) ? def : map.get(key);
    }

    public V required(K key,V def) {
        V v = get(key);
        if (v == null) {
            throw new IllegalArgumentException(key + "不能为空");
        }
        return v;
    }

    /**
     * 获取并删除
     * @param key
     * @return
     */
    public V getOnce(K key) {
        V v = map.get(key);
        map.remove(key);
        return v;
    }

    public V require(K key) {
        V v = map.get(key);
        if (v == null) {
            throw new IllegalArgumentException(key + "不能为空");
        }
        return v;
    }

    public V requireOnce(K key) {
        V v = require(key);
        remove(key);
        return v;
    }


    public Kv dels(K... keys){
        for (K key : keys) {
            remove(key);
        }
        return this;
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

    public Kv<K, V> clone() {
        Kv<K, V> kv = Kv.init();
        kv.putAll(this.map);
        return kv;
    }

    @SafeVarargs
    public static Kv<String,Object> toKv(Map<String,Object>... maps){
        if (maps == null || maps.length < 1) {
            return Kv.obj();
        } else {
            Kv<String, Object> kv = Kv.obj().setAll(maps[0]);
            for (int i = 1; i < maps.length; i++) {
                if (maps[i] != null) {
                    kv.setAll(maps[i]);
                }
            }
            return kv;
        }
    }


    public static List<Kv<String,Object>> toKvs(List<Map<String,Object>> maps){
        List<Kv<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> m : maps) {
            list.add(toKv(m));
        }
        return list;
    }

    public static Kv<String,String> toStringMap(Map<String, Object> map) {
        return toStringMap(map, EmptyMode.NULL_KEEP);
    }

    public static Kv<String,String> toStringMap(Map<String, Object> map,EmptyMode mode) {
        Kv<String, String> kv = Kv.init();
        if (mode == EmptyMode.NULL_IGNORE ) {
            for (Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    kv.put(entry.getKey(),entry.getValue().toString());
                }
            }
        } else if (mode == EmptyMode.NULL_KEEP) {
            for (Entry<String, Object> entry : map.entrySet()) {
                kv.put(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
            }
        } else {
            for (Entry<String, Object> entry : map.entrySet()) {
                kv.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
            }
        }
        return kv;
    }


    public enum EmptyMode{

        NULL_IGNORE("NULL_IGNORE"),
        NULL_KEEP("NULL_KEEP"),
        NULL_BLANK("null->\"\"");

        private String value;

        EmptyMode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
