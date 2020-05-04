package com.mrlao.nvb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 布局
 *
 * @author mr-lao
 * @date 2018年05月16日
 */
@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewLayout {
    int value();

    String layoutIDText() default "";
}
