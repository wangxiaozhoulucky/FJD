package com.bwie.fjd.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bwie.fjd.R;
import com.bwie.fjd.model.bean.CarBean;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder>{
 private Context context;
 private List<CarBean.DataBean> data;
    private CarZiAdapter carZiAdapter;

    public CarAdapter(Context context, List<CarBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.caritem01, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //全选
          holder.check_shang.setChecked(data.get(position).isOutchecked());
          holder.check_shang.setText(data.get(position).getSellerName());
          holder.rec_two.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
          final List<CarBean.DataBean.ListBean> list = data.get(position).getList();
        carZiAdapter = new CarZiAdapter(context, list);

        holder.rec_two.setAdapter(carZiAdapter);
        holder.check_shang.setOnClickListener(null);
        //商家选中控制里面的子条目
        holder.check_shang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ischecked = holder.check_shang.isChecked();//定义商家的选中状态
                data.get(position).setOutchecked(ischecked);
                if (ischecked){
                    for (int i = 0; i < data.get(holder.getLayoutPosition()).getList().size(); i++) {
                        data.get(holder.getLayoutPosition()).getList().get(i).setInnerchecked(true);
                    }

                }else {
                    for (int i = 0; i < data.get(holder.getLayoutPosition()).getList().size(); i++) {
                        data.get(holder.getLayoutPosition()).getList().get(i).setInnerchecked(false);
                    }
                }
                onclickchangelisten.onitemchecked(holder.getLayoutPosition(),ischecked);

            }
        });
//获取里层条目的选中状态
        carZiAdapter.setOnclickchangelisten(new CarZiAdapter.onclickchangelisten() {
            @Override
            public void onchecked(int layoutPosition, boolean checked) {
                //定义一个默认值
                boolean b=true;
                for (int i = 0; i < data.get(holder.getLayoutPosition()).getList().size(); i++) {
                    boolean innerchecked = data.get(holder.getLayoutPosition()).getList().get(i).isInnerchecked();
                    b=(b&innerchecked);
                }
                //设置给商家
                holder.check_shang.setChecked(b);
                //设置给外层条目
                data.get(position).setOutchecked(b);
                onclickchangelisten.onchecked(holder.getLayoutPosition(),checked);
            }
        });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final CheckBox check_shang;
        private final RecyclerView rec_two;
        private final ImageView delete_img;

        public ViewHolder(View itemView) {
            super(itemView);
            check_shang = itemView.findViewById(R.id.check_shang);
            rec_two = itemView.findViewById(R.id.rec_two);
            delete_img = itemView.findViewById(R.id.delete);
        }
    }


    private onclickchangelisten onclickchangelisten;
    //向外提供方法

    public void setOnclickchangelisten(CarAdapter.onclickchangelisten onclickchangelisten) {
        this.onclickchangelisten = onclickchangelisten;
    }
    //全选的商家回调方法
    public  interface onclickchangelisten{
        void onchecked(int layoutPosition,boolean checked);
        void onitemchecked(int layoutPosition,boolean ischecked);
    }
}
