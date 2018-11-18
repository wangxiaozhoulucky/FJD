package com.bwie.fjd.presenter;

import com.bwie.fjd.model.bean.GoodsInfoBean;
import com.bwie.fjd.model.http.HttpUtils;
import com.bwie.fjd.view.iview.InfoView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InfoPresnetr extends BasePresenter<InfoView> {
    public void loadData(int pid){
        HttpUtils.getdatanet().api.queryGoodsByPid(pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GoodsInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GoodsInfoBean goodsInfoBean) {
                       getView().onSuccess(goodsInfoBean);
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
