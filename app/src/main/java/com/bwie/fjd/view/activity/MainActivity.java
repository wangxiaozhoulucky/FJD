package com.bwie.fjd.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bwie.fjd.R;
import com.bwie.fjd.view.fragment.ClassifyFragment;
import com.bwie.fjd.view.fragment.HomeFragment;
import com.bwie.fjd.view.fragment.MessageFragment;
import com.bwie.fjd.view.fragment.MyFragment;
import com.bwie.fjd.view.fragment.ShopFragment;
import com.hjm.bottomtabbar.BottomTabBar;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bottom_tab_bar)
    BottomTabBar bottomTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        dibu();
    }

    //底部导航
    private void dibu() {
        bottomTabBar.init(getSupportFragmentManager())
                .setImgSize(26, 26)
                .setFontSize(14)
                .setTabPadding(4, 6, 10)
                .setChangeColor(Color.RED, Color.DKGRAY)
                .addTabItem("首页", R.drawable.home, HomeFragment.class)
                .addTabItem("分类", R.drawable.classily, ClassifyFragment.class)
                .addTabItem("消息", R.drawable.sou, MessageFragment.class)
                .addTabItem("购物车", R.drawable.shop, ShopFragment.class)
                .addTabItem("我的", R.drawable.my, MyFragment.class)
                .setTabBarBackgroundColor(Color.WHITE)
                .isShowDivider(false);
    }




}
