package com.bwie.fjd.presenter;

import com.bwie.fjd.model.MainModel;
import com.bwie.fjd.model.bean.HomeBean;
import com.bwie.fjd.model.http.HttpUtils;
import com.bwie.fjd.view.iview.MainView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainView>{

    private final MainModel mainModel;

    public MainPresenter() {
        mainModel = new MainModel();
    }

    public void loadData(){
        mainModel.getData()
      .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeBean homeBean) {
                        HomeBean.DataBean data = homeBean.getData();
//                        getView().onSuccess(data);

                        if (homeBean != null & "0".equals(homeBean.getCode())) {
                            if (getView() != null)
                                getView().onSuccess(data);
                            return;
                        }
                        if (getView() != null)
                            getView().onerror("服务未响应");
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
