package com.bwie.fjd.presenter;

import com.bwie.fjd.model.LoginModel;
import com.bwie.fjd.model.bean.LoginBean;
import com.bwie.fjd.model.bean.RegisBean;
import com.bwie.fjd.view.iview.LoginView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginView>{

    private final LoginModel loginModel;

    public LoginPresenter() {
        loginModel = new LoginModel();
    }
    public void loginData(String mobile,String password){
        loginModel.getData(mobile, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                         getView().onLoginSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void regData(String mobile,String password){
        loginModel.getReg(mobile, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<RegisBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisBean regisBean) {
                        getView().onRegSuccess(regisBean);
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
