package com.saavor.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saavor.user.Model.KitchensDealList;
import com.saavor.user.R;
import com.saavor.user.activity.XYZKitchen;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


    public class SeeAllDealAdapter extends RecyclerView.Adapter<com.saavor.user.adapter.SeeAllDealAdapter.MyViewHolder> {

        private ArrayList<KitchensDealList> dishname;
        private Context context;
        int count = 0 ;

        public SeeAllDealAdapter(Context contexts, ArrayList<KitchensDealList> dishname)
        {
            this.dishname = dishname;
            this.context=contexts;
        }

        @Override
        public com.saavor.user.adapter.SeeAllDealAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashofferlayout, parent, false);
            return new com.saavor.user.adapter.SeeAllDealAdapter.MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final com.saavor.user.adapter.SeeAllDealAdapter.MyViewHolder holder, final int position)
        {
            holder.dish_type.setText(dishname.get(position).getKitchenName());
            holder.disc.setText(dishname.get(position).getDealTitle().toString());

            try {
                // Picasso.with(getApplicationContext()).load("http://saavorapi.parkeee.net/" + basicInformation.getUserprofile().toString()).into(navpic);
                // ImageView targetImageView = (ImageView) findViewById(R.id.imageView);
                String internetUrl = "http://saavorapi.parkeee.net/" + dishname.get(position).getProfileImagePath();

                Glide.with(getApplicationContext()).load(internetUrl).error( R.drawable.usericonpd ).into(holder.offerdp);
                System.out.println(">>>>>>>>>");

            } catch (Exception e) {
                holder.offerdp.setImageResource(R.drawable.usericonpd);
            }

            holder.offer_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //  Toast.makeText(context,"deal " + position,Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, XYZKitchen.class);
                    intent.putExtra("profileid", dishname.get(position).getProfileId());
                    intent.putExtra("fromdeal", true);
                    context.startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount()
        {
                return  dishname.size();

        }
        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView dish_type,disc;
            ImageView offerdp;
            LinearLayout offer_ll;
            public MyViewHolder(View view) {
                super(view);

                dish_type=(TextView)view.findViewById(R.id.txt_dash_offer);
                disc=(TextView)view.findViewById(R.id.txt_best_disc);
                offerdp= (ImageView) view.findViewById(R.id.img_offer);
                offer_ll= (LinearLayout) view.findViewById(R.id.offer_ll);
            }
        }
    }

