package com.bwie.fjd.model;

import com.bwie.fjd.model.bean.LoginBean;
import com.bwie.fjd.model.bean.RegisBean;
import com.bwie.fjd.model.http.HttpUtils;

import io.reactivex.Observable;

public class LoginModel {
    public Observable<LoginBean> getData(String mobile,String password){
        Observable<LoginBean> data = HttpUtils.getdatanet().api.loginData(mobile, password);
        return data;
    }
    public Observable<RegisBean> getReg(String mobile,String password){
        Observable<RegisBean> data = HttpUtils.getdatanet().api.RegData(mobile, password);
        return data;
    }
}
