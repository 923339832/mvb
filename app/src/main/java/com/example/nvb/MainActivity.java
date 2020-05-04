package com.example.nvb;


import com.example.nvb.business.MainBottomMenuBusiness;
import com.example.nvb.business.MyBusiness;
import com.example.nvb.business.NewsBusiness;
import com.example.nvb.business.NewsClickBusiness;
import com.example.nvb.business.ShopBusiness;
import com.mrlao.mvb.BaseActivity;
import com.mrlao.mvb.BaseFragment;
import com.mrlao.mvb.annotation.Business;
import com.mrlao.mvb.annotation.FragmentLayoutBusiness;
import com.mrlao.mvb.annotation.Fragments;
import com.mrlao.mvb.annotation.Order;
import com.mrlao.mvb.annotation.ViewLayout;

@ViewLayout(R.layout.activity_main)
@Fragments(fragmentContainerId = R.id.fragment_container, fragmentCacheSize = 3, selectFragment = 1)
@SuppressWarnings("all")
public class MainActivity extends BaseActivity {

    @FragmentLayoutBusiness(layout = R.layout.fragment_shop,
            business = ShopBusiness.class)
    @Order(100)
    BaseFragment fragmentShop;

    @FragmentLayoutBusiness(
            layout = R.layout.fragment_news,
            business = {NewsBusiness.class, NewsClickBusiness.class})
    @Order(200)
    BaseFragment fragmentNesw;

    @FragmentLayoutBusiness(
            layout = R.layout.fragment_my,
            business = MyBusiness.class)
    @Order(300)
    BaseFragment fragmentMy;

    // 底部菜单
    @Business
    MainBottomMenuBusiness mainBottomMenuBusiness;
}
