package com.mrlao.nvb;

import android.view.View;

/**
 * <p>基本的IViewHolder实现类</p>
 *
 * @author mr-lao
 */
public abstract class BaseViewHolder implements IViewHolder {
    @Override
    public boolean initView(View view) {
        return false;
    }
}
