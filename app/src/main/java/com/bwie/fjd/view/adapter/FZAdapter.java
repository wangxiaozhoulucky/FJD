package com.bwie.fjd.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.FenBean;
import com.bwie.fjd.model.bean.HomeBean;

import java.util.List;

public class FZAdapter extends RecyclerView.Adapter<FZAdapter.ViewHolder>{
 private Context context;
 private List<HomeBean.DataBean.FenleiBean> list;
    private OnItemClickListener onItemClickListener;

    public FZAdapter(Context context, List<HomeBean.DataBean.FenleiBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fzitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.text_zuo.setText(list.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView text_zuo;
        public ViewHolder(View itemView) {
            super(itemView);
            text_zuo = itemView.findViewById(R.id.text_zuo);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
