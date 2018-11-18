package com.bwie.fjd.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.SouBean;
import com.bwie.fjd.model.bean.XiaoBean;
import com.bwie.fjd.view.activity.InfoActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class SouSuoAdapter extends RecyclerView.Adapter<SouSuoAdapter.MyViewHolder>{
    private Context context;
    private List<SouBean.DataBean> data;
    public SouSuoAdapter(Context context, List<SouBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.showlayout,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.num.setText("销量:"+data.get(position).getSalenum());
        holder.showAdapterTitle.setText(data.get(position).getTitle());
        holder.showAdapterPrice.setText("价格:"+data.get(position).getPrice()+"￥");
        String images = data.get(position).getImages();
        String[] split = images.split("\\|");
        Uri parse = Uri.parse(split[0]);
        AbstractDraweeController fresco = Fresco.newDraweeControllerBuilder()
                .setUri(parse)
                .build();
        holder.showAdapterSimple.setController(fresco);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,InfoActivity.class);
                int pid = data.get(position).getPid();
                intent.putExtra("pid",pid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView showAdapterSimple;
        private TextView showAdapterTitle,showAdapterPrice,num;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            showAdapterSimple = itemView.findViewById(R.id.show_adapter_simple);
            showAdapterPrice = itemView.findViewById(R.id.show_adapter_price);
            showAdapterTitle = itemView.findViewById(R.id.show_adapter_title);
            num = itemView.findViewById(R.id.show_adapter_selanum);
        }
    }
}
