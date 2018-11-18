package com.bwie.fjd.view.iview;

import com.bwie.fjd.model.bean.XiaoBean;

public interface XiaoView extends BaseView{
    void onSuccesss(XiaoBean xiaoBean);
    void onerrorr(XiaoBean xiaoBean);
}
