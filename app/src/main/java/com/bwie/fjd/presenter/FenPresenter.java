package com.bwie.fjd.presenter;

import com.bwie.fjd.model.bean.FenBean;
import com.bwie.fjd.model.http.HttpUtils;
import com.bwie.fjd.view.iview.MainView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FenPresenter extends BasePresenter<MainView>{
    public  void fenData(String cid){
        Observable<FenBean> data = HttpUtils.getdatanet().api.fenData(cid);
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FenBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FenBean fenBean) {
                      getView().onYouSuccess(fenBean);
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
