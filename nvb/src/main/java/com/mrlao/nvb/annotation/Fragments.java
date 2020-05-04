package com.mrlao.nvb.annotation;


import androidx.fragment.app.Fragment;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 给一个Activity添加Fragment
 *
 * @author mr-lao
 * @date 2018年05月16日
 */
@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Fragments {
    /**
     * Fragment类的Class
     *
     * @return
     */
    Class<? extends Fragment>[] value() default {};

    /**
     * Fragment容器的资源ID
     * 在module中，因为资源ID的值不是常量，所以在module中不能直接在注解中指定资源ID。
     *
     * @return
     */
    int fragmentContainerId() default -1;

    String fragmentContainerIdText() default "";

    /**
     * 默认选择第几个Fragment
     *
     * @return
     */
    int selectFragment() default 0;

    /**
     * 如果fragmentContainerId的值指向的是ViewPager，此值才会有意义。
     * 作用是设置ViewPager的页面缓存大小
     *
     * @return
     */
    int fragmentCacheSize() default -1;
}
