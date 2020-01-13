package com.saavor.user.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saavor.user.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by a123456 on 05/12/17.
 */



public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {


    private Context context;
    int count = 0;
    public SharedPreferences mDatabase;
    public SharedPreferences.Editor mTabel;
    public Gson gson;
    ArrayList<String> listt= new ArrayList<>();


    public ListAdapter(Context contexts, ArrayList<String> listt) {

        this.context = contexts;
        this.listt= listt;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_cusine_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.title.setText(listt.get(position));

    }

    @Override
    public int getItemCount() {
        return listt.size()-1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;


        public MyViewHolder(View view) {
            super(view);

            title=(TextView)view.findViewById(R.id.txt_cusine_title);

        }
    }
}