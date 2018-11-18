package com.bwie.fjd.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.HomeBean;
import com.bwie.fjd.view.activity.InfoActivity;
import com.facebook.drawee.view.SimpleDraweeView;


public class MiaoAdapter extends RecyclerView.Adapter<MiaoAdapter.ViewHolder>{
    private Context context;
    private HomeBean.DataBean.MiaoshaBean data;
    public MiaoAdapter(Context context, HomeBean.DataBean.MiaoshaBean data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.miaoitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Uri uri = Uri.parse(data.getList().get(position).getImages().split("\\|")[0]);
        holder.img.setImageURI(uri);
        holder.text_fen.setText(data.getList().get(position).getPrice()+"￥");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,InfoActivity.class);
                int pid = data.getList().get(position).getPid();
                intent.putExtra("pid",pid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.getList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final SimpleDraweeView img;
        private final TextView text_fen;
        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.simple);
            text_fen = itemView.findViewById(R.id.text_fen);
        }
    }
}
