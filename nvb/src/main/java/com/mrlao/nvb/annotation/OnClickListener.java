package com.mrlao.nvb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * View的点击事件监听
 *
 * @author mr-lao
 * @date 2018年05月16日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OnClickListener {
    /**
     * View的资源ID
     *
     * @return View的资源ID数组
     */
    int[] value();

    /**
     * 资源ID名称
     *
     * @return 资源ID名称
     */
    String[] idsText() default "";
}
