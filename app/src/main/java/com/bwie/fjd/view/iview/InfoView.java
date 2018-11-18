package com.bwie.fjd.view.iview;

import com.bwie.fjd.model.bean.GoodsInfoBean;

public interface InfoView extends BaseView{
    void onSuccess(GoodsInfoBean goodsInfoBean);
    void onError(String msg);

}
