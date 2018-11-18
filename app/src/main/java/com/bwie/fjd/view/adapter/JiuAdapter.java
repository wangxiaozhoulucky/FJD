package com.bwie.fjd.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.HomeBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

public class JiuAdapter extends RecyclerView.Adapter<JiuAdapter.ViewHolder> {
    private Context context;
    private List<HomeBean.DataBean.FenleiBean> data;

    public JiuAdapter(Context context, List<HomeBean.DataBean.FenleiBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jiuitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri uri = Uri.parse(data.get(position).getIcon().split("\\|")[0]);
        holder.img.setImageURI(uri);
        holder.text_fen.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
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
