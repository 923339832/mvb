package com.example.nvb.viewholder;

import android.widget.ListView;

import com.example.nvb.R;
import com.mrlao.mvb.BaseViewHolder;
import com.mrlao.mvb.annotation.ViewInject;


public class NewsViewHolder extends BaseViewHolder {
    @ViewInject(R.id.list_news)
    public ListView listViewNews;
}
