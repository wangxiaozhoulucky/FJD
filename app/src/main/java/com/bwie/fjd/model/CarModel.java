package com.bwie.fjd.model;

import com.bwie.fjd.model.bean.CarBean;
import com.bwie.fjd.model.http.HttpUtils;

import io.reactivex.Observable;

public class CarModel {
    public Observable<CarBean> getCarData(String uid,String token){
        Observable<CarBean> gets = HttpUtils.getdatanet().api.gets(uid,token);
        return gets;
    }
}
