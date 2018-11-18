package com.bwie.fjd.presenter;

import com.bwie.fjd.model.bean.SouBean;
import com.bwie.fjd.model.http.HttpUtils;
import com.bwie.fjd.view.iview.SouView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SouPresenter extends BasePresenter<SouView> {
     public void SouloadDataFromNet(String keyword,int page){
         Observable<SouBean> sousuo = HttpUtils.getdatanet().api.sousuo(keyword, page);
         sousuo.subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Observer<SouBean>() {
                     @Override
                     public void onSubscribe(Disposable d) {

                     }

                     @Override
                     public void onNext(SouBean souBean) {
                        getView().onSuccess(souBean);
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
