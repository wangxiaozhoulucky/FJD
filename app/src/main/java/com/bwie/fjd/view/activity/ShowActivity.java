package com.bwie.fjd.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.SouBean;
import com.bwie.fjd.model.bean.XiaoBean;
import com.bwie.fjd.presenter.SouPresenter;
import com.bwie.fjd.presenter.XiaoPresenter;
import com.bwie.fjd.view.adapter.SouAdapter;
import com.bwie.fjd.view.adapter.SouSuoAdapter;
import com.bwie.fjd.view.iview.SouView;
import com.bwie.fjd.view.iview.XiaoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowActivity extends AppCompatActivity implements SouView{

    @BindView(R.id.sao)
    ImageView sao;
    @BindView(R.id.sou)
    ImageView sousouImageview;
    @BindView(R.id.edit_sou)
    EditText sousuoEdittext;
    @BindView(R.id.show_recyclerView)
    RecyclerView showRecyclerView;
    @BindView(R.id.moren)
    ImageView moren;
    @BindView(R.id.jiage)
    ImageView jiage;
    @BindView(R.id.xiaoliang)
    ImageView xiaoliang;
    private SouPresenter souPresenter;
    private String string;
    private String string1;
    private List<SouBean.DataBean> data;
    private SouSuoAdapter souAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);
        souPresenter = new SouPresenter();
        souPresenter.attachView(this);
        string = getIntent().getStringExtra("sousuo");
        souPresenter.SouloadDataFromNet(string, 1);
        showRecyclerView.setLayoutManager(new LinearLayoutManager(ShowActivity.this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onSuccess(SouBean souBean) {
        data = souBean.getData();
        SouSuoAdapter souSuoAdapter = new SouSuoAdapter(ShowActivity.this, data);
        showRecyclerView.setAdapter(souSuoAdapter);
    }

    @OnClick(R.id.sou)
    public void onViewClicked() {
        string1 = sousuoEdittext.getText().toString();
        if (string1!=null){
            souPresenter.SouloadDataFromNet(string1, 1);
        }else {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        }

    }



}
