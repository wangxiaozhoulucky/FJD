package com.bwie.fjd.view.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.FenBean;
import com.bwie.fjd.model.bean.HomeBean;
import com.bwie.fjd.presenter.MainPresenter;
import com.bwie.fjd.view.activity.SouActivity;
import com.bwie.fjd.view.adapter.JiuAdapter;
import com.bwie.fjd.view.adapter.MiaoAdapter;
import com.bwie.fjd.view.adapter.TuiAdapter;
import com.bwie.fjd.view.iview.MainView;
import com.recker.flybanner.FlyBanner;
import com.sunfusheng.marqueeview.MarqueeView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements MainView {
    private static final int REQUEST_CODE = 200;
    @BindView(R.id.fly_banner)
    FlyBanner flyBanner;
    Unbinder unbinder;
    @BindView(R.id.rec_jiu)
    RecyclerView recJiu;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_minute)
    TextView tvMinute;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.dian)
    TextView dian;
    @BindView(R.id.miaosha)
    RecyclerView miaoshas;
    @BindView(R.id.tjRec)
    RecyclerView tjRec;
    @BindView(R.id.sao)
    ImageView sao;
    @BindView(R.id.sou)
    ImageView sou;
    @BindView(R.id.edit_sou)
    EditText editSou;
    private int h;
    private MainPresenter mainPresenter;
    private List<String> images = new ArrayList<>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Calendar instance = Calendar.getInstance();
                int hour = instance.get(instance.HOUR_OF_DAY);
                int tohour = 2;
                if (hour % 2 == 0) {
                    h = hour + tohour;
                } else {
                    h = hour - 1 + tohour;
                }
                int minute = instance.get(Calendar.MINUTE);
                int second = instance.get(Calendar.SECOND);
//        计算时差
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date d1 = simpleDateFormat.parse(h + ":00:00");
                    Date d2 = simpleDateFormat.parse(hour + ":" + minute + ":" + second);
                    //Date   d2 = new   Date(System.currentTimeMillis());//你也可以获取当前时间
                    long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
                    long hours = (diff / (1000 * 60 * 60));
                    long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
                    long seconds = (diff - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
                    dian.setText(h + "点场");
                    tvHour.setText(hours + "");
                    tvMinute.setText(minutes + "");
                    tvSecond.setText(seconds + "");
                } catch (Exception e) {
                }
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        mainPresenter.loadData();
        unbinder = ButterKnife.bind(this, view);
        handler.sendEmptyMessageDelayed(0, 1000);
        //跑马灯
        paomadeng();
        editSou.setCursorVisible(false);
        editSou.setFocusable(false);
        editSou.setFocusableInTouchMode(false);
        return view;
    }

    private void paomadeng() {
        List<String> info = new ArrayList<>();
        info.add("朱峰大促销下单拆福袋，亿万新年红包随便拿");
        info.add("朱峰家电五折团，抢十亿无门槛现金红红包！");
        info.add("3. GitHub帐号红包：sfsheng0322红包红包?");
        info.add("星球大战朱峰剃须刀首发送200元代金券红包.");
        info.add("5. 个人博客：sunfusheng.com红包红包红包");
        info.add("6. 微信公众号：孙福生红包红包红包红包红.");
        marqueeView.startWithList(info);
    }


    @Override
    public void onSuccess(HomeBean.DataBean data) {
        //轮播图
        List<HomeBean.DataBean.BannerBean> banner = data.getBanner();
        for (int i = 0; i < data.getBanner().size(); i++) {
            //    Log.e("aaa", data.getBanner().get(i).getTitle());
            String replace = data.getBanner().get(i).getIcon().replace("https", "http");
            images.add(replace.split("\\|")[0]);
        }
        flyBanner.setImagesUrl(images);

        //九宫格
        List<HomeBean.DataBean.FenleiBean> fenlei = data.getFenlei();
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        recJiu.setLayoutManager(manager);
        JiuAdapter adapter = new JiuAdapter(getActivity(), fenlei);
        recJiu.setAdapter(adapter);
        //秒杀
        HomeBean.DataBean.MiaoshaBean miaosha = data.getMiaosha();
        GridLayoutManager manager02 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        miaoshas.setLayoutManager(manager02);
        MiaoAdapter adapter02 = new MiaoAdapter(getActivity(), miaosha);
        miaoshas.setAdapter(adapter02);
        //为你推荐
        List<HomeBean.DataBean.TuijianBean.ListBeanX> list = data.getTuijian().getList();
        GridLayoutManager manager03 = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        tjRec.setLayoutManager(manager03);
        TuiAdapter adapter03 = new TuiAdapter(getActivity(), list);
        tjRec.setAdapter(adapter03);
    }

    @Override
    public void onYouSuccess(FenBean fenBean) {

    }

    @Override
    public void onerror(String msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainPresenter.datachView();
        unbinder.unbind();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    @OnClick({R.id.sao,R.id.edit_sou})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sao:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.edit_sou:
                Intent intent2 = new Intent(getActivity(), SouActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
