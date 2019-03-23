package com.soft.ware.rest.modular.auth.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yancc E-mail:javaccy@163.com
 * @version 创建时间：29/11/2017
 */
public class BeanMapUtils extends org.springframework.beans.BeanUtils {

    private static Logger logger = LoggerFactory.getLogger(BeanMapUtils.class);

    public enum Mode{
        WHITE("白名单模式"),
        BLACK("黑明单模式"),
        REGEX_WHITE("正则白名单模式"),
        REGEX_BLACK("正则黑明单模式");

        private String desc;

        Mode(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }


    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                setter.invoke(obj, map.get(property.getName()));
            }
        }

        return obj;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if(obj == null)
            return null;

        Map<String, Object> map = new HashMap<>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter!=null ? getter.invoke(obj) : null;
            map.put(key, value);
        }

        return map;
    }


    public static Object toObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    public static List<Map<String, Object>> toMap(boolean underline,Collection list) throws Exception {
        List<Map<String, Object>> ls = new ArrayList<>();
        for (Object obj : list) {
            ls.add(toMap(obj, underline));
        }
        return ls;
    }


    public static Map<String, Object> toMap(Object obj,boolean underline) throws Exception {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        if (underline) {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(underline(new StringBuffer(field.getName())).toString(), field.get(obj));
            }
        } else {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        }
        return map;
    }

    /**
     * 驼峰转下划线
     * @param str
     * @return
     */
    public static StringBuffer underline(StringBuffer str) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if(matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb,"_"+matcher.group(0).toLowerCase());
            //把之后的也添加到StringBuffer对象里
            matcher.appendTail(sb);
        }else {
            return sb;
        }
        return underline(sb);
    }


    /**
     * Copy the property values of the given source bean into the target bean.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     * @param source the source bean
     * @param target the target bean
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        copyProperties(source, target, null,false, (String[]) null);
    }

    /**
     * Copy the property values of the given source bean into the given target bean,
     * only setting properties defined in the given "editable" class (or interface).
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     * @param source the source bean
     * @param target the target bean
     * @param editable the class (or interface) to restrict property setting to
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target, Class<?> editable) throws BeansException {
        copyProperties(source, target, editable,false, (String[]) null);
    }

    /**
     * Copy the property values of the given source bean into the given target bean,
     * only setting properties defined in the given "editable" class (or interface).
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     * @param source the source bean
     * @param target the target bean
     * @param ignoreNull the class (or interface) to restrict property setting to
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target, boolean ignoreNull) throws BeansException {
        copyProperties(source, target, null, ignoreNull, (String[]) null);
    }

    /**
     * Copy the property values of the given source bean into the given target bean.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * @param source the source bean
     * @param target the target bean
     * @param editable the class (or interface) to restrict property setting to
     * @param ignoreProperties array of property names to ignore
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    private static void copyProperties(Object source, Object target, Class<?> editable,boolean ignoreNull, String... ignoreProperties)
            throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            if (ignoreNull) {
                                if (value != null) {
                                    writeMethod.invoke(target, value);
                                }
                            } else {
                                writeMethod.invoke(target, value);
                            }
                        }
                        catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }


    private static String getDateFormat(Object obj, Method m, Field field) {
        try {
            JsonFormat ann = m.getAnnotation(JsonFormat.class);
            String format = null;
            if (ann != null) {
                format = ann.pattern();
            }
            if (format == null && field != null) {
                ann = field.getAnnotation(JsonFormat.class);
                if (ann != null) {
                    return ann.pattern();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Field getField(Class clazz,String fieldName) {
        try {
            if (clazz != Object.class) {
                Field field = null;
                try {
                    field = clazz.getDeclaredField(fieldName);
                    if (field == null) {
                        return getField(clazz.getSuperclass(), fieldName);
                    } else {
                        return field;
                    }
                } catch (NoSuchFieldException e) {
                    return getField(clazz.getSuperclass(),fieldName);
                }
            }
        } catch (Exception e) {
            logger.warn("找不到属性：" + clazz.getName() + "." + fieldName);
        }
        return null;

    }



    public static void removeKeys(Collection<Map<String,Object>> maps,String... keys) {
        for (Map<String, Object> map : maps) {
            for (String key : keys) {
                if (key.contains(".")) {
                    removeKey(map, key.split("\\."), 0);
                } else {
                    map.remove(key);
                }
            }
        }
    }


    /**
     * 多层删除(推荐)
     * @param map
     * @param keySplit
     */
    private static void removeKey(Map map,String[] keySplit){
        removeKey(map, keySplit,0);
    }
    /**
     * @see #removeKey(Map, String[])
     * 多层删除
     * @param map
     * @param keySplit
     */
    @Deprecated
    private static void removeKey(Map map,String[] keySplit,int num){
        Object o;
        String s;
        if (num < (keySplit.length - 1)) {
            s = keySplit[num];
            o = map.get(s);
            if (o instanceof Map) {
                removeKey((Map) o, keySplit, ++num);
            } else if (o instanceof Collection) {
                removeKeys((Collection<Map<String, Object>>) o, StringUtils.join(Arrays.copyOfRange(keySplit, num + 1, keySplit.length), "."));
            }
        } else {
            map.remove(keySplit[num]);
        }
    }





}
