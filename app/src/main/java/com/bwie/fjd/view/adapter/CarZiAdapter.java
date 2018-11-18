package com.bwie.fjd.view.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.CarBean;
import com.bwie.fjd.model.bean.ShanBean;
import com.bwie.fjd.model.bean.bean1;
import com.bwie.fjd.presenter.CarPresenter;
import com.bwie.fjd.presenter.ShanPresenter;
import com.bwie.fjd.util.button;
import com.bwie.fjd.view.iview.CarView;
import com.bwie.fjd.view.iview.ShanView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CarZiAdapter extends RecyclerView.Adapter<CarZiAdapter.ViewHolder> implements ShanView{
 private Context context;
 private List<CarBean.DataBean.ListBean> data;
    private ShanPresenter shanPresenter;
    private SharedPreferences sp;
    private CarPresenter carPresenter;
    private String uid;
    private int pid;

    public CarZiAdapter(Context context, List<CarBean.DataBean.ListBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.caritem02, parent, false);
        ViewHolder holder = new ViewHolder(view);
        shanPresenter = new ShanPresenter();
        shanPresenter.attachView(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Uri uri = Uri.parse(data.get(position).getImages().split("\\|")[0]);
        holder.simple_img.setImageURI(uri);
        holder.price.setText(data.get(position).getPrice()+"￥");
        holder.shop_name.setText(data.get(position).getTitle());
        holder.text_num.setText(data.get(position).getNum()+"");
        //删除点击事件
        holder.delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = context.getSharedPreferences("flag", Context.MODE_PRIVATE);
                 uid = sp.getString("uid", "1");
                 pid = data.get(position).getPid();
                shanPresenter.shanCart(uid, String.valueOf(pid));

            }
        });
        holder.button.setAddAndMinusu(new button.AddAndMinus() {
            @Override
            public void add() {
                data.get(position).setNum(data.get(position).getNum()+1);
                bean1 bean1 = new bean1();
                EventBus.getDefault().post(bean1);
            }

            @Override
            public void minus() {
                data.get(position).setNum(data.get(position).getNum()-1);
                bean1 bean1 = new bean1();
                EventBus.getDefault().post(bean1);
            }
        });
        holder.check_zi.setChecked(data.get(position).isInnerchecked());
        //判断条目复选框的状态
        holder.check_zi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //内部状态
                data.get(position).setInnerchecked(holder.check_zi.isChecked());
                onclickchangelisten.onchecked(holder.getLayoutPosition(),holder.check_zi.isChecked());
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private final CheckBox check_zi;
        private final SimpleDraweeView simple_img;
        private final TextView shop_name;
        private final TextView price;
        private final ImageView delete_img;
        private final  button  button;
        private final TextView text_num;

        public ViewHolder(View itemView) {
            super(itemView);
            check_zi = itemView.findViewById(R.id.check_zi);
            simple_img = itemView.findViewById(R.id.simple_img);
            shop_name = itemView.findViewById(R.id.shop_name);
            price = itemView.findViewById(R.id.price);
            delete_img = itemView.findViewById(R.id.delete);
            button = itemView.findViewById(R.id.button);
            text_num = itemView.findViewById(R.id.text_num);
        }
    }

    private onclickchangelisten onclickchangelisten;
    //向外提供方法
    public void setOnclickchangelisten(CarZiAdapter.onclickchangelisten onclickchangelisten) {
        this.onclickchangelisten = onclickchangelisten;
    }

    //条目的回调方法
    public  interface onclickchangelisten{
        void onchecked(int layoutPosition,boolean checked);
    }

    @Override
    public void onSucces(ShanBean shanBean) {
        Toast.makeText(context, ""+shanBean.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onerre(ShanBean shanBean) {

    }


}
