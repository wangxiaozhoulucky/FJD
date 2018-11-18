package com.bwie.fjd.view.iview;

import com.bwie.fjd.model.bean.CarBean;

public interface CarView extends BaseView{
    void onSuccesss(CarBean carBean);
    void onErrorr(Throwable t);
}
