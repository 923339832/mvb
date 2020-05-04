package com.mrlao.nvb;


import com.mrlao.nvb.util.FragmentHelper;

/**
 * @author mr-lao
 * @date 2018年05月18日
 */
public abstract class BaseFragmentBusiness<ViewHolder extends IViewHolder, DataHolder> extends BaseBusiness<ViewHolder, DataHolder> {
    private FragmentHelper fragmentHelper;

    public void setFragmentHelper(FragmentHelper fragmentHelper) {
        this.fragmentHelper = fragmentHelper;
    }

    @SuppressWarnings("all")
    protected void selectFragment(int index) {
        if (null != fragmentHelper) {
            fragmentHelper.selectFragment(index);
        }
    }
}
