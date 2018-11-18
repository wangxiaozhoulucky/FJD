package com.bwie.fjd.view.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.GeRenBean;
import com.bwie.fjd.model.http.HttpUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GeRenActivity extends AppCompatActivity {
    @BindView(R.id.xx_img)
    SimpleDraweeView xxImg;
    @BindView(R.id.xx_name)
    TextView xxName;
    @BindView(R.id.xx_nc)
    TextView xxNc;
    @BindView(R.id.tc_dl)
    Button tcDl;
    @BindView(R.id.xx_dizhi)
    TextView xxDizhi;
    @BindView(R.id.xx_riqi)
    TextView xxRiqi;
    private String uid;
    private SharedPreferences sp;
    private PopupWindow window;
    String path = Environment.getExternalStorageDirectory() + "/head.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge_ren);
        ButterKnife.bind(this);
        sp = GeRenActivity.this.getSharedPreferences("flag", Context.MODE_PRIVATE);
        uid = sp.getString("uid", "1");
        Toast.makeText(this, ""+uid, Toast.LENGTH_SHORT).show();
        if (uid!= null&&!"1".equals(uid)) {
            //获取数据
            Observable<GeRenBean> user = HttpUtils.getdatanet().api.getUser(String.valueOf(uid));
            user.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<GeRenBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(GeRenBean geRenBean) {
                            GeRenBean.DataBean data = geRenBean.getData();
                            if (data != null) {
                                //获取数据 为控件进行赋值
                                Glide.with(GeRenActivity.this).load(data.getIcon()).into(xxImg);
                                xxName.setText(data.getUsername());
                                //  xxNc.setText((Integer) data.getNickname());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }


    @OnClick({R.id.xx_img, R.id.tc_dl,R.id.xx_dizhi, R.id.xx_riqi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xx_img:
                //得到更换头像的popwindow
                showPopwindow();
                break;
            case R.id.tc_dl:
                //点击退出登录 回到登录页面
                Intent intent = new Intent(GeRenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.xx_dizhi:
                break;
            case R.id.xx_riqi:
                new DatePickerDialog(GeRenActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        xxRiqi.setText(String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
                    }
                }, 2000, 1, 2).show();

                break;
        }
    }

    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pubwindow, null);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        // 在底部显示
        window.showAtLocation(findViewById(R.id.xx_img), Gravity.CENTER_VERTICAL, 0, 0);
        // 这里检验popWindow里的button是否可以点击
        Button first = view.findViewById(R.id.first);//相机
        Button third = view.findViewById(R.id.third);//取消
        Button second = view.findViewById(R.id.second);//相册
        //相机
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这个出捕获图片的常量值
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 设置图片输出位置; 输出到制定的uri路径上;
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                // 设置请求码
                startActivityForResult(intent, 100);
                window.dismiss();
            }
        });

        //相册
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 101);
                window.dismiss();
            }
        });

        //取消
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GeRenActivity.this, "取消", Toast.LENGTH_SHORT).show();
                window.dismiss();
            }
        });
        // popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
             finish();
    }
}
