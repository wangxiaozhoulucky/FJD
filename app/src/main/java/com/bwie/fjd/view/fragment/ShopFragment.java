package com.bwie.fjd.view.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.CarBean;
import com.bwie.fjd.model.bean.bean1;
import com.bwie.fjd.presenter.CarPresenter;
import com.bwie.fjd.view.adapter.CarAdapter;
import com.bwie.fjd.view.iview.CarView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
public class ShopFragment extends Fragment implements CarView {
    @BindView(R.id.rec_car)
    RecyclerView recCar;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.zj)
    TextView zj;
    Unbinder unbinder;
    private CarPresenter carPresenter;
    private SharedPreferences sp;
    private String uid;
    private String token;
    private CarAdapter adapter;
    private List<CarBean.DataBean> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        EventBus.getDefault().register(this);//注册eventbus–
        carPresenter = new CarPresenter();
        carPresenter.attachView(this);
        unbinder = ButterKnife.bind(this, view);
        sp = getActivity().getSharedPreferences("flag", Context.MODE_PRIVATE);
        uid = sp.getString("uid", "1");
        token = sp.getString("token", "");
        carPresenter.getCar(uid,token);
        return view;
    }
    @Override
    public void onSuccesss(final CarBean carBean) {
         data = carBean.getData();
        if (data!=null&&!"1".equals(uid)){
            recCar.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            adapter = new CarAdapter(getActivity(),data);
            recCar.setAdapter(adapter);
            //全选 来控制商家跟条目
            adapter.setOnclickchangelisten(new CarAdapter.onclickchangelisten() {
                @Override
                public void onchecked(int layoutPosition, boolean checked) {
                    boolean b=true;
                    for (int i = 0; i < carBean.getData().size(); i++) {
                        boolean outchecked = carBean.getData().get(i).isOutchecked();
                        for (int j = 0; j < carBean.getData().get(i).getList().size(); j++) {
                            boolean innerchecked = carBean.getData().get(i).getList().get(j).isInnerchecked();
                            b=(b&outchecked&innerchecked);
                        }
                    }
                    checkbox.setChecked(b);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onitemchecked(int layoutPosition, boolean ischecked) {
                    //设置外层的选中状态
                    carBean.getData().get(layoutPosition).setOutchecked(ischecked);
                    boolean b=true;
                    for (int i = 0; i < carBean.getData().size(); i++) {
                        boolean outchecked = carBean.getData().get(i).isOutchecked();
                        for (int j = 0; j < carBean.getData().get(i).getList().size(); j++) {
                            boolean innerchecked = carBean.getData().get(i).getList().get(j).isInnerchecked();
                            b=(b&outchecked&innerchecked);
                        }
                    }
                    checkbox.setChecked(b);
                    adapter.notifyDataSetChanged();
                }
            });
        }
        //全选的点击按钮
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked()){
                    for (int i = 0; i < carBean.getData().size(); i++) {
                        carBean.getData().get(i).setOutchecked(true);
                        for (int j = 0; j < carBean.getData().get(i).getList().size(); j++) {
                            carBean.getData().get(i).getList().get(j).setInnerchecked(true);
                        }
                    }
                } else {
                    for (int i = 0; i < carBean.getData().size(); i++) {
                        carBean.getData().get(i).setOutchecked(false);
                        for (int j = 0; j < carBean.getData().get(i).getList().size(); j++) {
                            carBean.getData().get(i).getList().get(j).setInnerchecked(false);
                        }
                    }
                }
                initzong();
                adapter.notifyDataSetChanged();
            }


        });

    }
    @Override
    public void onErrorr(Throwable t) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        carPresenter.datachView();
        EventBus.getDefault().unregister(this);//解注
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  msg(bean1 ha){
        initzong();
    }

    private void initzong() {
        int zong=0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).getList().size(); j++) {
                if (data.get(i).getList().get(j).isInnerchecked()){
                    zong+=data.get(i).getList().get(j).getNum()*data.get(i).getList().get(j).getPrice();
                }
            }
        }
        zj.setText(zong+"元");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        carPresenter.datachView();
    }
    @Override
    public void onResume() {
        super.onResume();
            carPresenter.getCar(uid,token);
    }

}
