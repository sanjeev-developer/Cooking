


package com.saavor.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.saavor.user.Model.FavDishList;
import com.saavor.user.R;
import com.saavor.user.activity.Favourite;

import java.util.ArrayList;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    private ArrayList<FavDishList> favdata;
    private Context context;
    int count = 0 ;

    public FavoriteAdapter(Context contexts, ArrayList<FavDishList> favdata)
    {
        this.favdata = favdata;
        this.context=contexts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_layout, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position)
    {
        holder.title.setText(favdata.get(position).getDishName());
        holder.dishtype.setText(favdata.get(position).getKitchenName().toString());

        if(favdata.get(position).getIsOpen() == 1)
        {
            holder.order.setVisibility(View.VISIBLE);
            holder.notavail.setVisibility(View.GONE);
        }
        else
        {
            holder.order.setVisibility(View.GONE);
            holder.notavail.setVisibility(View.VISIBLE);
        }

        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                ((Favourite) context).reorder(favdata.get(position).getDishId() , favdata.get(position).getDishName() , favdata.get(position).getKitchenName() , favdata.get(position).getProfileId(), favdata.get(position).getDeliveryCharges());
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return favdata.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,dishtype,order,notavail;

        public MyViewHolder(View view) {
            super(view);

            title=(TextView)view.findViewById(R.id.txt_fav_title);
            order=(Button)view.findViewById(R.id.btn_order_fav);
            dishtype=(TextView)view.findViewById(R.id.txt_fav_location);
            notavail=(TextView)view.findViewById(R.id.txt_notavail);

        }
    }
}