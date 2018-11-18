package com.bwie.fjd.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.fjd.R;

public class button extends LinearLayout implements View.OnClickListener {
    private Button jia;
    private Button jian;
    private TextView text_num;

    public button(Context context) {
        super(context);

    }

    public button(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.jia,this);
        jia = findViewById(R.id.jia);
        jian = findViewById(R.id.jian);
        text_num = findViewById(R.id.text_num);

        jia.setOnClickListener(this);
        jian.setOnClickListener(this);
    }

    public button(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View v) {
        String s = text_num.getText().toString();
        int i = Integer.parseInt(s);
        switch (v.getId()){
            case R.id.jia:
                if (addAndMinusu!=null){
                    addAndMinusu.add();
                }
                break;
            case R.id.jian:
                if (i>1){
                    if (addAndMinusu!=null){
                        addAndMinusu.minus();
                    }
                } else if (i<=1) {
                    Toast.makeText(v.getContext(), "商品必须大于1", Toast.LENGTH_SHORT).show();
                }
                
                break;
        }
    }

    //定义的接口回调
    private   AddAndMinus addAndMinusu;
    public interface AddAndMinus{
        void add();
        void minus();
    }
    public void setAddAndMinusu(AddAndMinus addAndMinusu){
        this.addAndMinusu=addAndMinusu;
    }

}
