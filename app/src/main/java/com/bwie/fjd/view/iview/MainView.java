package com.bwie.fjd.view.iview;

import com.bwie.fjd.model.bean.FenBean;
import com.bwie.fjd.model.bean.HomeBean;

public interface MainView extends BaseView{
    void onSuccess(HomeBean.DataBean homeBean);
    void onYouSuccess(FenBean fenBean);
    void onerror(String msg);
}
