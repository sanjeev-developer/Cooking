package com.saavor.user.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saavor.user.Model.KitchenList;
import com.saavor.user.R;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

    public class AllKitchenAdapter extends RecyclerView.Adapter<AllKitchenAdapter.MyViewHolder> {

        public ArrayList<KitchenList> dishname;
        private Context context;
        int count = 0 ;


        public AllKitchenAdapter(Context contexts, ArrayList<KitchenList> dishname)
        {
            this.dishname = dishname;
            this.context=contexts;
        }

        @Override
        public AllKitchenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashrecyclelayout, parent, false);
            return new AllKitchenAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final AllKitchenAdapter.MyViewHolder holder, final int position)
        {

            String string = ""+dishname.get(position).getMinOrderAmount().toString();
            String[] parts = string.split("\\.");
            String g = parts[0];
//
//            String stringg = ""+dishname.get(position).getDeliveryCharges().toString();
//            String[] partss = stringg.split("\\.");
//            String t = partss[0];
            holder.txtlunch.setText(dishname.get(position).getServiceList());
            holder.kitchen_title.setText(dishname.get(position).getKitchenName().toString());
            holder.cusine.setText(dishname.get(position).getCuisineList().toString()+"...");
            holder.deliverytime.setText(dishname.get(position).getAvgDeliveryTime().toString()+" mins");
            holder.min_order.setText("$"+g);
           // holder.delivery_fee.setText("$"+t);

            if(dishname.get(position).getIsDelivery() == 1)
            {
//                holder.deliveryFeell.setVisibility(View.VISIBLE);
                holder.deliveryTimell.setVisibility(View.VISIBLE);
                holder.delivery_only.setText("Pickup & Delivery ");
            }
            else
            {
                if(dishname.get(position).getIsKitchenDelivery() == 1)
                {
                    holder.delivery_only.setText("Pick up & Delivery ");
                }
                else
                {
//                    holder.deliveryFeell.setVisibility(View.GONE);
                    holder.deliveryTimell.setVisibility(View.GONE);
                    holder.delivery_only.setText("Pickup Only ");
                }

            }

            if(dishname.get(position).getIsDeal() == 1)
            {
                holder.butdeal.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.butdeal.setVisibility(View.INVISIBLE);
            }

            try {

                String internetUrl = "http://saavorapi.parkeee.net/" + dishname.get(position).getProfileImagePath();
                Glide.with(getApplicationContext()).load(internetUrl).error( R.drawable.usericonpd ).into(holder.kitchenimage);
                System.out.println(">>>>>>>>>");

            } catch (Exception e) {
                holder.kitchenimage.setImageResource(R.drawable.usericonpd);
            }

            holder.move.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String addressk=dishname.get(position).getAddress1()+", "+dishname.get(position).getStateName()+","+dishname.get(position).getCountryName();

                    Intent intent = new Intent(context, XYZKitchen.class);
                    intent.putExtra("profileid", dishname.get(position).getProfileId());
                    intent.putExtra("fromall", true);
                    context.startActivity(intent);
                }
            });



            int star=dishname.get(position).getStarRating();
            switch(star)
            {
                case 0:
                {
                    holder.heart1.setBackgroundResource(R.drawable.greyheart);
                    holder.heart2.setBackgroundResource(R.drawable.greyheart);
                    holder.heart3.setBackgroundResource(R.drawable.greyheart);
                    holder.heart4.setBackgroundResource(R.drawable.greyheart);
                    holder.heart5.setBackgroundResource(R.drawable.greyheart);
                }
                break;

                case 1:
                {
                    holder.heart1.setBackgroundResource(R.drawable.redreview);
                    holder.heart2.setBackgroundResource(R.drawable.greyheart);
                    holder.heart3.setBackgroundResource(R.drawable.greyheart);
                    holder.heart4.setBackgroundResource(R.drawable.greyheart);
                    holder.heart5.setBackgroundResource(R.drawable.greyheart);
                }
                break;

                case 2:
                {
                    holder.heart1.setBackgroundResource(R.drawable.redreview);
                    holder.heart2.setBackgroundResource(R.drawable.redreview);
                    holder.heart3.setBackgroundResource(R.drawable.greyheart);
                    holder.heart4.setBackgroundResource(R.drawable.greyheart);
                    holder.heart5.setBackgroundResource(R.drawable.greyheart);
                }
                break;

                case 3:
                {
                    holder.heart1.setBackgroundResource(R.drawable.redreview);
                    holder.heart2.setBackgroundResource(R.drawable.redreview);
                    holder.heart3.setBackgroundResource(R.drawable.redreview);
                    holder.heart4.setBackgroundResource(R.drawable.greyheart);
                    holder.heart5.setBackgroundResource(R.drawable.greyheart);
                }
                break;

                case 4:
                {
                    holder.heart1.setBackgroundResource(R.drawable.redreview);
                    holder.heart2.setBackgroundResource(R.drawable.redreview);
                    holder.heart3.setBackgroundResource(R.drawable.redreview);
                    holder.heart4.setBackgroundResource(R.drawable.redreview);
                    holder.heart5.setBackgroundResource(R.drawable.greyheart);
                }
                break;

                case 5:
                {
                    holder.heart1.setBackgroundResource(R.drawable.redreview);
                    holder.heart2.setBackgroundResource(R.drawable.redreview);
                    holder.heart3.setBackgroundResource(R.drawable.redreview);
                    holder.heart4.setBackgroundResource(R.drawable.redreview);
                    holder.heart5.setBackgroundResource(R.drawable.redreview);
                }
                break;
            }


        }

        @Override
        public int getItemCount()
        {
            return dishname.size();

        }
        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView kitchen_title, cusine, deliverytime, min_order, delivery_only,txtlunch;
            LinearLayout move, deliveryTimell;
            ImageView kitchenimage,heart1,heart2,heart3,heart4,heart5;
            Button butdeal;

            public MyViewHolder(View view) {
                super(view);

                kitchen_title=(TextView)view.findViewById(R.id.txt_kitchen_title);
                cusine=(TextView)view.findViewById(R.id.txt_cusine);
                deliverytime=(TextView)view.findViewById(R.id.txt_dtime);
                min_order=(TextView)view.findViewById(R.id.txt_morder);
              //  delivery_fee=(TextView)view.findViewById(R.id.txt_dfee);
                delivery_only=(TextView)view.findViewById(R.id.txt_donly);
                move=(LinearLayout) view.findViewById(R.id.ll_kitchen_move);
                kitchenimage= (ImageView) view.findViewById(R.id.img_kitchendp);
                butdeal=(Button) view.findViewById(R.id.but_deal);
                txtlunch=(TextView)view.findViewById(R.id.txt_lunch);
                deliveryTimell=(LinearLayout) view.findViewById(R.id.deliveryTimell);
              //  deliveryFeell=(LinearLayout) view.findViewById(R.id.deliveryFeell);


                heart1= (ImageView) view.findViewById(R.id.img_heart1_d);
                heart2= (ImageView) view.findViewById(R.id.img_heart2_d);
                heart3= (ImageView) view.findViewById(R.id.img_heart3_d);
                heart4= (ImageView) view.findViewById(R.id.img_heart4_d);
                heart5= (ImageView) view.findViewById(R.id.img_heart5_d);
            }
        }
    }
