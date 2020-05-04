package com.mrlao.mvb.util;

import java.lang.reflect.Field;

public class ClassUtil {
    /**
     * 设置字段
     *
     * @param field
     * @param obj
     * @param value
     */
    public static void setFieldValue(Field field, Object obj, Object value) {
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            try {
                field.setAccessible(true);
                field.set(obj, value);
                field.setAccessible(false);
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
    }

    @SuppressWarnings("all")
    public static <T> T getFieldValue(Field field, Object obj) {
        try {
            return (T) field.get(obj);
        } catch (IllegalAccessException e) {
            try {
                field.setAccessible(true);
                Object o = field.get(obj);
                field.setAccessible(false);
                return (T) o;
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public static Field[] getFields(Class cls) {
        /*Field[] declaredFields = cls.getDeclaredFields();
        if (declaredFields == null || declaredFields.length == 0) {
            return declaredFields;
        }
        // 倒置
        Field[] rest = new Field[declaredFields.length];
        int index = 0;
        for (int i = declaredFields.length - 1; i >= 0; i--) {
            rest[index] = declaredFields[i];
            index++;
        }
        return rest;*/
        return cls.getDeclaredFields();
    }
}
