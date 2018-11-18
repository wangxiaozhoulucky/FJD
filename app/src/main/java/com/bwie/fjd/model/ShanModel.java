package com.bwie.fjd.model;
import com.bwie.fjd.model.bean.ShanBean;
import com.bwie.fjd.model.http.HttpUtils;
import io.reactivex.Observable;
public class ShanModel {
    public Observable<ShanBean> shanCar(String uid, String pid){
        Observable<ShanBean> shancar = HttpUtils.getdatanet().api.shan(uid, pid);
        return shancar;
    }
}
