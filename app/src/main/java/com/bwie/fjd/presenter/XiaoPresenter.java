package com.bwie.fjd.presenter;

import com.bwie.fjd.model.bean.SouBean;
import com.bwie.fjd.model.bean.XiaoBean;
import com.bwie.fjd.model.http.HttpUtils;
import com.bwie.fjd.view.iview.SouView;
import com.bwie.fjd.view.iview.XiaoView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class XiaoPresenter extends BasePresenter<XiaoView> {
     public void paiXu(String pscid,String sort){
         Observable<XiaoBean> sousuo = HttpUtils.getdatanet().api.xiao(pscid, sort);
         sousuo.subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Observer<XiaoBean>() {
                     @Override
                     public void onSubscribe(Disposable d) {

                     }

                     @Override
                     public void onNext(XiaoBean xiaoBean) {
                        getView().onSuccesss(xiaoBean);
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
