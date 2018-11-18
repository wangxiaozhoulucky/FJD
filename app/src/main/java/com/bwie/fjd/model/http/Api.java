package com.bwie.fjd.model.http;
import com.bwie.fjd.model.bean.CarBean;
import com.bwie.fjd.model.bean.FenBean;
import com.bwie.fjd.model.bean.GeRenBean;
import com.bwie.fjd.model.bean.GoodsInfoBean;
import com.bwie.fjd.model.bean.HomeBean;
import com.bwie.fjd.model.bean.JiaBean;
import com.bwie.fjd.model.bean.LoginBean;
import com.bwie.fjd.model.bean.RegisBean;
import com.bwie.fjd.model.bean.ShanBean;
import com.bwie.fjd.model.bean.SouBean;
import com.bwie.fjd.model.bean.XiaoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    //首页
    @GET("home/getHome")
    Observable<HomeBean> shouye();

    //搜索
    @GET("product/searchProducts")
    Observable<SouBean> sousuo(@Query("keywords") String keywords, @Query("page") int page);

    //详情
    @GET("product/getProductDetail")
    Observable<GoodsInfoBean> queryGoodsByPid(@Query("pid") int pid);

    //右分类
    @GET("product/getProductCatagory")
    Observable<FenBean> fenData(@Query("cid") String cid);

    //登陆
    @GET("user/login")
    Observable<LoginBean> loginData(@Query("mobile") String mobile, @Query("password") String password);

    //注册
    @GET("user/reg")
    Observable<RegisBean> RegData(@Query("mobile") String mobile, @Query("password") String password);

    //个人中心
    @GET("user/getUserInfo")
    Observable<GeRenBean> getUser(@Query("uid") String uid);

    //购物车
    @GET("product/getCarts")
    Observable<CarBean> gets(@Query("uid") String uid,@Query("token") String token);
    //加入购物车
    @GET("product/addCart")
    Observable<JiaBean> jiaCar(@Query("uid") String uid,@Query("pid") String pid);
    //销量价格
    @GET("product/getProducts")
    Observable<XiaoBean> xiao(@Query("pscid") String pscid, @Query("sort") String sort);
    //删除购物车
    @GET("product/deleteCart")
    Observable<ShanBean> shan(@Query("uid") String uid, @Query("pid") String pid);
}
