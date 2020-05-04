package com.mrlao.mvb;

import android.app.Activity;
import android.view.View;

/**
 * <p>业务接口。一个IBusiness实现类，就是一个业务处理类。</p>
 *
 * @param <ViewHolder> 存放View的容器
 * @param <DataHolder> 存放数据的容器
 * @author mr-lao
 * @date 2018年05月16日
 */
public interface IBusiness<ViewHolder extends IViewHolder, DataHolder> extends IActivityLife {
    /**
     * 当Activity调用setViewHolder或initBusiness方法 时，会调用init()方法。
     * 子类通过实现init()方法来初始化自身的一些属性和数据，init()方法是业务类的入口方法。
     */
    void initBusiness(Activity activity, View layout);

    /**
     * 获取值，用于不同的business之间数据共享
     *
     * @param key
     * @param <T>
     * @return
     */
    <T> T getParam(String key);

    /**
     * 保存值，用于不同的business之间的数据共享
     *
     * @param param
     * @param key
     */
    void addParam(Object param, String key);

    /**
     * 是否需要拦截下一个Business初始化
     *
     * @return
     */
    boolean needInterceptNextBusinessInit();
}
