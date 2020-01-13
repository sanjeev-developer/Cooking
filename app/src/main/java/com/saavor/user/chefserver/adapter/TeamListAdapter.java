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

import com.saavor.user.Model.TeamList;
import com.saavor.user.R;
import com.saavor.user.chefserver.ChefHutActivity;
import com.saavor.user.chefserver.ChefProfileActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.MyViewHolder> {

    ArrayList<TeamList> aLTeamList;
    Context context;


    public TeamListAdapter(Context context, ArrayList<TeamList> aLTeamList) {
        this.context = context;
        this.aLTeamList = aLTeamList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {

        holder.txt_member_name.setText(aLTeamList.get(position).getUserName());
        holder.txt_occupation.setText(aLTeamList.get(position).getBusinessType());
        holder.txt_region.setText(aLTeamList.get(position).getCuisineList());
        holder.txt_hourly_rate.setText("$"+aLTeamList.get(position).getPrice()+"/hr"+" | "+aLTeamList.get(position).getMinBookingAmount()+"hr min booking");

        if (!aLTeamList.get(position).getProfileImagePath().equalsIgnoreCase("")) {
            holder.imageLoader.setVisibility(View.VISIBLE);
            Picasso.with(context).invalidate("http://saavorapi.parkeee.net/" + aLTeamList.get(position).getProfileImagePath());
            Picasso.with(holder.img_Dp.getContext())
                    .load("http://saavorapi.parkeee.net/" + aLTeamList.get(position).getProfileImagePath())
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .resize(96, 96).centerCrop().into(holder.img_Dp, new Callback() {
                @Override
                public void onSuccess() {
                    holder.imageLoader.setVisibility(View.GONE);
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
                intent.putExtra("ChefId",aLTeamList.get(position).getChefId());
                context.startActivity(intent);
                //((ChefHutActivity)context).FinishActivity();
              //  ChefProfileActivity.activity.finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return aLTeamList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_Dp;
        ProgressBar imageLoader;
        TextView txt_member_name;
        TextView txt_occupation;
        TextView txt_region;
        TextView txt_hourly_rate;
        LinearLayout ll_parentView;

        public MyViewHolder(View View) {
            super(View);
            img_Dp=(ImageView)View.findViewById(R.id.img_Dp);
            imageLoader=(ProgressBar)View.findViewById(R.id.imageLoader);
            txt_member_name=(TextView)View.findViewById(R.id.txt_member_name);
            txt_occupation=(TextView)View.findViewById(R.id.txt_occupation);
            txt_region=(TextView)View.findViewById(R.id.txt_region);
            txt_hourly_rate=(TextView)View.findViewById(R.id.txt_hourly_rate);
            ll_parentView=(LinearLayout)View.findViewById(R.id.ll_parentView);
        }
    }
}
