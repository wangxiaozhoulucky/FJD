package com.bwie.fjd.view.iview;

import com.bwie.fjd.model.bean.LoginBean;
import com.bwie.fjd.model.bean.RegisBean;

public interface LoginView extends BaseView{
    void onLoginSuccess(LoginBean loginBean);
    void onRegSuccess(RegisBean regisBean);
    void onError(LoginBean loginBean);
    void onErrorReg(RegisBean regisBean);
}
