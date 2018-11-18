package com.bwie.fjd.presenter;

import com.bwie.fjd.model.CarModel;
import com.bwie.fjd.model.bean.CarBean;
import com.bwie.fjd.view.iview.CarView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CarPresenter extends BasePresenter<CarView>{

    private final CarModel carModel;

    public CarPresenter() {
        carModel = new CarModel();
    }
    public void getCar(String uid,String token){
        carModel.getCarData(uid,token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CarBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CarBean carBean) {
                          if (carBean!=null){
                              getView().onSuccesss(carBean);
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
