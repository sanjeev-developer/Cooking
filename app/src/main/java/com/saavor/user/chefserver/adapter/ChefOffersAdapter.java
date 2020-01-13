package com.saavor.user.chefserver.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.saavor.user.Model.ChefList;
import com.saavor.user.R;
import com.saavor.user.chefserver.ChefProfileActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 14-12-2017.
 */

public class ChefOffersAdapter extends RecyclerView.Adapter<ChefOffersAdapter.MyViewHolder> {

    ArrayList<ChefList> aListChefList;
    Context context;

    public ChefOffersAdapter(Context context, ArrayList<ChefList> aListChefList) {
        this.aListChefList = aListChefList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashofferlayout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {

        holder.txt_dash_offer.setText(aListChefList.get(position).getFirstName()+" "+aListChefList.get(position).getLastName());
        holder.txt_best_disc.setText(aListChefList.get(position).getPrice());

        if (!aListChefList.get(position).getProfileImagePath().equalsIgnoreCase("")) {
          //  holder.imageLoader.setVisibility(View.VISIBLE);
            Picasso.with(context).invalidate("http://saavorapi.parkeee.net/" +aListChefList.get(position).getProfileImagePath());
            Picasso.with(holder.img_offer.getContext())
                    .load("http://saavorapi.parkeee.net/" +aListChefList.get(position).getProfileImagePath())
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .resize(96, 96).centerCrop().into(holder.img_offer, new Callback() {
                @Override
                public void onSuccess() {
               //     holder.imageLoader.setVisibility(View.GONE);
                }
                @Override
                public void onError() {
                }
            });
        }

        holder.ll_parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, ChefProfileActivity.class);
                intent.putExtra("ChefId",aListChefList.get(position).getChefId());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return aListChefList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_offer;
        TextView txt_dash_offer;
        TextView txt_best_disc;
       // ProgressBar imageLoader;
        LinearLayout ll_parentView;
        public MyViewHolder(View view) {
            super(view);
            img_offer=(ImageView)view.findViewById(R.id.img_offer);
            txt_dash_offer=(TextView)view.findViewById(R.id.txt_dash_offer);
            txt_best_disc=(TextView)view.findViewById(R.id.txt_best_disc);
          //  imageLoader=(ProgressBar) view.findViewById(R.id.imageLoader);
            ll_parentView = (LinearLayout) view.findViewById(R.id.offer_ll);
        }
    }

}
