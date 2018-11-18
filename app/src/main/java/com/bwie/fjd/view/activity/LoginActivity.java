package com.bwie.fjd.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.LoginBean;
import com.bwie.fjd.model.bean.RegisBean;
import com.bwie.fjd.presenter.LoginPresenter;
import com.bwie.fjd.view.iview.LoginView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {
    @BindView(R.id.ck_pwd)
    CheckBox ckPwd;
    @BindView(R.id.ck_login)
    CheckBox ckLogin;
    private UMShareAPI umShareAPI;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.qq)
    ImageView qq;
    @BindView(R.id.btn_reg)
    TextView btnReg;
    private LoginPresenter loginPresenter;
    private String name1;
    private String iconurl;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        umShareAPI = UMShareAPI.get(this);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);
        //自动登陆
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        ziDong();
    }

    private void ziDong() {
        //判断记住密码多选框的状态
        if (sp.getBoolean("ISCHECK", false)) {
            //设置默认是记录密码状态
            ckPwd.setChecked(true);
            editName.setText(sp.getString("USER_NAME", ""));
            editPwd.setText(sp.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if (sp.getBoolean("AUTO_ISCHECK", false)) {
                //设置默认是自动登录状态
                ckLogin.setChecked(false);
                //跳转界面
//                Intent intent = new Intent(LoginActivity.this, GeRenActivity.class);
//                LoginActivity.this.startActivity(intent);
                finish();
            }
        }

    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
       // Toast.makeText(this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
        String code = loginBean.getCode();
        String msg = loginBean.getMsg();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        if (code.equals("0")) {
            String mobile = loginBean.getData().getMobile();
            String uid = String.valueOf(loginBean.getData().getUid());
            String token = loginBean.getData().getToken();
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            sp = getSharedPreferences("flag", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("mobile", mobile);
            editor.putString("uid", uid);
            editor.putString("token", token);
            editor.commit();
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("mobile",mobile);
            intent.putExtras(bundle);
            setResult(1, intent);
            finish();
        }
    }

    @Override
    public void onRegSuccess(RegisBean regisBean) {

    }

    @Override
    public void onError(LoginBean loginBean) {
        Toast.makeText(this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorReg(RegisBean regisBean) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.datachView();
    }

    @OnClick({R.id.btn_login, R.id.qq, R.id.btn_reg,R.id.ck_pwd, R.id.ck_login})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_login:
                String name = editName.getText().toString();
                String password = editPwd.getText().toString();
                if (ckPwd.isChecked()) {
                    //记住用户名、密码、
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("USER_NAME", name);
                    editor.putString("PASSWORD", password);
                    editor.commit();
                }

                loginPresenter.loginData(name, password);
                break;
            case R.id.ck_pwd:
                if (ckPwd.isChecked()) {
                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                } else {
                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();
                }

                break;
            case R.id.ck_login:
                if (ckLogin.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }

                break;
            case R.id.qq:
                Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show();
                UMAuthListener authListener = new UMAuthListener() {
                    /**
                     * @param platform 平台名称
                     * @desc 授权开始的回调
                     */
                    @Override
                    public void onStart(SHARE_MEDIA platform) {

                    }

                    /**
                     * @param platform 平台名称
                     * @param action   行为序号，开发者用不上
                     * @param data     用户资料返回
                     * @desc 授权成功的回调
                     */
                    @Override
                    public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
                        name1 = data.get("name");
                        iconurl = data.get("iconurl");
//                        String uid01 = data.get("uid");
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putString("uid",uid01);
//                        editor.commit();

                        Intent intent2 = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("name", name1);
                        bundle.putString("iconurl", iconurl);
                        intent2.putExtras(bundle);
                        setResult(888, intent2);
                        finish();
                    }

                    /**
                     * @param platform 平台名称
                     * @param action   行为序号，开发者用不上
                     * @param t        错误原因
                     * @desc 授权失败的回调
                     */
                    @Override
                    public void onError(SHARE_MEDIA platform, int action, Throwable t) {

                        Toast.makeText(LoginActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    /**
                     * @param platform 平台名称
                     * @param action   行为序号，开发者用不上
                     * @desc 授权取消的回调
                     */
                    @Override
                    public void onCancel(SHARE_MEDIA platform, int action) {
                        Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
                    }
                };
                umShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, authListener);
                break;

            case R.id.btn_reg:
                Intent intent1 = new Intent(LoginActivity.this, RegisActivity.class);
                startActivity(intent1);
                finish();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


}
