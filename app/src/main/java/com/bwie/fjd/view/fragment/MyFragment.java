package com.bwie.fjd.view.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.FenBean;
import com.bwie.fjd.model.bean.HomeBean;
import com.bwie.fjd.presenter.MainPresenter;
import com.bwie.fjd.view.activity.GeRenActivity;
import com.bwie.fjd.view.activity.LoginActivity;
import com.bwie.fjd.view.adapter.TuiAdapter;
import com.bwie.fjd.view.iview.MainView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyFragment extends Fragment implements MainView {
    @BindView(R.id.img)
    SimpleDraweeView img;
    @BindView(R.id.deng)
    TextView deng;
    @BindView(R.id.rv_product_my)
    RecyclerView rvProductMy;
    Unbinder unbinder;
    @BindView(R.id.img_set)
    ImageView imgSet;
    private MainPresenter mainPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        mainPresenter.loadData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.img, R.id.deng,R.id.img_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, 999);
                break;
            case R.id.deng:
                break;
            case R.id.img_set:
                Intent intent2 = new Intent(getActivity(), GeRenActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 999 && resultCode == 1) {
                String mobile = data.getExtras().getString("mobile");
                deng.setText(mobile);
            } else {
                if (requestCode == 999 && resultCode == 888) {
                String name = data.getExtras().getString("name");
                String iconurl = data.getExtras().getString("iconurl");
                deng.setText(name);
                Uri parse = Uri.parse(iconurl);
                img.setImageURI(parse);
            }
        }
    }

    @Override
    public void onSuccess(HomeBean.DataBean data) {
        List<HomeBean.DataBean.TuijianBean.ListBeanX> list = data.getTuijian().getList();
        GridLayoutManager manager03 = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rvProductMy.setLayoutManager(manager03);
        TuiAdapter adapter03 = new TuiAdapter(getActivity(), list);
        rvProductMy.setAdapter(adapter03);
    }

    @Override
    public void onYouSuccess(FenBean fenBean) {

    }

    @Override
    public void onerror(String msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
