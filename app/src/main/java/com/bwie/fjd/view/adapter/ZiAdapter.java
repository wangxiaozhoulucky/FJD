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
import com.bwie.fjd.model.bean.FenBean;
import com.bwie.fjd.view.activity.PaiActivity;
import com.bwie.fjd.view.activity.ShowActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.List;
public class ZiAdapter extends RecyclerView.Adapter<ZiAdapter.Holder> {
    private List<FenBean.DataBean> msg;
    private Context context;
    private OnItemClickListener onClickListener;

    public ZiAdapter(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ZiAdapter(List<FenBean.DataBean> msg, Context context) {
        this.msg = msg;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ziitem, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        Uri uri = Uri.parse(msg.get(position).getList().get(position).getIcon().split("\\|")[0]);
        holder.sim.setImageURI(uri);
        holder.name.setText(msg.get(position).getList().get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PaiActivity.class);
                String pscid = String.valueOf(msg.get(position).getList().get(position).getPscid());
                intent.putExtra("pscid",pscid);
                intent.putExtra("sousuo", msg.get(position).getList().get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return msg.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private final SimpleDraweeView sim;
        private final TextView name;

        public Holder(View itemView) {
            super(itemView);
            sim = itemView.findViewById(R.id.sim_dr_view);
            name = itemView.findViewById(R.id.text_you_name);
        }
    }

    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onClickListener= onClickListener;
    }

    interface OnItemClickListener{
        void onItemClickListener(int position);
    }

}