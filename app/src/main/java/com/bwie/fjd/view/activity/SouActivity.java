package com.bwie.fjd.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.fjd.R;
import com.bwie.fjd.util.FlowLayout;
import com.bwie.fjd.util.LiuShi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SouActivity extends AppCompatActivity {

    @BindView(R.id.sao)
    ImageView sao;
    @BindView(R.id.sou)
    ImageView sou;
    @BindView(R.id.edit_sou)
    EditText editSou;
    @BindView(R.id.liushi)
    FlowLayout liushi;
    private ViewGroup.MarginLayoutParams layoutParams;
    private List<String> list;
    private String string;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou);
        ButterKnife.bind(this);
        editSou.setCursorVisible(true);
        editSou.setFocusable(true);
        editSou.setFocusableInTouchMode(true);
        initData();
    }

    private void initData() {
        layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 40;
        layoutParams.rightMargin = 40;
        list = new ArrayList<>();
    }

    @OnClick(R.id.sou)
    public void onViewClicked() {
        liushi.removeAllViews();
        string = editSou.getText().toString();
        list.add(string);
        for (int i = list.size() - 1; i > -1; i--) {
            textView = new TextView(SouActivity.this);
            textView.setText(list.get(i));
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(layoutParams);
            liushi.addView(textView);
        }
        if (string!=null){
            Intent intent = new Intent(SouActivity.this, ShowActivity.class);
            intent.putExtra("sousuo", string);
            editSou.setText("");
            startActivity(intent);
        }else {
            Toast.makeText(this, "请输入内容！", Toast.LENGTH_SHORT).show();
        }

    }
}
