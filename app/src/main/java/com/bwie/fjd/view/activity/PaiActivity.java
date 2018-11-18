package com.bwie.fjd.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.XiaoBean;
import com.bwie.fjd.presenter.XiaoPresenter;
import com.bwie.fjd.view.adapter.SouAdapter;
import com.bwie.fjd.view.iview.XiaoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaiActivity extends AppCompatActivity implements XiaoView {

    @BindView(R.id.moren)
    ImageView moren;
    @BindView(R.id.jiage)
    ImageView jiage;
    @BindView(R.id.xiaoliang)
    ImageView xiaoliang;
    @BindView(R.id.show_recyclerView)
    RecyclerView showRecyclerView;
    private XiaoPresenter xiaoPresenter;
    private List<XiaoBean.DataBean> data;
    private SouAdapter souAdapter;
    private String pscid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pai);
        ButterKnife.bind(this);
        xiaoPresenter = new XiaoPresenter();
        xiaoPresenter.attachView(this);
        pscid = getIntent().getStringExtra("pscid");
        xiaoPresenter.paiXu(pscid, "0");
        showRecyclerView.setLayoutManager(new LinearLayoutManager(PaiActivity.this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onSuccesss(XiaoBean xiaoBean) {
        data = xiaoBean.getData();
        souAdapter = new SouAdapter(PaiActivity.this, data);
        showRecyclerView.setAdapter(souAdapter);
    }

    @Override
    public void onerrorr(XiaoBean xiaoBean) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        xiaoPresenter.datachView();
    }

    @OnClick({R.id.moren, R.id.jiage, R.id.xiaoliang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.moren:
                xiaoPresenter.paiXu(pscid, "0");
                break;
            case R.id.jiage:
                xiaoPresenter.paiXu(pscid, "2");
                break;
            case R.id.xiaoliang:
                xiaoPresenter.paiXu(pscid, "1");
                break;
        }
    }

}
