package com.saavor.user.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.R;


/**
 * Created by user on 11-12-2017.
 */

public class ChefsDealsAdapter extends PagerAdapter {

    Context context;

    public ChefsDealsAdapter(Context context){

        this.context=context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView txt_main;
        LinearLayout ll_parentView;
        View itemView = inflater.inflate(R.layout.dealaround, container, false);
        txt_main=(TextView)itemView.findViewById(R.id.txt_main);
        ll_parentView=(LinearLayout)itemView.findViewById(R.id.ll_parentView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
