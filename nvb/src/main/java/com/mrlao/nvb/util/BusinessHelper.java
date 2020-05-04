package com.mrlao.nvb.util;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.mrlao.nvb.BaseBusiness;
import com.mrlao.nvb.BaseFragmentBusiness;
import com.mrlao.nvb.IBusiness;
import com.mrlao.nvb.annotation.Business;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Business的帮助类
 *
 * @author mr-lao
 * @date 2018年05月18日
 */
public class BusinessHelper implements IBusiness {
    private List<IBusiness> businessList;
    private Object targetObj;
    private final Map<String, Object> session;

    public Map<String, Object> getSession() {
        return this.session;
    }

    public BusinessHelper(Object target, List<IBusiness> businessList, Map<String, Object> session) {
        this.targetObj = target;
        this.businessList = businessList;
        if (null == session) {
            this.session = new HashMap<>();
        } else {
            this.session = session;
        }
    }

    /**
     * 设置FragmentHelper
     *
     * @param fragmentHelper 辅助类
     */
    public void setFragmentHelper(FragmentHelper fragmentHelper) {
        for (IBusiness business : this.businessList) {
            if (business instanceof BaseFragmentBusiness) {
                ((BaseFragmentBusiness) business).setFragmentHelper(fragmentHelper);
            }
        }
    }

    public void initBusiness(Activity activity, View layout) {
        initBusiness(activity, layout, true);
    }

    @SuppressWarnings("all")
    public void initBusiness(Activity activity, View layout, boolean createBusiness) {
        if (createBusiness) {
            createBusiness();
        }
        if (this.businessList.isEmpty()) {
            return;
        }
        ArrayList<IBusiness> needList = new ArrayList<>();
        for (IBusiness business : this.businessList) {
            if (business instanceof BaseBusiness) {
                ((BaseBusiness) business).setSesstion(session);
            }
            if (null == layout) {
                business.initBusiness(activity, activity.getWindow().getDecorView());
            } else {
                business.initBusiness(activity, layout);
            }
            needList.add(business);
            if (business.needInterceptNextBusinessInit()) {
                //移除
                break;

            }
        }
        this.businessList.clear();
        this.businessList.addAll(needList);
    }

    @Override
    public void addParam(Object param, String key) {

    }

    @Override
    public boolean needInterceptNextBusinessInit() {
        return false;
    }

    @Override
    public Object getParam(String key) {
        return null;
    }

    private void createBusiness() {
        try {
            //解决类名的Business
            Business businessAnnotation = AnnotationUtil.getClassAnnotation(Business.class, targetObj.getClass());
            if (null != businessAnnotation) {
                Class<? extends IBusiness>[] businessClasss = businessAnnotation.business();
                if (businessClasss.length != 0) {
                    for (Class<? extends IBusiness> businessClass : businessClasss) {
                        IBusiness business = (IBusiness) businessClass.newInstance();
                        this.businessList.add(business);
                    }
                }
            }
            //解释类中字段的Business
            ArrayList<Business> businessAnnotationList = new ArrayList<>();
            ArrayList<Field> fieldList = new ArrayList<>();
            AnnotationUtil.parseFiledAnnotation(Business.class, targetObj.getClass(), businessAnnotationList, fieldList);
            if (!businessAnnotationList.isEmpty()) {
                for (int i = 0; i < businessAnnotationList.size(); i++) {
                    Business annotation = businessAnnotationList.get(i);
                    Class<?> businessClass = annotation.value();
                    if (businessClass.equals(Object.class)) {
                        businessClass = fieldList.get(i).getType();
                    }
                    if (!IBusiness.class.isAssignableFrom(businessClass)) {
                        continue;
                    }
                    IBusiness business = newBusinessInstance(annotation, businessClass);
                    ClassUtil.setFieldValue(fieldList.get(i), targetObj, business);
                    this.businessList.add(business);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private IBusiness newBusinessInstance(Business businessAnnotation, Class<?> businessClass) throws Exception {
        IBusiness business = null;
        String[] params = businessAnnotation.params();
        if (params.length > 0) {
            Constructor<?> constructor = businessClass.getConstructor(String[].class);
            business = (IBusiness) constructor.newInstance((Object) params);
        } else {
            business = (IBusiness) businessClass.newInstance();
        }
        return business;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.businessList.isEmpty()) {
            return false;
        }
        for (IBusiness business : this.businessList) {
            if (business.onActivityResult(requestCode, resultCode, data)) {
                return true;
            }
        }
        return false;
    }


    public boolean onDestroy() {
        if (this.businessList.isEmpty()) {
            return false;
        }
        for (IBusiness business : this.businessList) {
            if (business.onDestroy()) {
                return true;
            }
        }
        return false;
    }


    public void onResume() {
        if (this.businessList.isEmpty()) {
            return;
        }
        for (IBusiness business : this.businessList) {
            business.onResume();
        }
    }

    /**
     * 请在Activity的onPause()方法中调用
     */
    public void onPause() {
        if (this.businessList.isEmpty()) {
            return;
        }
        for (IBusiness business : this.businessList) {
            business.onPause();
        }
    }

    /**
     * 请在Activity的onStop()方法中调用
     */
    public void onStop() {
        if (this.businessList.isEmpty()) {
            return;
        }
        for (IBusiness business : this.businessList) {
            business.onStop();
        }
    }

    /**
     * 请在Activity的onStart()方法中调用
     */
    public void onStart() {
        if (this.businessList.isEmpty()) {
            return;
        }
        for (IBusiness business : this.businessList) {
            business.onStart();
        }
    }

    /**
     * 请在Activity的onRestart()方法中调用
     */
    public void onRestart() {
        if (this.businessList.isEmpty()) {
            return;
        }
        for (IBusiness business : this.businessList) {
            business.onRestart();
        }
    }

    /**
     * 请在Activity的onRestart()方法中调用
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this.businessList.isEmpty()) {
            return false;
        }
        for (IBusiness business : this.businessList) {
            if (business.onKeyDown(keyCode, event)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 请在Activity的onRequestPermissionsResult()方法中调用
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (this.businessList.isEmpty()) {
            return;
        }
        for (IBusiness business : this.businessList) {
            business.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
