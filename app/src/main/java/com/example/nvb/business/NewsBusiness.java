package com.example.nvb.business;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.nvb.R;
import com.example.nvb.dataholder.MainActivityDataHolder;
import com.example.nvb.viewholder.NewsViewHolder;
import com.mrlao.mvb.BaseBusiness;
import com.mrlao.mvb.annotation.OnClickListener;

import java.util.ArrayList;
import java.util.List;

public class NewsBusiness extends BaseBusiness<NewsViewHolder, MainActivityDataHolder> {
    private BaseAdapter adapter;
    private List<String> list = new ArrayList<>();

    private List<String> getNewsList() {
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        return list;
    }

    @Override
    protected void init() {
        adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, getNewsList());
        viewHolder.listViewNews.setAdapter(adapter);

        dataHolder.newsList = list;
    }

    @OnClickListener(R.id.btn_refresh)
    public void refresh(View view) {
        list.clear();
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        list.add("美国总统特朗普与中国达成共识，双方停止新的关税增加");
        adapter.notifyDataSetChanged();
        Toast.makeText(activity, "刷新新闻数据", Toast.LENGTH_SHORT).show();
    }

    @OnClickListener(R.id.btn_load_more)
    public void loadMore(View view) {
        list.add("人民日报批评伪娘祸害下一代青少年健康成长");
        list.add("人民日报批评伪娘祸害下一代青少年健康成长");
        list.add("人民日报批评伪娘祸害下一代青少年健康成长");
        list.add("人民日报批评伪娘祸害下一代青少年健康成长");
        list.add("人民日报批评伪娘祸害下一代青少年健康成长");
        list.add("人民日报批评伪娘祸害下一代青少年健康成长");
        list.add("人民日报批评伪娘祸害下一代青少年健康成长");
        list.add("人民日报批评伪娘祸害下一代青少年健康成长");
        list.add("人民日报批评伪娘祸害下一代青少年健康成长");
        list.add("人民日报批评伪娘祸害下一代青少年健康成长");
        adapter.notifyDataSetChanged();
        Toast.makeText(activity, "加载更多新闻数据", Toast.LENGTH_SHORT).show();
    }
}
