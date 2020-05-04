package com.example.nvb.business;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.nvb.R;
import com.example.nvb.dataholder.MainActivityDataHolder;
import com.mrlao.nvb.BaseBusiness;
import com.mrlao.nvb.NotNeedViewHolder;
import com.mrlao.nvb.annotation.OnItemClickListener;

public class NewsClickBusiness extends BaseBusiness<NotNeedViewHolder, MainActivityDataHolder> {

    @Override
    protected void init() {

    }

    @SuppressWarnings({"all", "点击事件处理"})
    @OnItemClickListener(R.id.list_news)
    public void onNewsListViewItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (dataHolder.newsList == null) {
            Toast.makeText(activity, "程序异常，newsList变量为null", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(activity, dataHolder.newsList.get(position), Toast.LENGTH_SHORT).show();
    }
}
