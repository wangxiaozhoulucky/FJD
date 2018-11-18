package com.bwie.fjd.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.FenBean;

import java.util.List;

public class FYAdapter extends RecyclerView.Adapter<FYAdapter.ViewHolder>{
private Context context;
private List<FenBean.DataBean> list;

    public FYAdapter(Context context, List<FenBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fyitem, parent, false);
      ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.text001.setText(list.get(position).getName());
        GridLayoutManager manager1 = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);
        ZiAdapter adapter = new ZiAdapter(list,context);
        holder.rec_view.setLayoutManager(manager1);
        holder.rec_view.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView text001;
        private final RecyclerView rec_view;

        public ViewHolder(View itemView) {
            super(itemView);
            text001 = itemView.findViewById(R.id.text_001);
            rec_view = itemView.findViewById(R.id.recycler_you_view);
        }
    }
}
