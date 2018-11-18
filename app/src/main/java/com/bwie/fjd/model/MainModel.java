package com.bwie.fjd.model;

import com.bwie.fjd.model.bean.HomeBean;
import com.bwie.fjd.model.http.HttpUtils;

import io.reactivex.Observable;

public class MainModel {
    public Observable<HomeBean> getData(){
        Observable<HomeBean> shouye = HttpUtils.getdatanet().api.shouye();
        return shouye;
    }
}
