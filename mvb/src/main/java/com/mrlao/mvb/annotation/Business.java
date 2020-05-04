package com.mrlao.mvb.annotation;


import com.mrlao.mvb.IBusiness;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将一个IBusiness属性设置成
 *
 * @author mr-lao
 * @date 2018年05月16日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Business {
    Class<?> value() default Object.class;

    Class<? extends IBusiness>[] business() default {};

    // 参数注入，business类中必须有constructor(string[])构造器
    String[] params() default {};
}
