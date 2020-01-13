package com.saavor.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.R;
import com.saavor.user.activity.AddAddress;

import java.util.ArrayList;

/**
 * Created by a123456 on 24/01/18.
 */

public class StateSpinner extends BaseAdapter {
    Context context;
    ArrayList<String> countryNames = new ArrayList<String>();
    LayoutInflater inflter;

    public StateSpinner(Context applicationContext,  ArrayList<String> countryNames) {
        this.context = applicationContext;
        this.countryNames = countryNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.spinner_data);
        LinearLayout linearLayout=  (LinearLayout) view.findViewById(R.id.ll_spinner);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddAddress) context).stateselected(countryNames.get(i).toString(), i);
            }
        });

        names.setText(countryNames.get(i));
        return view;
    }
}
