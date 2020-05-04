package com.mrlao.mvb.util;

import android.app.Activity;
import android.view.View;

import com.mrlao.mvb.IViewHolder;
import com.mrlao.mvb.annotation.ViewInject;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author mr-lao
 * @date 2018年05月18日
 */
public class ViewHolderHelper {
    /**
     * 初始化ViewHolder
     *
     * @param view       视图控件容器
     * @param viewHolder viewHolder
     */
    public static void initViewHolder(View view, IViewHolder viewHolder) {
        ArrayList<Field> fields = new ArrayList<>();
        ArrayList<ViewInject> viewInjects = new ArrayList<>();
        AnnotationUtil.parseFiledAnnotation(ViewInject.class, viewHolder.getClass(), viewInjects, fields);
        if (fields.isEmpty()) {
            return;
        }
        for (int i = 0; i < fields.size(); i++) {
            Field f = fields.get(i);
            ViewInject v = viewInjects.get(i);
            View value = view.findViewById(v.value());
            ClassUtil.setFieldValue(f, viewHolder, value);
        }
    }

    public static void initViewHolder(Activity activity, IViewHolder viewHolder) {
        initViewHolder(activity.getWindow().getDecorView(), viewHolder);
    }
}
