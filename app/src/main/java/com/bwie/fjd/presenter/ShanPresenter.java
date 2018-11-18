package com.bwie.fjd.presenter;

import com.bwie.fjd.model.JiaModel;
import com.bwie.fjd.model.ShanModel;
import com.bwie.fjd.model.bean.JiaBean;
import com.bwie.fjd.model.bean.ShanBean;
import com.bwie.fjd.view.iview.JiaView;
import com.bwie.fjd.view.iview.ShanView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShanPresenter extends BasePresenter<ShanView> {

    private final ShanModel shanModel;

    public ShanPresenter() {
        shanModel = new ShanModel();
    }
    public void shanCart(String uid,String pid){
        shanModel.shanCar(uid, pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShanBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShanBean shanBean) {
                          if (shanBean!=null){
                              getView().onSucces(shanBean);
                          }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
