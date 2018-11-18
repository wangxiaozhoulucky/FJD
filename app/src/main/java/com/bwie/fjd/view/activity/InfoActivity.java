package com.bwie.fjd.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.GoodsInfoBean;
import com.bwie.fjd.model.bean.HomeBean;
import com.bwie.fjd.model.bean.JiaBean;
import com.bwie.fjd.presenter.InfoPresnetr;
import com.bwie.fjd.presenter.JiaPresenter;
import com.bwie.fjd.view.adapter.DetailsAdapter;
import com.bwie.fjd.view.iview.InfoView;
import com.bwie.fjd.view.iview.JiaView;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoActivity extends AppCompatActivity implements InfoView, JiaView {
    @BindView(R.id.text_fen)
    TextView textFen;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.jia)
    TextView jia;
    @BindView(R.id.flybanner)
    ViewPager viewpager;
    @BindView(R.id.text_price)
    TextView textPrice;
    private InfoPresnetr infoPresnetr;
    private JiaPresenter jiaPresenter;
    private int pid;
    private SharedPreferences sp;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        infoPresnetr = new InfoPresnetr();
        infoPresnetr.attachView(this);
        pid = getIntent().getIntExtra("pid", 1);
        Log.e("kkk",pid+"");
        infoPresnetr.loadData(pid);

        //加入购物车
        sp = getSharedPreferences("flag", Context.MODE_PRIVATE);
        uid = sp.getString("uid", "1");
        jiaPresenter = new JiaPresenter();
        jiaPresenter.attachView(this);

    }

    @Override
    public void onSuccess(GoodsInfoBean goodsInfoBean) {
        textPrice.setText( "￥"+goodsInfoBean.getData().getPrice() );
        textFen.setText("￥"+goodsInfoBean.getData().getBargainPrice());
       // textPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        textPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        textTitle.setText(goodsInfoBean.getData().getTitle());
        String images = goodsInfoBean.getData().getImages();
        String[] split = images.split("\\|");
        /**
         * 将图片做成无限轮播
         * 配置适配器
         */
        DetailsAdapter adapter = new DetailsAdapter(InfoActivity.this, split);
        viewpager.setAdapter(adapter);

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        infoPresnetr.datachView();
    }

    @OnClick({ R.id.jia})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jia:
                if (uid!=null&&!"1".equals(uid)){
                    jiaPresenter.jiaCart(uid, String.valueOf(pid));
                }else {
                    Toast.makeText(this,"请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onJiaSucces(JiaBean jiaBean) {
        if (jiaBean.getMsg().equals("加购成功")) {
                Toast.makeText(this, jiaBean.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onJiaErrorr(Throwable t) {
        Toast.makeText(this, t + "", Toast.LENGTH_SHORT).show();
    }


}
