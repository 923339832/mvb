package com.mrlao.mvb;

import android.view.View;

/**
 * 不需要ViewHolder的ViewHolder实现
 * 使用场景：
 * Business不需要操作任何View的时候，可以使用NotNeedViewHolder
 *
 * @author mr-lao
 * @date 2018年05月18日
 */
public class NotNeedViewHolder implements IViewHolder {
    @Override
    public boolean initView(View view) {
        return true;
    }
}
