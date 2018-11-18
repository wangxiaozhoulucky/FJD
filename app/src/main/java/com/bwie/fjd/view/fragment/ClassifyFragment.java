package com.bwie.fjd.view.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.FenBean;
import com.bwie.fjd.model.bean.HomeBean;
import com.bwie.fjd.presenter.FenPresenter;
import com.bwie.fjd.presenter.MainPresenter;
import com.bwie.fjd.view.activity.SouActivity;
import com.bwie.fjd.view.adapter.FYAdapter;
import com.bwie.fjd.view.adapter.FZAdapter;
import com.bwie.fjd.view.iview.MainView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
public class ClassifyFragment extends Fragment implements MainView {
    private static final int REQUEST_CODE = 200;
    @BindView(R.id.sao)
    ImageView sao;
    @BindView(R.id.sou)
    ImageView sou;
    @BindView(R.id.edit_sou)
    EditText editSou;
    @BindView(R.id.recl_left)
    RecyclerView reclLeft;
    @BindView(R.id.recl_right)
    RecyclerView reclRight;
    Unbinder unbinder;
    private FenPresenter fenPresenter;
    private MainPresenter mainPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classify, container, false);
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        mainPresenter.loadData();
        fenPresenter = new FenPresenter();
        fenPresenter.attachView(this);
        fenPresenter.fenData("1");
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fenPresenter.datachView();
        mainPresenter.datachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @OnClick({R.id.sao, R.id.sou})
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

    @Override
    public void onSuccess(final HomeBean.DataBean homeBean) {
        final List<HomeBean.DataBean.FenleiBean> data = homeBean.getFenlei();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        reclLeft.setLayoutManager(manager);
        FZAdapter adapter = new FZAdapter(getActivity(), data);
        reclLeft.setAdapter(adapter);
        adapter.setOnItemClickListener(new FZAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), "点击了"+position, Toast.LENGTH_SHORT).show();
                fenPresenter.fenData(String.valueOf(data.get(position).getCid()));
            }
        });
    }

    @Override
    public void onYouSuccess(FenBean fenBean) {
        List<FenBean.DataBean> data = fenBean.getData();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        reclRight.setLayoutManager(manager);
        FYAdapter fyAdapter = new FYAdapter( getActivity(),data);
        reclRight.setAdapter(fyAdapter);
    }

    @Override
    public void onerror(String msg) {

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

}
