package com.bwie.fjd.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bwie.fjd.R;

public class DetailsAdapter extends PagerAdapter {
    private Context context;
    private String[] split;

    public DetailsAdapter(Context context, String[] split) {
        this.context = context;
        this.split = split;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = View.inflate(context, R.layout.layout_detailviewpager, null);
        ImageView img = view.findViewById(R.id.img);
        Glide.with(context).load(split[position%split.length]).into(img);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


}
