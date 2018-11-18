package com.bwie.fjd.presenter;

import com.bwie.fjd.view.iview.BaseView;

public class BasePresenter<V extends BaseView>{
    private V iv;

    public void attachView(V iv) {
        this.iv = iv;
    }

    public void datachView() {
        this.iv =null;
    }
    public V getView() {
        return iv;
    }
}
