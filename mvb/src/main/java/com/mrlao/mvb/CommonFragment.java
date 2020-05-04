package com.mrlao.mvb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * 当你不想创建Fragment的子类的时候，框架会自动使用CommonFragment来存放你的视图与业务
 *
 * @author mr-lao
 * @date-time 2018/5/18 11:49
 */
public class CommonFragment extends BaseFragment {
    protected int layoutID = -1;

    public void setLayoutID(int layoutID) {
        this.layoutID = layoutID;
    }

    public void setBusinessList(List<IBusiness> businessList) {
        this.businessList = businessList;
    }

    @Override
    protected View getLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layoutID != -1) {
            return inflater.inflate(layoutID, null);
        }
        return super.getLayout(inflater, container, savedInstanceState);
    }
}
