package com.saavor.user.chefserver.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saavor.user.R;

import java.util.ArrayList;


public class ServiceDetailsAdapter extends RecyclerView.Adapter<ServiceDetailsAdapter.MyViewHolder> {

    ArrayList<String> aLDishName;
    Activity context;
    public ServiceDetailsAdapter(Activity context,ArrayList<String>aLDishName) {

        this.context=context;
        this.aLDishName=aLDishName;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_cart_listitems, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {

        holder.imgDelete.setVisibility(View.GONE);
        holder.txtItemName.setText(aLDishName.get(position).toString());
        holder.View.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return aLDishName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txtItemName;
        ImageView imgDelete;
        View View;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtItemName=(TextView)itemView.findViewById(R.id.txtItemName);
            imgDelete=(ImageView)itemView.findViewById(R.id.imgDelete);
            View=(View)itemView.findViewById(R.id.View);
        }
    }
}
