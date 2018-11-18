package com.bwie.fjd.presenter;

import com.bwie.fjd.model.JiaModel;
import com.bwie.fjd.model.bean.JiaBean;
import com.bwie.fjd.view.iview.JiaView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JiaPresenter extends BasePresenter<JiaView> {

    private final JiaModel jiaModel;

    public JiaPresenter() {
        jiaModel = new JiaModel();
    }
    public void jiaCart(String uid,String pid){
        jiaModel.jiaCar(uid, pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JiaBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JiaBean jiaBean) {
                          if (jiaBean!=null){
                              getView().onJiaSucces(jiaBean);
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
