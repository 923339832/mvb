package com.mrlao.mvb;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mrlao.mvb.annotation.Business;
import com.mrlao.mvb.annotation.FragmentLayoutBusiness;
import com.mrlao.mvb.annotation.Fragments;
import com.mrlao.mvb.annotation.Order;
import com.mrlao.mvb.annotation.ViewLayout;
import com.mrlao.mvb.util.AnnotationUtil;
import com.mrlao.mvb.util.BusinessHelper;
import com.mrlao.mvb.util.ClassUtil;
import com.mrlao.mvb.util.FragmentHelper;
import com.mrlao.mvb.util.ResourceIDHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author mr-lao
 * @date 2018年05月16日
 */
public abstract class BaseActivity extends FragmentActivity {
    protected List<IBusiness> businessList = new ArrayList<>();
    private BusinessHelper businessHelper;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ResourceIDHelper.parseResourceID(this);
        setLayoutView();

        businessHelper = new BusinessHelper(this, businessList, null);
        businessHelper.initBusiness(this, getWindow().getDecorView());
        checkFragmentsIfExistAndInit();

        //检查activity中Fragment中的business
        businessHelper.setFragmentHelper(this.fragmentHelper);
    }

    private FragmentHelper fragmentHelper;

    /**
     * @return fragment的视图容器
     */
    protected int getFragmentContainerViewResourceID() {
        return -1;
    }

    /**
     * 检查Fragment是否存在
     */
    private void checkFragmentsIfExistAndInit() {
        final Fragments fragments = AnnotationUtil.getClassAnnotation(Fragments.class, this.getClass());
        if (fragments == null) {
            return;
        }
        int fragmentContainerId = fragments.fragmentContainerId();
        if (!TextUtils.isEmpty(fragments.fragmentContainerIdText()) && fragmentContainerId == -1) {
            fragmentContainerId = ResourceIDHelper.get(fragments.fragmentContainerIdText());
        }
        if (fragmentContainerId == -1) {
            fragmentContainerId = getFragmentContainerViewResourceID();
        }
        ViewGroup fragmentContainerView = findViewById(fragmentContainerId);
        if (fragmentContainerView instanceof ViewPager) {
            fragmentHelper = new FragmentHelper((ViewPager) fragmentContainerView);
        } else {
            fragmentHelper = new FragmentHelper(getSupportFragmentManager(), fragments.fragmentContainerId());
        }
        try {
            fragmentList = new ArrayList<>();
            if (fragments.value().length != 0) {
                Class<? extends Fragment>[] classList = fragments.value();
                for (Class<? extends Fragment> cls : classList) {
                    Fragment fragment = cls.newInstance();
                    fragmentList.add(fragment);
                    if (fragment instanceof BaseFragment) {
                        BaseFragment baseFragment = (BaseFragment) fragment;
                        baseFragment.setFragmentHelper(fragmentHelper);
                        baseFragment.setSession(businessHelper.getSession());
                    }
                }
            } else {
                //解释Activity中字段注解的Fragment
                Field[] declaredFields = ClassUtil.getFields(this.getClass());
                if (declaredFields.length == 0) {
                    //没有fragment
                    return;
                }

                HashMap<Integer, Fragment> indexFragmentsMap = new HashMap<>();

                for (Field field : declaredFields) {
                    Class<?> fieldType = field.getType();
                    if (!Fragment.class.isAssignableFrom(fieldType)) {
                        continue;
                    }

                    FragmentLayoutBusiness fragmentLayoutBusiness = field.getAnnotation(FragmentLayoutBusiness.class);
                    ViewLayout viewLayout = field.getAnnotation(ViewLayout.class);

                    if (fragmentLayoutBusiness == null && viewLayout == null) {
                        continue;
                    }

                    if (BaseFragment.class.isAssignableFrom(fieldType)) {
                        BaseFragment baseFragment;
                        if (CommonFragment.class == fieldType || BaseFragment.class == fieldType) {
                            CommonFragment commontFragment = new CommonFragment();
                            if (null != viewLayout) {
                                if (viewLayout.value() < 0) {
                                    if (!TextUtils.isEmpty(viewLayout.layoutIDText())) {
                                        commontFragment.setLayoutID(ResourceIDHelper.get(viewLayout.layoutIDText()));
                                    }
                                } else {
                                    commontFragment.setLayoutID(viewLayout.value());
                                }
                            } else {
                                if (fragmentLayoutBusiness.layout() < 0) {
                                    if (!TextUtils.isEmpty(fragmentLayoutBusiness.layoutIDText())) {
                                        commontFragment.setLayoutID(ResourceIDHelper.get(fragmentLayoutBusiness.layoutIDText()));
                                    }
                                } else {
                                    commontFragment.setLayoutID(fragmentLayoutBusiness.layout());
                                }
                            }

                            //业务类列表
                            Class<? extends IBusiness>[] businessClassArray = null;
                            if (null != fragmentLayoutBusiness) {
                                businessClassArray = fragmentLayoutBusiness.business();
                            } else {
                                Business businessAnnotation = field.getAnnotation(Business.class);
                                if (null != businessAnnotation) {
                                    businessClassArray = businessAnnotation.business();
                                }
                            }

                            if (null != businessClassArray) {
                                ArrayList<IBusiness> list = new ArrayList<>();
                                for (Class<? extends IBusiness> businessClass : businessClassArray) {
                                    list.add(businessClass.newInstance());
                                }
                                commontFragment.setBusinessList(list);
                            }
                            baseFragment = commontFragment;
                        } else {
                            baseFragment = (BaseFragment) fieldType.newInstance();
                        }
                        baseFragment.setFragmentHelper(fragmentHelper);
                        baseFragment.setSession(businessHelper.getSession());

                        Order order = field.getAnnotation(Order.class);
                        if (null != order) {
                            if (indexFragmentsMap.containsKey(order.value())) {
                                throw new RuntimeException("Activity【" + this.getClass().toString() + "】中Fragment中注解的@Order顺序有重复，请修正！");
                            }
                            indexFragmentsMap.put(order.value(), baseFragment);
                        } else {
                            fragmentList.add(baseFragment);
                        }
                        //设置值
                        ClassUtil.setFieldValue(field, this, baseFragment);
                    }
                }
                //解释order中的值
                if (indexFragmentsMap.size() > 0) {
                    Set<Integer> keySet = indexFragmentsMap.keySet();
                    Integer[] keyArray = new Integer[keySet.size()];
                    keySet.toArray(keyArray);
                    Arrays.sort(keyArray);
                    for (Integer key : keyArray) {
                        fragmentList.add(indexFragmentsMap.get(key));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (fragmentList.isEmpty()) {
            return;
        }
        if (fragmentContainerView instanceof ViewPager) {
            ViewPager viewPager = (ViewPager) fragmentContainerView;
            if (fragments.fragmentCacheSize() > 0) {
                viewPager.setOffscreenPageLimit(fragments.fragmentCacheSize());
            }
            FragmentPagerAdapter adpter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return fragmentList.get(position);
                }

                @Override
                public int getCount() {
                    return fragmentList.size();
                }
            };
            viewPager.setAdapter(adpter);
            //显示选择的那一个
            viewPager.setCurrentItem(fragments.selectFragment());
        } else {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            //显示选择的那一个
            Fragment fragment = fragmentList.get(fragments.selectFragment());
            supportFragmentManager.beginTransaction().add(fragmentContainerId, fragment).commit();
            fragmentHelper.setFragmentList(this.fragmentList);
        }
    }

    //设置资源布局
    protected void setLayoutView() {
        ViewLayout layout = AnnotationUtil.getClassAnnotation(ViewLayout.class, this.getClass());
        if (null == layout) {
            return;
        }
        int layoutResourceID = layout.value();
        if (layoutResourceID <= 0) {
            if (TextUtils.isEmpty(layout.layoutIDText())) {
                return;
            }
            layoutResourceID = ResourceIDHelper.get(layout.layoutIDText());
        }
        setContentView(layoutResourceID);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        businessHelper.onActivityResult(requestCode, resultCode, data);
    }


    public void onDestroy() {
        super.onDestroy();
        businessHelper.onDestroy();
    }


    public void onResume() {
        super.onResume();
        businessHelper.onResume();
    }

    /**
     * 请在Activity的onPause()方法中调用
     */
    public void onPause() {
        super.onPause();
        businessHelper.onPause();
    }

    /**
     * 请在Activity的onStop()方法中调用
     */
    public void onStop() {
        super.onStop();
        businessHelper.onStop();
    }

    /**
     * 请在Activity的onStart()方法中调用
     */
    public void onStart() {
        super.onStart();
        businessHelper.onStart();
    }

    /**
     * 请在Activity的onRestart()方法中调用
     */
    public void onRestart() {
        super.onRestart();
        businessHelper.onRestart();
    }

    /**
     * 请在Activity的onRestart()方法中调用
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (businessHelper.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @param index 指定的Fragment
     */
    @SuppressWarnings("all")
    protected void selectFragment(int index) {
        if (null != fragmentHelper) {
            fragmentHelper.selectFragment(index);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        businessHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
