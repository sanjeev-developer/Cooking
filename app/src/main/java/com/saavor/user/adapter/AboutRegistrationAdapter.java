package com.saavor.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saavor.user.R;

import java.util.ArrayList;

/**
 * Created by a123456 on 21/04/17.
 */

public class AboutRegistrationAdapter extends RecyclerView.Adapter<AboutRegistrationAdapter.MyViewHolder> {

    private ArrayList<String> listItem;
    String[]data;
    String[] values;
    private Context context;
    int count = 0 ;

    public AboutRegistrationAdapter( Context contexts,String[] list, String[] txt_values) {
        this.data = list;
        this.values=txt_values;
        this.context=contexts;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view= inflater.inflate(R.layout.custom_listitems, null);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_listitems, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        count++;
        holder.textView.setText(String.valueOf(count));
        holder.txt_list.setText(data[position]);
        holder.txt_about2.setText(values[position]);

    }


    @Override
    public int getItemCount() {
        return 3;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView,txt_list,txt_about2;

        public MyViewHolder(View view) {
            super(view);

            textView=(TextView)view.findViewById(R.id.txt_count_listitems);
            txt_list=(TextView)view.findViewById(R.id.txt_list_about);
            txt_about2=(TextView)view.findViewById(R.id.txt_about2);


        }


    }
}