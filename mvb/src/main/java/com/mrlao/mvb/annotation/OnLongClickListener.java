package com.mrlao.mvb.annotation;

public @interface OnLongClickListener {
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
