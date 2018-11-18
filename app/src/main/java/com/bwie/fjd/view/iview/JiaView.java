package com.bwie.fjd.view.iview;

import com.bwie.fjd.model.bean.JiaBean;

public interface JiaView extends BaseView{
    void onJiaSucces(JiaBean jiaBean);
    void onJiaErrorr(Throwable t);
}
