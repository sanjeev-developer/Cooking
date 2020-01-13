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

/**
 * Created by user on 19-12-2017.
 */

public class ItemsInCartAdapter extends RecyclerView.Adapter<ItemsInCartAdapter.MyViewHolder> {

    ArrayList<String>aLDishName;
    ArrayList<String>aLDishId;
    Activity context;

    public ItemsInCartAdapter(Activity context,ArrayList<String>aLDishName,ArrayList<String>aLDishId) {
        this.context=context;
        this.aLDishName=aLDishName;
        this.aLDishId=aLDishId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_cart_listitems, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aLDishName.remove(aLDishName.get(position).toString());
                aLDishId.remove(aLDishId.get(position).toString());
                if(aLDishName.size()<1){
                    context.finish();
                }
                notifyDataSetChanged();
            }
        });



        holder.txtItemName.setText(aLDishName.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return aLDishName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName;
        ImageView imgDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtItemName=(TextView)itemView.findViewById(R.id.txtItemName);
            imgDelete=(ImageView)itemView.findViewById(R.id.imgDelete);
        }
    }

}
