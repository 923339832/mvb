package com.mrlao.mvb.annotation;

import com.mrlao.mvb.IBusiness;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单独注解Fragment，目的是不创建多余的Fragment实现类，即可实现Fragment的添加与业务实现
 *
 * @author mr-lao
 * @date-time 2018/5/18 15:23
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FragmentLayoutBusiness {
    /**
     * Fragment布局
     *
     * @return
     */
    int layout() default -1;

    /**
     * Fragment布局
     *
     * @return
     */
    String layoutIDText() default "";

    /**
     * Fragment业务
     *
     * @return
     */
    Class<? extends IBusiness>[] business() default {};
}
