package com.bwie.fjd.model;

import com.bwie.fjd.model.bean.JiaBean;
import com.bwie.fjd.model.http.HttpUtils;

import io.reactivex.Observable;

public class JiaModel {
    public Observable<JiaBean> jiaCar(String uid,String pid){
        Observable<JiaBean> jiaCar = HttpUtils.getdatanet().api.jiaCar(uid, pid);
        return jiaCar;
    }
}
