package com.mrlao.mvb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ListView的选项被点击事件
 * 方法参数：AdapterView<?> parent, View view, int position, long id
 * 示例：public void onItemClick(AdapterView<?> parent, View view, int position, long id){}
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OnItemClickListener {
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
