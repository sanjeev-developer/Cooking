package com.saavor.user.adapter;

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

import com.saavor.user.Model.ChefSearchList;
import com.saavor.user.R;
import com.saavor.user.chefserver.ChefProfileActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 09-12-2017.
 */

public class TopChefsAdapter extends RecyclerView.Adapter<TopChefsAdapter.MyViewHolder> {

    Context context;
    ArrayList<ChefSearchList> aLChefSearchList;
    boolean IsSeeAll;

    public TopChefsAdapter(Context context, ArrayList<ChefSearchList> aLChefSearchList,boolean IsSeeAll) {
        this.context = context;
        this.aLChefSearchList = aLChefSearchList;
        this.IsSeeAll=IsSeeAll;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_chef_listitems, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {

        holder.txt_Bookings.setText(aLChefSearchList.get(position).getVerifiedBookings());
        holder.ll_parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, ChefProfileActivity.class);
                intent.putExtra("ChefId",aLChefSearchList.get(position).getChefId());
                context.startActivity(intent);

            }
        });

        if (!aLChefSearchList.get(position).getProfileImagePath().equalsIgnoreCase("")) {
            holder.imageLoader.setVisibility(View.VISIBLE);
            Picasso.with(context).invalidate("http://saavorapi.parkeee.net/" + aLChefSearchList.get(position).getProfileImagePath());
            Picasso.with(holder.img_chefDp.getContext())
                    .load("http://saavorapi.parkeee.net/" + aLChefSearchList.get(position).getProfileImagePath())
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .resize(96, 96).centerCrop().into(holder.img_chefDp, new Callback() {
                @Override
                public void onSuccess() {
                    holder.imageLoader.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                }
            });
        }

        if (aLChefSearchList.get(position).getStarRating().equalsIgnoreCase("1")) {
            holder.img_heart1_d.setImageResource(R.drawable.redreview);
        } else if (aLChefSearchList.get(position).getStarRating().equalsIgnoreCase("2")) {
            holder.img_heart1_d.setImageResource(R.drawable.redreview);
            holder.img_heart2_d.setImageResource(R.drawable.redreview);
        } else if (aLChefSearchList.get(position).getStarRating().equalsIgnoreCase("3")) {
            holder.img_heart1_d.setImageResource(R.drawable.redreview);
            holder.img_heart2_d.setImageResource(R.drawable.redreview);
            holder.img_heart3_d.setImageResource(R.drawable.redreview);
        } else if (aLChefSearchList.get(position).getStarRating().equalsIgnoreCase("4")) {
            holder.img_heart1_d.setImageResource(R.drawable.redreview);
            holder.img_heart2_d.setImageResource(R.drawable.redreview);
            holder.img_heart3_d.setImageResource(R.drawable.redreview);
            holder.img_heart4_d.setImageResource(R.drawable.redreview);
        } else if (aLChefSearchList.get(position).getStarRating().equalsIgnoreCase("5")) {
            holder.img_heart1_d.setImageResource(R.drawable.redreview);
            holder.img_heart2_d.setImageResource(R.drawable.redreview);
            holder.img_heart3_d.setImageResource(R.drawable.redreview);
            holder.img_heart4_d.setImageResource(R.drawable.redreview);
            holder.img_heart5_d.setImageResource(R.drawable.redreview);
        }

        holder.txt_chef_name.setText(aLChefSearchList.get(position).getFirstName()+" "+aLChefSearchList.get(position).getLastName());
     //   holder.txt_chef_name.setText(aLChefSearchList.get(position).getBusinessName());

        if(aLChefSearchList.get(position).getCuisineList() == null || aLChefSearchList.get(position).getCuisineList().equals(""))
        {
            holder.txt_region.setVisibility(View.GONE);
        }
        else
        {
            holder.txt_region.setText(aLChefSearchList.get(position).getCuisineList());
        }
        holder.txt_hourly_rate.setText("$"+aLChefSearchList.get(position).getPrice()+"/hr");
        holder.txt_distance.setText("within "+aLChefSearchList.get(position).getBookingRadius()+" miles");
        holder.txt_company_name.setText(aLChefSearchList.get(position).getBusinessName());

    }

    @Override
    public int getItemCount() {
        int count=0;
        if(IsSeeAll){
            count= aLChefSearchList.size();
        }else{
            if(aLChefSearchList.size()<=3){
                count= aLChefSearchList.size();
            }else{
                count=3;
            }

        }
        return count;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_chefDp;
        ImageView img_heart1_d, img_heart2_d, img_heart3_d, img_heart4_d, img_heart5_d;
        TextView txt_chef_name;
        TextView txt_company_name;
        TextView txt_region;
        TextView txt_hourly_rate;
        TextView txt_distance;
        TextView txt_Bookings;
        LinearLayout ll_parentView;
        ProgressBar imageLoader;

        public MyViewHolder(View view) {
            super(view);
            img_chefDp = (ImageView) view.findViewById(R.id.img_chefDp);
            img_heart1_d = (ImageView) view.findViewById(R.id.img_heart1_d);
            img_heart2_d = (ImageView) view.findViewById(R.id.img_heart2_d);
            img_heart3_d = (ImageView) view.findViewById(R.id.img_heart3_d);
            img_heart4_d = (ImageView) view.findViewById(R.id.img_heart4_d);
            img_heart5_d = (ImageView) view.findViewById(R.id.img_heart5_d);

            txt_chef_name = (TextView) view.findViewById(R.id.txt_chef_name);
            txt_company_name = (TextView) view.findViewById(R.id.txt_company_name);
            txt_region = (TextView) view.findViewById(R.id.txt_region);
            txt_hourly_rate = (TextView) view.findViewById(R.id.txt_hourly_rate);
            txt_distance = (TextView) view.findViewById(R.id.txt_distance);
            txt_Bookings = (TextView) view.findViewById(R.id.txt_Bookings);

            ll_parentView = (LinearLayout) view.findViewById(R.id.ll_parentView);

            imageLoader = (ProgressBar) view.findViewById(R.id.imageLoader);
        }
    }
}
