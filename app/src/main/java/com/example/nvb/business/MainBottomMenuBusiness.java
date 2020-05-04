package com.example.nvb.business;

import android.widget.RadioGroup;

import com.example.nvb.R;
import com.mrlao.mvb.BaseFragmentBusiness;
import com.mrlao.mvb.NotNeedDataHolder;
import com.mrlao.mvb.NotNeedViewHolder;

public class MainBottomMenuBusiness extends BaseFragmentBusiness<NotNeedViewHolder, NotNeedDataHolder> {
    @Override
    protected void init() {
        RadioGroup radioGroup = activity.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rd_btn_shop) {
                    // 选择第一个Fragment
                    selectFragment(0);
                } else if (checkedId == R.id.rd_btn_news) {
                    // 选择第二个Fragment
                    selectFragment(1);
                } else if (checkedId == R.id.rd_btn_my) {
                    // 选择第三个Fragment
                    selectFragment(2);
                }
            }
        });
    }

}
