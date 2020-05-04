package com.mrlao.mvb;

import android.view.View;

/**
 * @author mr-lao
 * @date 2018年05月16日
 */
public interface  IViewHolder {
    /**
     * 当你不想使用框架的自动初始化ViewHolder功能的话，请将返回值设置成true，并在此方法中实现ViewHolder的初始化工作。
     *
     * @param view
     * @return
     */
    public boolean initView(View view);
}
