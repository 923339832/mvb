package com.mrlao.nvb.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author mr-lao
 * @date 2018年05月16日
 */
public class AnnotationUtil {

    /**
     * 解释Class中的所有Filed，将有annotationClass标注的Filed放到于filedList中，将Filed标注的注解放到annotationList中。
     * annotationList和filedList的长度一样
     *
     * @param annotationClass
     * @param targetClass
     * @param annotationList
     * @param filedList
     * @param <T>
     */
    public static <T extends Annotation> void parseFiledAnnotation(Class<T> annotationClass, Class<?> targetClass, List<T> annotationList, List<Field> filedList) {
        Field[] declaredFields = ClassUtil.getFields(targetClass);
        if (declaredFields == null || declaredFields.length == 0) {
            return;
        }
        for (Field field : declaredFields) {
            T annotation = field.getAnnotation(annotationClass);
            if (null == annotation) {
                continue;
            }
            annotationList.add(annotation);
            filedList.add(field);
        }
    }

    /**
     * 获取标注在类名上，指定类型为annotationClass的注解
     *
     * @param annotationClass 注解类型
     * @param targetClass     目标类
     * @param <T>             返回值
     * @return
     */
    public static <T extends Annotation> T getClassAnnotation(Class<T> annotationClass, Class<?> targetClass) {
        return targetClass.getAnnotation(annotationClass);
    }

    /**
     * @param annotationClass
     * @param targetClass
     * @param annotationList
     * @param methodList
     * @param <T>
     */
    public static <T extends Annotation> void parseMethodAnnotation(Class<T> annotationClass, Class<?> targetClass, List<T> annotationList, List<Method> methodList) {
        Method[] declaredMethods = targetClass.getDeclaredMethods();
        if (null == declaredMethods || declaredMethods.length == 0) {
            return;
        }
        for (Method method : declaredMethods) {
            T annotation = method.getAnnotation(annotationClass);
            if (annotation == null) {
                continue;
            }
            annotationList.add(annotation);
            methodList.add(method);
        }
    }


}
