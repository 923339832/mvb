package com.mrlao.mvb;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mrlao.mvb.annotation.ViewLayout;
import com.mrlao.mvb.util.AnnotationUtil;
import com.mrlao.mvb.util.BusinessHelper;
import com.mrlao.mvb.util.FragmentHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mr-lao
 * @data 2018年05月17日
 */
public abstract class BaseFragment extends Fragment {
    protected List<IBusiness> businessList = new ArrayList<>();
    protected BusinessHelper businessHelper;
    protected FragmentHelper fragmentHelper;

    protected Map<String, Object> session;

    public void setFragmentHelper(FragmentHelper fragmentHelper) {
        this.fragmentHelper = fragmentHelper;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayout(inflater, container, savedInstanceState);
        businessHelper = new BusinessHelper(this, businessList, session);
        businessHelper.initBusiness(this.getActivity(), view);
        //设置FragmentHelper
        for (IBusiness business : businessList) {
            if (business instanceof BaseFragmentBusiness) {
                BaseFragmentBusiness baseFragmentBusiness = (BaseFragmentBusiness) business;
                baseFragmentBusiness.setFragmentHelper(fragmentHelper);
            }
        }
        return view;
    }

    @SuppressWarnings("all")
    protected View getLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewLayout layout = AnnotationUtil.getClassAnnotation(ViewLayout.class, this.getClass());
        return inflater.inflate(layout.value(), null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        businessHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        businessHelper.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        businessHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        businessHelper.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        businessHelper.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        businessHelper.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        businessHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
