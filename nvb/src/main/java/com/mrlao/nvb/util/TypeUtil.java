package com.mrlao.nvb.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 获取泛型T的class类型
 *
 * @author bobo
 */
public class TypeUtil {

    @SuppressWarnings("rawtypes")
    public static Class getTypeClass(Object obj) {
        Type t = obj.getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) t;
            return (Class) pt.getActualTypeArguments()[0];
        }
        return null;
    }

    public static Class getTypeClass(Object obj, int index) {
        Type t = obj.getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) t;
            return (Class) pt.getActualTypeArguments()[index];
        }
        return null;
    }
}
