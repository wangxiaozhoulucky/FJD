package com.bwie.fjd.view.iview;

import com.bwie.fjd.model.bean.ShanBean;

public interface ShanView extends BaseView{
    void onSucces(ShanBean shanBean);
    void onerre(ShanBean shanBean);
}
