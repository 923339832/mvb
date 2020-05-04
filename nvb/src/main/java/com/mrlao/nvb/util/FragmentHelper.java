package com.mrlao.nvb.util;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

/**
 * Fragment帮助类，主要是用于选择Fragment
 *
 * @author mr-lao
 * @date 2018年05月18日
 */
public class FragmentHelper {
    private ViewPager viewPager;
    private FragmentManager supportFragmentManager;
    private List<Fragment> fragmentList;
    private int containerID;

    public FragmentHelper(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public FragmentHelper(FragmentManager supportFragmentManager, int containerID) {
        this.supportFragmentManager = supportFragmentManager;
        this.containerID = containerID;
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }

    public void selectFragment(int index) {
        if (viewPager != null) {
            selectFragmentViewPager(index);
        } else {
            selectFragmentManager(index);
        }
    }

    private void selectFragmentViewPager(int index) {
        viewPager.setCurrentItem(index);
    }

    private void selectFragmentManager(int index) {
        //隐藏所有
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        for (Fragment fragment : supportFragmentManager.getFragments()) {
            fragmentTransaction.hide(fragment);
        }
        Fragment fragment = this.fragmentList.get(index);
        if (!supportFragmentManager.getFragments().contains(fragment)) {
            fragmentTransaction.add(containerID, fragment);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }
}
