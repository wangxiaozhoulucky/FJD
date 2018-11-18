package com.bwie.fjd.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bwie.fjd.R;

public class QiDongActivity extends AppCompatActivity {
    private int time=3;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                if (time>0){
                    time--;
                    // text_time.setText(time+"s");
                    handler.sendEmptyMessageDelayed(0,1000);
                }else {
                    Intent intent = new Intent(QiDongActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qi_dong);
        handler.sendEmptyMessageDelayed(0,1000);
    }
}
