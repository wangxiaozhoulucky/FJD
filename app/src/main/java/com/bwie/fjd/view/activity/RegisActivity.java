package com.bwie.fjd.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.LoginBean;
import com.bwie.fjd.model.bean.RegisBean;
import com.bwie.fjd.presenter.LoginPresenter;
import com.bwie.fjd.view.iview.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.btn_reg)
    Button btnReg;
    @BindView(R.id.text_login)
    TextView textLogin;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);
    }

    @OnClick({R.id.btn_reg, R.id.text_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reg:
                String name = editName.getText().toString();
                String password = editPwd.getText().toString();
                loginPresenter.regData(name,password);

                break;
            case R.id.text_login:
                Intent intent1=new Intent(RegisActivity.this,LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {

    }

    @Override
    public void onRegSuccess(RegisBean regisBean) {
        Toast.makeText(this, regisBean.getMsg(), Toast.LENGTH_SHORT).show();
        if (regisBean.getMsg().equals("注册成功")){
            Intent intent1=new Intent(RegisActivity.this,LoginActivity.class);
            startActivity(intent1);
            finish();
        }
    }

    @Override
    public void onError(LoginBean loginBean) {

    }

    @Override
    public void onErrorReg(RegisBean regisBean) {
        Toast.makeText(this,regisBean.getMsg(), Toast.LENGTH_SHORT).show();
    }
}
