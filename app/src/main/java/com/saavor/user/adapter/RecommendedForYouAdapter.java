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

import com.saavor.user.Model.ChefList;
import com.saavor.user.R;
import com.saavor.user.chefserver.ChefProfileActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

/**
 * Created by user on 09-12-2017.
 */

public class RecommendedForYouAdapter extends RecyclerView.Adapter<RecommendedForYouAdapter.MyViewHolder>  {

    Context context;
    ArrayList<ChefList>aListChefList;

    public RecommendedForYouAdapter(Context mcontext,ArrayList<ChefList>aListChefList){
        context=mcontext;
        this.aListChefList=aListChefList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_for_you, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {

        // set the values=====

        holder.txtHourlyRate.setText("$"+aListChefList.get(position).getPrice());
        holder.txtUserName.setText(aListChefList.get(position).getFirstName()+" "+aListChefList.get(position).getLastName());
         if(aListChefList.get(position).getStarRating().equalsIgnoreCase("1")){
            holder.imgRating1.setImageResource(R.drawable.redreview);
        }else if(aListChefList.get(position).getStarRating().equalsIgnoreCase("2")){
            holder.imgRating1.setImageResource(R.drawable.redreview);
            holder.imgRating2.setImageResource(R.drawable.redreview);
        }else if(aListChefList.get(position).getStarRating().equalsIgnoreCase("3")){
            holder.imgRating1.setImageResource(R.drawable.redreview);
            holder.imgRating2.setImageResource(R.drawable.redreview);
            holder.imgRating3.setImageResource(R.drawable.redreview);
        }else if(aListChefList.get(position).getStarRating().equalsIgnoreCase("4")){
             holder.imgRating1.setImageResource(R.drawable.redreview);
             holder.imgRating2.setImageResource(R.drawable.redreview);
             holder.imgRating3.setImageResource(R.drawable.redreview);
             holder.imgRating4.setImageResource(R.drawable.redreview);
        }else if(aListChefList.get(position).getStarRating().equalsIgnoreCase("5")){
             holder.imgRating1.setImageResource(R.drawable.redreview);
             holder.imgRating2.setImageResource(R.drawable.redreview);
             holder.imgRating3.setImageResource(R.drawable.redreview);
             holder.imgRating4.setImageResource(R.drawable.redreview);
             holder.imgRating5.setImageResource(R.drawable.redreview);
        }


        if (!aListChefList.get(position).getProfileImagePath().equalsIgnoreCase("")) {
            holder.imageLoader.setVisibility(View.VISIBLE);
            Picasso.with(context).invalidate("http://saavorapi.parkeee.net/" +aListChefList.get(position).getProfileImagePath());
            Picasso.with(holder.profilePicIv.getContext())
                    .load("http://saavorapi.parkeee.net/" +aListChefList.get(position).getProfileImagePath())
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .resize(96, 96).centerCrop().into(holder.profilePicIv, new Callback() {
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
        ImageView profilePicIv,imgRating1,imgRating2,imgRating3,imgRating4,imgRating5;
        TextView txtHourlyRate;
        TextView txtUserName;
        ProgressBar imageLoader;
        LinearLayout ll_parentView;

        public MyViewHolder(View view) {
            super(view);

            profilePicIv=(ImageView)view.findViewById(R.id.profilePicIv);
            imgRating1=(ImageView)view.findViewById(R.id.imgRating1);
            imgRating2=(ImageView)view.findViewById(R.id.imgRating2);
            imgRating3=(ImageView)view.findViewById(R.id.imgRating3);
            imgRating4=(ImageView)view.findViewById(R.id.imgRating4);
            imgRating5=(ImageView)view.findViewById(R.id.imgRating5);

            txtHourlyRate=(TextView)view.findViewById(R.id.txtHourlyRate);
            txtUserName=(TextView)view.findViewById(R.id.txtUserName);
            imageLoader=(ProgressBar)view.findViewById(R.id.imageLoader);

            ll_parentView = (LinearLayout) view.findViewById(R.id.ll_parentView);

        }
    }
}
